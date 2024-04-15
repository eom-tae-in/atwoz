package com.atwoz.mission.intrastructure.membermission;

import com.atwoz.mission.domain.membermission.MemberMissions;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.atwoz.mission.fixture.MemberMissionsFixture.멤버_미션들_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class MemberMissionsJpaRepositoryTest {

    @Autowired
    private MemberMissionsJpaRepository memberMissionsJpaRepository;

    @Test
    void 회원_미션_목록_생성() {
        // given
        MemberMissions memberMissions = 멤버_미션들_생성();

        // when
        MemberMissions result = memberMissionsJpaRepository.save(memberMissions);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(memberMissions);
    }

    @Test
    void 회원_미션_목록_조회() {
        // given
        Long memberId = 1L;
        MemberMissions memberMissions = 멤버_미션들_생성();
        MemberMissions saveMemberMissions = memberMissionsJpaRepository.save(memberMissions);

        // when
        Optional<MemberMissions> result = memberMissionsJpaRepository.findByMemberId(memberId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result.get()).usingRecursiveComparison()
                    .ignoringFieldsOfTypes(LocalDateTime.class)
                    .isEqualTo(saveMemberMissions);
        });
    }
}
