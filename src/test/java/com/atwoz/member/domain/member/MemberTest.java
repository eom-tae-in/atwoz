package com.atwoz.member.domain.member;

import com.atwoz.global.fixture.PhoneNumberGenerator;
import com.atwoz.member.domain.member.vo.MemberAccountStatus;
import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import com.atwoz.member.infrastructure.member.dto.MemberNotificationsResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_계정_상태_조회_응답_픽스처.회원_계정_상태_조회_응답_생성_회원계정상태;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_연락처_정보_조회_응답_픽스처.회원_연락처_정보_조회_응답_생성_연락처;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_푸시_알림_조회_응답_픽스처.회원_푸시_알림_조회_응답_생성_회원푸시알림목록;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberTest {

    private Member member;

    private PhoneNumberGenerator phoneNumberGenerator;

    // TODO: 아직 미흡한 테스트가 있음. 인증 기능 구현 완료시 최신화 진행

    @BeforeEach
    void setup() {
        member = Member.createWithOAuth("01011111111");
        phoneNumberGenerator = new PhoneNumberGenerator();
    }


    @Nested
    class 회원_정보_변경 {

        @Test
        void 회원의_푸시_알림_정보를_변경한다() {
            // given
            boolean isLikeReceivedNotificationOn = true;
            boolean isNewMessageNotificationOn = true;
            boolean isProfileExchangeNotificationOn = true;
            boolean isProfileImageChangeNotificationOn = true;
            boolean isLongTimeLoLoginNotificationOn = true;
            boolean isInterviewWritingRequestNotificationOn = true;
            MemberNotificationsResponse before = 회원_푸시_알림_조회_응답_생성_회원푸시알림목록(member.getMemberPushNotifications());

            // when
            member.updatePushNotifications(
                    isLikeReceivedNotificationOn,
                    isNewMessageNotificationOn,
                    isProfileExchangeNotificationOn,
                    isProfileImageChangeNotificationOn,
                    isLongTimeLoLoginNotificationOn,
                    isInterviewWritingRequestNotificationOn
            );

            // then
            MemberNotificationsResponse after = 회원_푸시_알림_조회_응답_생성_회원푸시알림목록(member.getMemberPushNotifications());

            assertSoftly(softly -> {
                softly.assertThat(after).usingRecursiveComparison()
                        .isNotEqualTo(before);
                softly.assertThat(after.isLikeReceivedNotificationOn())
                        .isEqualTo(isLikeReceivedNotificationOn);
                softly.assertThat(after.isNewMessageNotificationOn())
                        .isEqualTo(isNewMessageNotificationOn);
                softly.assertThat(after.isProfileExchangeNotificationOn())
                        .isEqualTo(isProfileExchangeNotificationOn);
                softly.assertThat(after.isProfileImageChangeNotificationOn())
                        .isEqualTo(isProfileImageChangeNotificationOn);
                softly.assertThat(after.isLongTimeLoLoginNotificationOn())
                        .isEqualTo(isLongTimeLoLoginNotificationOn);
                softly.assertThat(after.isInterviewWritingRequestNotificationOn())
                        .isEqualTo(isInterviewWritingRequestNotificationOn);
            });
        }

        @Test
        void 회원의_계정_상태_정보를_변경한다() {
            // given
            MemberAccountStatusResponse before = 회원_계정_상태_조회_응답_생성_회원계정상태(member.getMemberAccountStatus());
            String accessStatus = MemberAccountStatus.INACTIVE.getStatus();

            // when
            member.updateAccountStatus(accessStatus);

            // then
            MemberAccountStatusResponse after = 회원_계정_상태_조회_응답_생성_회원계정상태(member.getMemberAccountStatus());
            assertSoftly(softly -> {
                softly.assertThat(after).usingRecursiveComparison()
                        .isNotEqualTo(before);
                softly.assertThat(after.status()).isEqualTo(accessStatus);
            });
        }

        @Test
        void 회원의_연락처_정보를_변경한다() {
            // given
            MemberContactInfoResponse before = 회원_연락처_정보_조회_응답_생성_연락처(member.getContact());
            String contactType = "카카오톡 아이디";
            String contactValue = "atwoz";

            // when
            member.updateContact(contactType, contactValue);

            // then
            MemberContactInfoResponse after = 회원_연락처_정보_조회_응답_생성_연락처(member.getContact());
            assertSoftly(softly -> {
                softly.assertThat(after).usingRecursiveComparison()
                        .isNotEqualTo(before);
                softly.assertThat(after.contactType()).isEqualTo(contactType);
                softly.assertThat(after.contactValue()).isEqualTo(contactValue);
            });
        }

        @Test
        void 회원의_최신_방문_일을_변경한다() {
            // when
            member.updateLastVisitDate();

            // then
            assertThat(member.getLatestVisitDate()).isEqualTo(LocalDate.now());
        }
    }
}
