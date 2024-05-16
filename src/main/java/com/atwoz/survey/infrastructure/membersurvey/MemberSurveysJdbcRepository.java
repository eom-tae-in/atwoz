package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyAnswerResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyQuestionResponse;
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
        List<MemberSurveyAnswerResponse> memberSurveyAnswerResponse = convertMemberSurveys(memberId);
        List<Long> result = new ArrayList<>();

        List<Long> otherMembers = collectMembersExceptCurrentMember(memberId);
        otherMembers.forEach(id -> {
            List<MemberSurveyAnswerResponse> otherMemberSurveyAnswerResponse = convertMemberSurveys(id);
            if (isMatched(memberSurveyAnswerResponse, otherMemberSurveyAnswerResponse)) {
                result.add(id);
            }
        });
        return result;
    }

    private List<MemberSurveyAnswerResponse> convertMemberSurveys(final Long memberId) {
        String sql = "SELECT mss.member_id, ms.survey_id, ms.question_id, ms.answer_number " +
                "FROM member_surveys mss " +
                "INNER JOIN member_survey ms " +
                "ON mss.id = ms.member_surveys_id " +
                "WHERE mss.member_id = ?";
        return jdbcTemplate.query(sql, new Object[]{memberId},
                (rs, rowNum) -> new MemberSurveyAnswerResponse(
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

    private boolean isMatched(final List<MemberSurveyAnswerResponse> memberResponse, final List<MemberSurveyAnswerResponse> otherResponse) {
        int matchedSurveyAnswer = 0;
        for (MemberSurveyAnswerResponse member : memberResponse) {
            for (MemberSurveyAnswerResponse other : otherResponse) {
                if (isSameAnswer(member, other)) {
                    matchedSurveyAnswer++;
                }
            }
        }
        return matchedSurveyAnswer >= MINIMUM_MATCH_SIZE;
    }

    private static boolean isSameAnswer(final MemberSurveyAnswerResponse member, final MemberSurveyAnswerResponse other) {
        return member.surveyId().equals(other.surveyId()) &&
                member.questionId().equals(other.questionId()) &&
                member.answerNumber().equals(other.answerNumber());
    }

    public MemberSurveyResponse findMemberSurvey(final Long memberId, final Long surveyId) {
        String sql = "SELECT ms.survey_id, ms.question_id, ms.answer_number FROM member_surveys mss " +
                "INNER JOIN member_survey ms " +
                "ON mss.id = ms.member_surveys_id " +
                "WHERE mss.member_id = ? " +
                "AND ms.survey_id = ?";

        List<MemberSurveyQuestionResponse> questions = jdbcTemplate.query(sql, new Object[]{memberId, surveyId},
                (rs, rowNum) -> new MemberSurveyQuestionResponse(
                        rs.getLong("question_id"),
                        rs.getInt("answer_number")
                )
        );

        return new MemberSurveyResponse(surveyId, questions);
    }
}
