package com.atwoz.member.application.member;

import com.atwoz.global.fixture.PhoneNumberGenerator;
import com.atwoz.member.application.member.dto.MemberContactInfoDuplicationCheckResponse;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.vo.Contact;
import com.atwoz.member.exception.exceptions.member.MemberContactInfoAlreadyExistedException;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import com.atwoz.member.infrastructure.member.dto.MemberNotificationsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_계정_상태_조회_응답_픽스처.회원_계정_상태_조회_응답_생성_회원계정상태;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_연락처_정보_조회_응답_픽스처.회원_연락처_정보_조회_응답_생성_연락처;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_푸시_알림_조회_응답_픽스처.회원_푸시_알림_조회_응답_생성_회원푸시알림목록;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberQueryServiceTest {

    private MemberQueryService memberQueryService;

    private Member member;

    private PhoneNumberGenerator phoneNumberGenerator;

    @BeforeEach
    void setup() {
        MemberRepository memberRepository = new MemberFakeRepository();
        memberQueryService = new MemberQueryService(memberRepository);
        member = memberRepository.save(Member.createWithOAuth("01011111111"));
        phoneNumberGenerator = new PhoneNumberGenerator();
    }


    @Nested
    class 회원_절보_조회 {

        @Test
        void 회원의_푸시_알림_정보를_조회한다() {
            // given
            Long memberId = member.getId();

            // when
            MemberNotificationsResponse response = memberQueryService.findMemberNotifications(memberId);

            // then
            assertThat(response).usingRecursiveComparison()
                    .isEqualTo(회원_푸시_알림_조회_응답_생성_회원푸시알림목록(member.getMemberPushNotifications()));
        }

        @Test
        void 회원의_계정_상태_정보를_조회한다() {
            // given
            Long memberId = member.getId();

            // when
            MemberAccountStatusResponse response = memberQueryService.findMemberAccountStatus(memberId);

            // then
            assertThat(response).usingRecursiveComparison()
                    .isEqualTo(회원_계정_상태_조회_응답_생성_회원계정상태(member.getMemberAccountStatus()));
        }

        @Test
        void 회원의_연락처_정보를_조회한다() {
            // given
            Long memberId = member.getId();

            // when
            MemberContactInfoResponse response = memberQueryService.findMemberContactInfo(memberId);

            // then
            assertThat(response).usingRecursiveComparison()
                    .isEqualTo(회원_연락처_정보_조회_응답_생성_연락처(member.getContact()));
        }
    }

    @Nested
    class 회원_연락처_중복_검증 {

        @Test
        void 회원이_사용하려는_연락처_정보가_이미_존재하는_경우_예외를_반환한다() {
            // given
            Contact alreadyExistContact = member.getContact();

            // when & then
            assertThatThrownBy(() -> memberQueryService.checkContactInfoDuplicated(
                    alreadyExistContact.getContactType().getType(),
                    alreadyExistContact.getContactValue()
            )).isInstanceOf(MemberContactInfoAlreadyExistedException.class);
        }

        @Test
        void 회원이_사용하려는_연락처_정보가_존재하지_않는_경우_false를_반환한다() {
            // given
            Contact newContact = Contact.createWith("카카오톡 아이디", "atwoz");

            // when
            MemberContactInfoDuplicationCheckResponse response = memberQueryService.checkContactInfoDuplicated(
                    newContact.getContactType().getType(),
                    newContact.getContactValue()
            );

            // then
            assertThat(response.isDuplicated()).isFalse();
        }
    }
}
