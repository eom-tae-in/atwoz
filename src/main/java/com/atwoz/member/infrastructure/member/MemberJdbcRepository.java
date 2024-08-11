package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.profile.vo.Job;
import com.atwoz.member.infrastructure.member.dto.InternalProfileFilterRequest;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<ProfileResponse> findTodayProfiles(final InternalProfileFilterRequest request,
                                                   final Long memberId) {
        String foundGender = findGenderByMemberId(memberId);
        List<Long> memberHobbiesIds = findMemberHobbiesIdByHobbyCode(request.hobbyCode());
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String sql = "WITH find_today_profiles AS ( " +
                "    SELECT " +
                "        m.nickname, " +
                "        pp.age, " +
                "        p.city, " +
                "        p.sector, " +
                "        p.job, " +
                "        m.member_grade, " +
                "        ROW_NUMBER() OVER (PARTITION BY m.member_grade ORDER BY m.nickname) as rnk " +
                "    FROM member m " +
                "    JOIN member_profile mp ON mp.id = m.member_profile_id " +
                "    JOIN profile p ON p.id = mp.profile_id " +
                "    JOIN physical_profile pp ON pp.id = p.physical_profile_id " +
                "    WHERE m.member_grade IN ('GOLD', 'DIAMOND') " +
                addDefaultCondition() +
                addHobbyFilterCondition(request.hobbyCode(), memberHobbiesIds, parameters) +
                addFilterCondition(request, parameters) +
                ") " +
                "(SELECT DISTINCT nickname, age, city, sector, job " +
                "FROM find_today_profiles " +
                "WHERE member_grade = 'GOLD' " +
                "  AND rnk <= 2) " +
                "UNION ALL " +
                "(SELECT DISTINCT nickname, age, city, sector, job " +
                "FROM find_today_profiles " +
                "WHERE member_grade = 'DIAMOND' " +
                "  AND rnk = 1) ";
        parameters.addValue("gender", foundGender);
        List<ProfileResponse> results = namedParameterJdbcTemplate.query(sql, parameters,
                (rs, rowNum) -> ProfileResponse.builder()
                        .nickname(rs.getString("nickname"))
                        .age(rs.getInt("age"))
                        .city(rs.getString("city"))
                        .sector(rs.getString("sector"))
                        .jobCode(Job.valueOf(rs.getString("job"))
                                .getCode())
                        .build());
        if (results.isEmpty()) {
            return Collections.emptyList();
        }

        return results;
    }

    private String findGenderByMemberId(final Long memberId) {
        String sql = "SELECT pp.gender FROM member m " +
                "JOIN member_profile mp ON mp.id = m.member_profile_id " +
                "JOIN profile p ON p.id = mp.profile_id " +
                "JOIN physical_profile pp ON pp.id = p.physical_profile_id " +
                "WHERE m.id = :memberId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("memberId", memberId);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, String.class);
    }

    private List<Long> findMemberHobbiesIdByHobbyCode(final String hobbyCode) {
        if (hobbyCode == null || hobbyCode.isBlank()) {
            return Collections.emptyList();
        }
        String sql = "SELECT mh1.member_hobbies_id "
                + "FROM member_hobby mh1 "
                + "LEFT JOIN hobby h ON h.id = mh1.hobby_id "
                + "WHERE h.code = :hobbyCode ";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("hobbyCode", hobbyCode);

        return namedParameterJdbcTemplate.query(sql, parameters,
                (rs, rowNum) -> rs.getLong("member_hobbies_id"));
    }

    private String addDefaultCondition() {
        return " AND pp.gender <> :gender"
                + " AND p.profile_access_status = 'PUBLIC' "
                + " AND mp.profile_access_status = 'PUBLIC' ";
    }

    private String addHobbyFilterCondition(final String hobbyCode,
                                           final List<Long> memberHobbiesIds,
                                           final MapSqlParameterSource parameters) {
        if (hobbyCode == null || hobbyCode.isBlank()) {
            return "";
        }
        if (memberHobbiesIds.isEmpty()) {
            return " AND 1=2 ";
        }
        parameters.addValue("memberHobbiesIds", memberHobbiesIds);
        return " AND p.member_hobbies_id IN (:memberHobbiesIds) ";
    }

    private String addFilterCondition(final InternalProfileFilterRequest request,
                                      final MapSqlParameterSource parameters) {
        StringBuilder sqlBuilder = new StringBuilder();
        addNumberFilterCondition(sqlBuilder, parameters, "pp.age >= :minAge", "minAge", request.minAge());
        addNumberFilterCondition(sqlBuilder, parameters, "pp.age <= :maxAge", "maxAge", request.maxAge());
        addObjectFilterCondition(sqlBuilder, parameters, "p.smoke = :smoke", "smoke", request.smoke());
        addObjectFilterCondition(sqlBuilder, parameters, "p.drink = :drink", "drink", request.drink());
        addObjectFilterCondition(sqlBuilder, parameters, "p.religion = :religion", "religion", request.religion());
        addListFilterCondition(sqlBuilder, parameters, "p.city IN (:cities)", "cities", request.cities());

        return sqlBuilder.toString();
    }

    private void addNumberFilterCondition(final StringBuilder sqlBuilder,
                                          final MapSqlParameterSource parameters,
                                          final String condition,
                                          final String parameterName,
                                          final Number parameterValue) {
        if (parameterValue != null) {
            parameters.addValue(parameterName, parameterValue);
            sqlBuilder.append(" AND ")
                    .append(condition);
        }
    }

    private void addObjectFilterCondition(final StringBuilder sqlBuilder,
                                          final MapSqlParameterSource parameters,
                                          final String condition,
                                          final String parameterName,
                                          final Object parameterValue) {
        if (parameterValue != null) {
            parameters.addValue(parameterName, parameterValue.toString());
            sqlBuilder.append(" AND ")
                    .append(condition);
        }
    }

    private void addListFilterCondition(final StringBuilder sqlBuilder,
                                        final MapSqlParameterSource parameters,
                                        final String condition,
                                        final String parameterName,
                                        final List<String> parameterValues) {
        if (!parameterValues.isEmpty()) {
            parameters.addValue(parameterName, parameterValues);
            sqlBuilder.append(" AND ")
                    .append(condition);
        }
    }
}
