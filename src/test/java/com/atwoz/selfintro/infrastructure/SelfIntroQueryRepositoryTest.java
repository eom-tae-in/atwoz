//package com.atwoz.selfintro.infrastructure;
//
//import com.atwoz.helper.IntegrationHelper;
//import com.atwoz.member.domain.member.Member;
//import com.atwoz.member.domain.member.MemberRepository;
//import com.atwoz.global.fixture.PhoneNumberGenerator;
//import com.atwoz.profile.domain.ProfileRepository;
//import com.atwoz.selfintro.application.dto.SelfIntroFilterRequest;
//import com.atwoz.selfintro.domain.SelfIntro;
//import com.atwoz.selfintro.domain.SelfIntroRepository;
//import com.atwoz.selfintro.infrastructure.dto.SelfIntroQueryResponse;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.IntStream;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
//import static com.atwoz.member.fixture.member.회원_픽스처.회원_생성_전화번호;
//import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성_회원아이디_생년월일;
//import static com.atwoz.selfintro.fixture.셀프소개글_요청_픽스처.셀프소개글_필터_요청_픽스처.셀프소개글_필터_요청서;
//import static com.atwoz.selfintro.fixture.셀프소개글_픽스처.셀프소개글_생성_회원아이디;
//import static org.assertj.core.api.SoftAssertions.assertSoftly;
//
//@DisplayNameGeneration(ReplaceUnderscores.class)
//@SuppressWarnings("NonAsciiCharacters")
//class SelfIntroQueryRepositoryTest extends IntegrationHelper {
//
//    @Autowired
//    private SelfIntroQueryRepository selfIntroQueryRepository;
//
//    @Autowired
//    private SelfIntroRepository selfIntroRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private ProfileRepository profileRepository;
//
//    private PhoneNumberGenerator phoneNumberGenerator;
//
//    private Member member;
//
//    // TODO: 이 부분 수정해야함
//    @BeforeEach
//    void setup() {
//        phoneNumberGenerator = new PhoneNumberGenerator();
//        member = memberRepository.save(회원_생성_전화번호(phoneNumberGenerator.generatePhoneNumber()));
//    }
//
//    @Test
//    void 셀프_소개글을_최근_생성된_기준으로_10개_페이징해서_조회한다() {
//        // given
//        List<SelfIntro> selfIntros = new ArrayList<>();
//        saveSelfIntros(25, selfIntros, member.getId());
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        SelfIntroFilterRequest selfIntroFilterRequest = 셀프소개글_필터_요청서();
//
//        // when
//        Page<SelfIntroQueryResponse> selfIntroResponses = selfIntroQueryRepository.findAllSelfIntrosWithPagingAndFiltering(
//                pageRequest,
//                selfIntroFilterRequest,
//                member.getId()
//        );
//
//        // then
//        List<SelfIntroQueryResponse> results = selfIntroResponses.getContent();
//        List<SelfIntro> expectedResults = selfIntros.stream()
//                .sorted(Comparator.comparing(SelfIntro::getCreatedAt).reversed())
//                .limit(10)
//                .toList();
//        assertSoftly(softly -> {
//            softly.assertThat(selfIntroResponses.hasNext()).isTrue();
//            softly.assertThat(selfIntroResponses.getTotalElements()).isEqualTo(25);
//            softly.assertThat(selfIntroResponses.getTotalPages()).isEqualTo(3);
//            softly.assertThat(selfIntroResponses).hasSize(10);
//            IntStream.range(0, 10)
//                    .forEach(index -> {
//                        SelfIntro expectResult = expectedResults.get(index);
//                        SelfIntroQueryResponse result = results.get(index);
//                        softly.assertThat(expectResult.getId()).isEqualTo(result.selfIntroId());
//                        softly.assertThat(expectResult.getContent()).isEqualTo(result.selfIntroContent());
//                    });
//        });
//    }
//
//    @Test
//    void 셀프_소개글을_여러가지_기준으로_필터링한_후_10개씩_페이징해서_조회한다() {
//        // given
//        Member primaryMember = memberRepository.save(회원_생성_전화번호(phoneNumberGenerator.generatePhoneNumber()));
//        Member secondaryMember = memberRepository.save(회원_생성_전화번호(phoneNumberGenerator.generatePhoneNumber()));
//        profileRepository.save(프로필_생성_회원아이디_생년월일(primaryMember.getId(), 1990));
//        profileRepository.save(프로필_생성_회원아이디_생년월일(secondaryMember.getId(), 2000));
//        List<SelfIntro> selfIntros = new ArrayList<>();
//        saveSelfIntros(15, selfIntros, primaryMember.getId());
//        saveSelfIntros(25, selfIntros, secondaryMember.getId());
//        SelfIntroFilterRequest selfIntroFilterRequest = 셀프소개글_필터_요청서();
//        PageRequest pageRequest = PageRequest.of(0, 10);
//
//        // when
//        Page<SelfIntroQueryResponse> selfIntroResponses = selfIntroQueryRepository.findAllSelfIntrosWithPagingAndFiltering(
//                pageRequest,
//                selfIntroFilterRequest,
//                primaryMember.getId()
//        );
//
//        // then
//        List<SelfIntroQueryResponse> results = selfIntroResponses.getContent();
//        List<SelfIntro> expectedResults = selfIntros.stream()
//                .filter(selfIntro -> selfIntro.getMemberId().equals(primaryMember.getId()))
//                .sorted(Comparator.comparing(SelfIntro::getCreatedAt).reversed())
//                .limit(10)
//                .toList();
//
//        assertSoftly(softly -> {
//            softly.assertThat(selfIntroResponses.hasNext()).isTrue();
//            softly.assertThat(selfIntroResponses.getTotalElements()).isEqualTo(15);
//            softly.assertThat(selfIntroResponses.getTotalPages()).isEqualTo(2);
//            softly.assertThat(selfIntroResponses).hasSize(10);
//            IntStream.range(0, 10)
//                    .forEach(index -> {
//                        SelfIntro expectResult = expectedResults.get(index);
//                        SelfIntroQueryResponse result = results.get(index);
//                        softly.assertThat(expectResult.getId()).isEqualTo(result.selfIntroId());
//                        softly.assertThat(expectResult.getContent()).isEqualTo(result.selfIntroContent());
//                    });
//        });
//    }
//
//    private void saveSelfIntros(
//            final int endExclusive,
//            final List<SelfIntro> selfIntros,
//            final Long memberId
//    ) {
//        IntStream.range(0, endExclusive)
//                .forEach(i -> selfIntros.add(selfIntroRepository.save(셀프소개글_생성_회원아이디(memberId))));
//    }
//}
