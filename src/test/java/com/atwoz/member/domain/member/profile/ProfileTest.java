package com.atwoz.member.domain.member.profile;

import com.atwoz.member.domain.member.dto.initial.InternalProfileInitializeRequest;
import com.atwoz.member.domain.member.dto.update.InternalProfileUpdateRequest;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import com.atwoz.member.infrastructure.member.physical.FakeYearManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.member.dto.request.InternalProfileInitializeRequestFixture.내부_프로필_초기화_요청_생성_연도관리자;
import static com.atwoz.member.fixture.member.dto.request.InternalProfileUpdateRequestFixture.내부_프로필_업데이트_요청_생성_연도관리자;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProfileTest {

    private Profile profile;
    private YearManager yearManager;

    @BeforeEach
    void setup() {
        profile = Profile.createWith("남성");
        yearManager = new FakeYearManager();
    }

    @Test
    void 프로필_정보가_유효하면_값이_초기화된다() {
        // given
        InternalProfileInitializeRequest initializeRequest = 내부_프로필_초기화_요청_생성_연도관리자(yearManager);

        // when
        profile.initialize(initializeRequest);

        // then
        assertSoftly(softly -> {
            softly.assertThat(profile.getJob().getCode()).isEqualTo(initializeRequest.job());
            softly.assertThat(profile.getGraduate().getName()).isEqualTo(initializeRequest.graduate());
            softly.assertThat(profile.getSmoke().getName()).isEqualTo(initializeRequest.smoke());
            softly.assertThat(profile.getDrink().getName()).isEqualTo(initializeRequest.drink());
            softly.assertThat(profile.getReligion().getName()).isEqualTo(initializeRequest.religion());
            softly.assertThat(profile.getMbti().name()).isEqualTo(initializeRequest.mbti());
            softly.assertThat(profile.getLocation().getCity()).isEqualTo(initializeRequest.city());
            softly.assertThat(profile.getLocation().getSector()).isEqualTo(initializeRequest.sector());
            softly.assertThat(profile.getPhysicalProfile()).isNotNull();
            softly.assertThat(profile.getMemberHobbies()).isNotNull();
            softly.assertThat(profile.getMemberStyles()).isNotNull();
        });
    }

    @Test
    void 프로필_정보가_유효하면_값이_변경된다() {
        // given
        InternalProfileUpdateRequest updateRequest = 내부_프로필_업데이트_요청_생성_연도관리자(yearManager);

        // when
        profile.update(updateRequest);

        // then
        assertSoftly(softly -> {
            softly.assertThat(profile.getJob().getCode()).isEqualTo(updateRequest.job());
            softly.assertThat(profile.getGraduate().getName()).isEqualTo(updateRequest.graduate());
            softly.assertThat(profile.getSmoke().getName()).isEqualTo(updateRequest.smoke());
            softly.assertThat(profile.getDrink().getName()).isEqualTo(updateRequest.drink());
            softly.assertThat(profile.getReligion().getName()).isEqualTo(updateRequest.religion());
            softly.assertThat(profile.getMbti().name()).isEqualTo(updateRequest.mbti());
            softly.assertThat(profile.getLocation().getCity()).isEqualTo(updateRequest.city());
            softly.assertThat(profile.getLocation().getSector()).isEqualTo(updateRequest.sector());
            softly.assertThat(profile.getPhysicalProfile()).isNotNull();
            softly.assertThat(profile.getMemberHobbies()).isNotNull();
            softly.assertThat(profile.getMemberStyles()).isNotNull();
        });
    }
}
