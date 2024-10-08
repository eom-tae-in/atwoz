package com.atwoz.member.application.member;

import com.atwoz.member.application.member.dto.MemberAccountStatusPatchRequest;
import com.atwoz.member.application.member.dto.MemberContactInfoPatchRequest;
import com.atwoz.member.application.member.dto.MemberNotificationsPatchRequest;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.vo.Contact;
import com.atwoz.member.exception.exceptions.member.MemberNotFoundException;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_계정_상태_변경_요청_픽스처.회원_계정_상태_변경_요청_생성;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_계정_상태_변경_요청_픽스처.회원_계정_상태_변경_요청_생성_회원계정상태;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_연락처_정보_변경_요청_픽스처.회원_연락처_정보_변경_요청_생성;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_연락처_정보_변경_요청_픽스처.회원_연락처_정보_변경_요청_생성_연락처;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_푸시_알림_변경_요청_픽스처.회원_푸시_알림_변경_요청_생성;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_푸시_알림_변경_요청_픽스처.회원_푸시_알림_변경_요청_생성_회원푸시알림목록;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private MemberRepository memberRepository;

    private MemberService memberService;

    private Member member;

    @BeforeEach
    void init() {
        memberRepository = new MemberFakeRepository();
        memberService = new MemberService(memberRepository);
        member = memberRepository.save(Member.createWithOAuth("01022222222"));
    }

    @Nested
    class 회원_생성 {
        // TODO: 우선 ContactType을 전화번호로 고정하고 테스트를 진행한다. 로그인 기능 구현되면 변경한다.
        @Test
        void 인증_완료된_유저의_전화번호와_일치하는_회원이_없으면_저장한다() {
            // given
            String uniquePhoneNumber = "01077777777";
            String contactType = "휴대폰 번호";

            // when
            memberService.create(uniquePhoneNumber);

            // then
            boolean result = memberRepository.existsByContact(Contact.createWith(contactType, uniquePhoneNumber));
            assertThat(result).isTrue();
        }

        // TODO: 추후에 NICE 인증 방식이 구현되면 예외 경우도 테스트 진행해야 함.
    }

    @Nested
    class 회원_정보_수정 {

        @Test
        void 회원의_푸시_알림_설정_정보를_변경한다() {
            // given
            Long memberId = member.getId();
            MemberNotificationsPatchRequest request = 회원_푸시_알림_변경_요청_생성();

            // when
            memberService.patchMemberNotifications(memberId, request);

            // then
            MemberNotificationsPatchRequest expectedResponse = 회원_푸시_알림_변경_요청_생성_회원푸시알림목록(
                    member.getMemberPushNotifications()
            );
            assertThat(expectedResponse).usingRecursiveComparison()
                    .isEqualTo(request);
        }

        @Test
        void 회원의_계정_상태_정보를_변경한다() {
            // given
            Long memberId = member.getId();
            MemberAccountStatusPatchRequest request = 회원_계정_상태_변경_요청_생성();

            // when
            memberService.patchMemberAccountStatus(memberId, request);

            // when & then
            MemberAccountStatusPatchRequest expectedResponse = 회원_계정_상태_변경_요청_생성_회원계정상태(
                    member.getMemberAccountStatus()
            );
            assertThat(expectedResponse).usingRecursiveComparison()
                    .isEqualTo(request);
        }

        @Test
        void 회원의_연락처_정보를_변경한다() {
            // given
            Long memberId = member.getId();
            MemberContactInfoPatchRequest request = 회원_연락처_정보_변경_요청_생성();

            // when
            memberService.patchMemberContact(memberId, request);

            // then
            MemberContactInfoPatchRequest expectedResponse = 회원_연락처_정보_변경_요청_생성_연락처(member.getContact());
            assertThat(expectedResponse).usingRecursiveComparison()
                    .isEqualTo(request);
        }

        @Test
        void 회원_정보_수정_요청을_한_회원_정보를_찾을_수_없는_경우_예외가_발생한다() {
            // given
            Long notExistMemberId = 999L;
            MemberContactInfoPatchRequest request = 회원_연락처_정보_변경_요청_생성();

            // when & then
            assertThatThrownBy(() -> memberService.patchMemberContact(notExistMemberId, request))
                    .isInstanceOf(MemberNotFoundException.class);
        }
    }

    @Nested
    class 회원_삭제 {

        @Test
        void 회원_삭제_요청을_보낸_대상이_존재하지_않으면_예외가_발생한다() {
            // given
            Long notExistMemberId = member.getId() + 1L;

            // when & then
            assertThatThrownBy(() -> memberService.deleteMember(notExistMemberId))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 회원을_삭제한다() {
            // when
            memberService.deleteMember(member.getId());
            Optional<Member> optionalMember = memberRepository.findById(member.getId());

            // then
            assertThat(optionalMember).isNotPresent();
        }
    }
}

