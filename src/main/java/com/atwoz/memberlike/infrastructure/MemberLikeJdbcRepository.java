package com.atwoz.memberlike.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberLikeJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void deleteExpiredLikes() {
        String sql = "DELETE FROM MemberLike"
                + " WHERE updated_at < TIMESTAMPADD(DAY, -30, CURRENT_DATE)" +
                " AND like_icon = 'NONE'";

        jdbcTemplate.update(sql);
    }
}
