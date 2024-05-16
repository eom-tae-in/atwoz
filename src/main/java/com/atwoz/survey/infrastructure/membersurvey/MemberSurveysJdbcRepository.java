package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberSurveysJdbcRepository {

    private static final int MINIMUM_MATCH_SIZE = 30;

    private final JdbcTemplate jdbcTemplate;

    public List<Long> findMatchMembers(final Long memberId) {
        List<MemberSurveyResponse> memberSurveyResponses = convertMemberSurveys(memberId);
        List<Long> result = new ArrayList<>();

        List<Long> otherMembers = collectMembersExceptCurrentMember(memberId);
        otherMembers.forEach(id -> {
            List<MemberSurveyResponse> otherMemberSurveyResponses = convertMemberSurveys(id);
            if (isMatched(memberSurveyResponses, otherMemberSurveyResponses)) {
                result.add(id);
            }
        });
        return result;
    }

    private List<MemberSurveyResponse> convertMemberSurveys(final Long memberId) {
        String sql = "SELECT mss.member_id, ms.survey_id, ms.question_id, ms.answer_number " +
                "FROM member_surveys mss " +
                "INNER JOIN member_survey ms " +
                "ON mss.id = ms.member_surveys_id " +
                "WHERE mss.member_id = ?";
        return jdbcTemplate.query(sql, new Object[]{memberId},
                (rs, rowNum) -> new MemberSurveyResponse(
                        rs.getLong("member_id"),
                        rs.getLong("survey_id"),
                        rs.getLong("question_id"),
                        rs.getInt("answer_number")
                )
        );
    }

    private List<Long> collectMembersExceptCurrentMember(final Long memberId) {
        String sql = "SELECT DISTINCT member_id FROM member_surveys WHERE member_id != ?";
        return jdbcTemplate.queryForList(sql, new Object[]{memberId}, Long.class);
    }

    private boolean isMatched(final List<MemberSurveyResponse> memberResponse, final List<MemberSurveyResponse> otherResponse) {
        int matchedSurveyAnswer = 0;
        for (MemberSurveyResponse member : memberResponse) {
            for (MemberSurveyResponse other : otherResponse) {
                if (isSameAnswer(member, other)) {
                    matchedSurveyAnswer++;
                }
            }
        }
        return matchedSurveyAnswer >= MINIMUM_MATCH_SIZE;
    }

    private static boolean isSameAnswer(final MemberSurveyResponse member, final MemberSurveyResponse other) {
        return member.surveyId().equals(other.surveyId()) &&
                member.questionId().equals(other.questionId()) &&
                member.answerNumber().equals(other.answerNumber());
    }
}
