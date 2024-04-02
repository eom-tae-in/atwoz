package com.atwoz.member.application.member;

import com.atwoz.member.application.member.dto.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.MemberNicknameRequest;
import com.atwoz.member.application.member.dto.MemberUpdateRequest;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.exception.exceptions.member.MemberNicknameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.MemberNotFoundException;
import com.atwoz.member.fixture.MemberRequestFixture;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import com.atwoz.member.infrastructure.member.physical_profile.FakeYearManager;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.atwoz.member.fixture.MemberRequestFixture.회원_정보_수정_요청서_요청;
import static com.atwoz.member.fixture.MemberRequestFixture.회원_정보_초기화_요청서_요청;
import static com.atwoz.member.fixture.domain.member.MemberFixture.PASS_인증만_완료한_유저_생성;
import static com.atwoz.member.fixture.domain.member.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private MemberRepository memberRepository;
    private MemberService memberService;


    @BeforeEach
    void init() {
        memberRepository = new MemberFakeRepository();
        memberService = new MemberService(memberRepository, new FakeYearManager());
    }

    @Test
    void 인증_완료된_유저의_전화번호와_일치하는_회원이_없으면_저장한다() {
        // given
        String uniquePhoneNumber = "01012345678";

        // when
        memberService.create(uniquePhoneNumber);

        // then
        Optional<Member> member = memberRepository.findByPhoneNumber(uniquePhoneNumber);
        assertSoftly(softly -> {
            softly.assertThat(member.isPresent()).isTrue();
            Member foundMember = member.get();
            softly.assertThat(foundMember.getPhoneNumber()).isEqualTo(uniquePhoneNumber);
        });
    }

    @DisplayName("중복된_닉네임이_존재하는지_확인한다")
    @Nested
    class MemberNicknameValidation {

        @Test
        void 중복된_닉네임이_존재하는지_않으면_예외가_발생하지_않는다() {
            // given
            MemberNicknameRequest memberNicknameRequest = new MemberNicknameRequest("uniqueNickname");

            // when & then
            assertDoesNotThrow(() -> memberService.checkMemberExists(memberNicknameRequest));
        }

        @Test
        void 중복된_닉네임이_존재하면_예외가_발생한다() {
            // given
            memberRepository.save(일반_유저_생성());
            MemberNicknameRequest memberNicknameRequest = new MemberNicknameRequest("nickname");

            // when & then
            assertThatThrownBy(() -> memberService.checkMemberExists(memberNicknameRequest))
                    .isInstanceOf(MemberNicknameAlreadyExistedException.class);
        }
    }

    @DisplayName("회원 정보를 초기화한다")
    @Nested
    class MemberInitialization {

        @Test
        void 회원_정보_초기화_요청을_보낸_대상이_존재하지_않으면_예외가_발생한다() {
            // given
            Long notExistMemberId = 1L;
            MemberInitializeRequest memberInitializeRequest = 회원_정보_초기화_요청서_요청();

            // when & then
            assertThatThrownBy(() -> memberService.initializeMember(notExistMemberId, memberInitializeRequest))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 회원의_닉네임이_중복된_닉네임이면_예외가_발생한다() {
            // given
            memberRepository.save(일반_유저_생성());
            Member member = memberRepository.save(PASS_인증만_완료한_유저_생성());
            MemberInitializeRequest memberInitializeRequest = 회원_정보_초기화_요청서_요청();

            // when & then
            assertThatThrownBy(() -> memberService.initializeMember(member.getId(), memberInitializeRequest))
                    .isInstanceOf(MemberNicknameAlreadyExistedException.class);
        }

        @Test
        void 추천인_닉네임이_존재하지_않는_닉네임이면_예외가_발생한다() {
            // given
            Long memberId = 1L;
            MemberInitializeRequest memberInitializeRequest = 회원_정보_초기화_요청서_요청();

            // when & then
            assertThatThrownBy(() -> memberService.initializeMember(memberId, memberInitializeRequest))
                    .isInstanceOf(MemberNotFoundException.class);
        }
    }

    @DisplayName("회원 정보를 수정한다")
    @Nested
    class MemberModification {

        @Test
        void 회원_정보_수정_요청을_보낸_대상이_존재하지_않으면_예외가_발생한다() {
            // given
            Long notExistMemberId = 1L;
            MemberUpdateRequest memberUpdateRequest = MemberRequestFixture.회원_정보_수정_요청서_요청();

            // when & then
            assertThatThrownBy(() -> memberService.updateMember(notExistMemberId, memberUpdateRequest))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 회원의_닉네임이_중복된_닉네임이면_예외가_발생한다() {
            // given
            memberRepository.save(일반_유저_생성());
            Member member = memberRepository.save(일반_유저_생성("uniqueNickname", "01022222222"));

            MemberUpdateRequest memberUpdateRequest = 회원_정보_수정_요청서_요청("nickname");

            // when & then
            assertThatThrownBy(() -> memberService.updateMember(member.getId(), memberUpdateRequest))
                    .isInstanceOf(MemberNicknameAlreadyExistedException.class);
        }
    }

    @DisplayName("회원을 삭제한다")
    @Nested
    class MemberDeletion {

        @Test
        void 회원_삭제_요청을_보낸_대상이_존재하지_않으면_예외가_발생한다() {
            // given
            Long notExistMemberId = 1L;

            // when & then
            assertThatThrownBy(() -> memberService.deleteMember(notExistMemberId))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 회원을_삭제한다() {
            // given
            Member member = memberRepository.save(일반_유저_생성());

            // when
            memberService.deleteMember(member.getId());
            Optional<Member> optionalMember = memberRepository.findById(member.getId());

            // then
            assertThat(optionalMember).isNotPresent();
        }
    }
}

