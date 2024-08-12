package com.atwoz.member.application.member;

import com.atwoz.member.application.member.dto.initial.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.update.MemberUpdateRequest;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.exception.exceptions.member.MemberAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.MemberNicknameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.MemberNotFoundException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.InvalidHobbyException;
import com.atwoz.member.exception.exceptions.member.profile.style.InvalidStyleException;
import com.atwoz.member.fixture.member.domain.MemberFixture;
import com.atwoz.member.fixture.member.generator.UniqueMemberFieldsGenerator;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import com.atwoz.member.infrastructure.member.physical.FakeYearManager;
import com.atwoz.member.infrastructure.member.profile.hobby.HobbyFakeRepository;
import com.atwoz.member.infrastructure.member.profile.style.StyleFakeRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임;
import static com.atwoz.member.fixture.member.dto.request.MemberInitializeRequestFixture.회원_초기화_요청;
import static com.atwoz.member.fixture.member.dto.request.MemberInitializeRequestFixture.회원_초기화_요청_닉네임;
import static com.atwoz.member.fixture.member.dto.request.MemberInitializeRequestFixture.회원_초기화_요청_닉네임_추천인_닉네임;
import static com.atwoz.member.fixture.member.dto.request.MemberInitializeRequestFixture.회원_초기화_요청_닉네임_취미코드목록_스타일코드목록;
import static com.atwoz.member.fixture.member.dto.request.MemberUpdateRequestFixture.회원_업데이트_요청;
import static com.atwoz.member.fixture.member.dto.request.MemberUpdateRequestFixture.회원_업데이트_요청_닉네임;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_목록_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_목록_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private MemberRepository memberRepository;

    private MemberService memberService;

    private Member member;

    private UniqueMemberFieldsGenerator uniqueMemberFieldsGenerator;

    @BeforeEach
    void init() {
        memberRepository = new MemberFakeRepository();
        HobbyRepository hobbyRepository = new HobbyFakeRepository();
        StyleRepository styleRepository = new StyleFakeRepository();
        취미_목록_생성(hobbyRepository);
        스타일_목록_생성(styleRepository);
        memberService = new MemberService(
                memberRepository,
                hobbyRepository,
                styleRepository,
                new FakeYearManager()
        );
        member = memberRepository.save(MemberFixture.회원_생성());
        uniqueMemberFieldsGenerator = new UniqueMemberFieldsGenerator();
    }

    @Nested
    class 회원_생성 {

        @Test
        void 인증_완료된_유저의_전화번호와_일치하는_회원이_없으면_저장한다() {
            // given
            String uniquePhoneNumber = "01012345678";

            // when
            memberService.create(uniquePhoneNumber);

            // then
            Optional<Member> member = memberRepository.findByPhoneNumber(uniquePhoneNumber);
            assertSoftly(softly -> {
                softly.assertThat(member).isPresent();
                Member foundMember = member.get();
                softly.assertThat(foundMember.getPhoneNumber()).isEqualTo(uniquePhoneNumber);
            });
        }

        @Test
        void 이미_가입된_회원이_다시_가입_요청을_하면_예외가_발생한다() {
            // given
            String phoneNumber = member.getPhoneNumber();

            // when & then
            assertThatThrownBy(() -> memberService.create(phoneNumber))
                    .isInstanceOf(MemberAlreadyExistedException.class);
        }
    }

    @Nested
    class 회원_정보_초기화 {

        @Test
        void 회원_정보_초기화_요청을_보낸_대상이_존재하지_않으면_예외가_발생한다() {
            // given
            Long notExistMemberId = member.getId() + 1L;
            MemberInitializeRequest memberInitializeRequest = 회원_초기화_요청();

            // when & then
            assertThatThrownBy(() -> memberService.initializeMember(notExistMemberId, memberInitializeRequest))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 회원의_닉네임이_중복된_닉네임이면_예외가_발생한다() {
            // given
            Member memberWithoutProfile = memberRepository.save(프로필_정보가_없는_회원_생성());
            Long memberId = memberWithoutProfile.getId();
            MemberInitializeRequest memberInitializeRequest = 회원_초기화_요청_닉네임(member.getNickname());

            // when & then
            assertThatThrownBy(() -> memberService.initializeMember(memberId, memberInitializeRequest))
                    .isInstanceOf(MemberNicknameAlreadyExistedException.class);
        }

        @Test
        void 취미코드_정보가_존재하지_않는_코드면_예외가_발생한다() {
            // given
            Member memberWithoutProfile = memberRepository.save(프로필_정보가_없는_회원_생성());
            Long memberId = memberWithoutProfile.getId();
            List<String> invalidHobbyCodes = List.of("invalidHobbyCode1", "invalidHobbyCode1");
            List<String> validStyleCodes = List.of("C001", "C002");
            MemberInitializeRequest memberInitializeRequest = 회원_초기화_요청_닉네임_취미코드목록_스타일코드목록(
                    uniqueMemberFieldsGenerator.generateNickname(),
                    invalidHobbyCodes,
                    validStyleCodes
            );

            // when & then
            assertThatThrownBy(() -> memberService.initializeMember(memberId, memberInitializeRequest))
                    .isInstanceOf(InvalidHobbyException.class);
        }

        @Test
        void 스타일코드_정보가_존재하지_않는_코드면_예외가_발생한다() {
            // given
            Member memberWithoutProfile = memberRepository.save(프로필_정보가_없는_회원_생성());
            Long memberId = memberWithoutProfile.getId();
            List<String> validHobbyCodes = List.of("code1", "code2");
            List<String> inValidStyleCodes = List.of("invalidStyleCode1", "invalidStyleCode2");
            MemberInitializeRequest memberInitializeRequest = 회원_초기화_요청_닉네임_취미코드목록_스타일코드목록(
                    uniqueMemberFieldsGenerator.generateNickname(),
                    validHobbyCodes,
                    inValidStyleCodes
            );

            // when & then
            assertThatThrownBy(() -> memberService.initializeMember(memberId, memberInitializeRequest))
                    .isInstanceOf(InvalidStyleException.class);
        }

        @Test
        void 추천인_닉네임이_존재하지_않는_닉네임이면_예외가_발생한다() {
            // given
            Member memberWithoutProfile = memberRepository.save(프로필_정보가_없는_회원_생성());
            Long memberId = memberWithoutProfile.getId();
            String nickname = uniqueMemberFieldsGenerator.generateNickname();
            MemberInitializeRequest memberInitializeRequest = 회원_초기화_요청_닉네임_추천인_닉네임(nickname, "recommenderNickname");

            // when & then
            assertThatThrownBy(() -> memberService.initializeMember(memberId, memberInitializeRequest))
                    .isInstanceOf(MemberNotFoundException.class);
        }
    }

    @Nested
    class 회원_정보_수정 {

        @Test
        void 회원_정보_수정_요청을_보낸_대상이_존재하지_않으면_예외가_발생한다() {
            // given
            Long notExistMemberId = member.getId() + 1L;
            MemberUpdateRequest memberUpdateRequest = 회원_업데이트_요청();

            // when & then
            assertThatThrownBy(() -> memberService.updateMember(notExistMemberId, memberUpdateRequest))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 회원의_닉네임이_중복된_닉네임이면_예외가_발생한다() {
            // given
            Member newMember = memberRepository.save(새로운_회원_생성());
            Long newMemberId = newMember.getId();
            MemberUpdateRequest memberUpdateRequest = 회원_업데이트_요청_닉네임(member.getNickname());

            // when & then
            assertThatThrownBy(() -> memberService.updateMember(newMemberId, memberUpdateRequest))
                    .isInstanceOf(MemberNicknameAlreadyExistedException.class);
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

    private Member 프로필_정보가_없는_회원_생성() {
        return Member.createWithOAuth(uniqueMemberFieldsGenerator.generatePhoneNumber());
    }

    private Member 새로운_회원_생성() {
        return 회원_생성_닉네임(uniqueMemberFieldsGenerator.generateNickname());
    }
}

