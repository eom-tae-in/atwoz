package com.atwoz.admin.infrastructure.admin;

import com.atwoz.admin.domain.admin.Admin;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.atwoz.admin.fixture.AdminFixture.관리자_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class AdminJpaRepositoryTest {

    @Autowired
    private AdminJpaRepository adminJpaRepository;

    private Admin admin;

    @BeforeEach
    void setup() {
        admin = 관리자_생성();
        adminJpaRepository.save(admin);
    }

    @Nested
    class 회원_조회 {

        @Test
        void 아이디로_관리자를_찾는다() {
            // given
            Long adminId = admin.getId();

            // when
            Optional<Admin> foundAdmin = adminJpaRepository.findById(adminId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundAdmin).isPresent();
                softly.assertThat(foundAdmin.get()).usingRecursiveComparison().isEqualTo(admin);
            });
        }

        @Test
        void 이메일로_관리자를_찾는다() {
            // given
            String adminEmail = admin.getEmail();

            // when
            Optional<Admin> foundAdmin = adminJpaRepository.findAdminByEmail(adminEmail);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundAdmin).isPresent();
                softly.assertThat(foundAdmin.get()).usingRecursiveComparison().isEqualTo(admin);
            });
        }
    }
}
