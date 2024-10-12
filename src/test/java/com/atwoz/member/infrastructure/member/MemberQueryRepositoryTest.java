package com.atwoz.member.infrastructure.member;

import com.atwoz.global.fixture.PhoneNumberGenerator;
import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import com.atwoz.member.infrastructure.member.dto.MemberNotificationsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_계정_상태_조회_응답_픽스처.회원_계정_상태_조회_응답_생성_회원계정상태;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_연락처_정보_조회_응답_픽스처.회원_연락처_정보_조회_응답_생성_연락처;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_푸시_알림_조회_응답_픽스처.회원_푸시_알림_조회_응답_생성_회원푸시알림목록;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    private Member member;

    private PhoneNumberGenerator phoneNumberGenerator;

    @BeforeEach
    void setup() {
        member = Member.createWithOAuth("01011111111");
        memberRepository.save(member);
        phoneNumberGenerator = new PhoneNumberGenerator();
    }

    @Nested
    class 회원_정보_조회 {

        @Test
        void 회원의_푸시_알림_정보를_조회한다() {
            // given
            Long memberId = member.getId();

            // when
            MemberNotificationsResponse response = memberQueryRepository.findMemberNotifications(memberId);

            // then
            assertThat(response).usingRecursiveComparison()
                    .isEqualTo(회원_푸시_알림_조회_응답_생성_회원푸시알림목록(member.getMemberPushNotifications()));
        }

        @Test
        void 회원의_계정_상태_정보를_조회한다() {
            // given
            Long memberId = member.getId();

            // when
            MemberAccountStatusResponse response = memberQueryRepository.findMemberAccountStatus(memberId);

            // then
            assertThat(response).usingRecursiveComparison()
                    .isEqualTo(회원_계정_상태_조회_응답_생성_회원계정상태(member.getMemberAccountStatus()));
        }

        @Test
        void 회원의_연락처_정보를_조회한다() {
            // given
            Long memberId = member.getId();

            // when
            MemberContactInfoResponse response = memberQueryRepository.findMemberContactInfo(memberId);

            // then
            assertThat(response).usingRecursiveComparison()
                    .isEqualTo(회원_연락처_정보_조회_응답_생성_연락처(member.getContact()));
        }
    }
}
