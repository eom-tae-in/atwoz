package com.atwoz.member.infrastructure.selfintro;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.selfintro.SelfIntro;
import com.atwoz.member.domain.selfintro.SelfIntroRepository;
import com.atwoz.member.infrastructure.selfintro.dto.SelfIntroResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static com.atwoz.member.fixture.member.MemberFixture.일반_유저_생성;
import static com.atwoz.member.fixture.selfintro.SelfIntroFixture.셀프_소개글_생성_id_없음;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SelfIntroQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private SelfIntroQueryRepository selfIntroQueryRepository;

    @Autowired
    private SelfIntroRepository selfIntroRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setup() {
        member = memberRepository.save(일반_유저_생성());
    }

    @Test
    void 셀프_소개글을_최근_생성된_기준으로_10개_페이징해서_조회한다() {
        // given
        List<SelfIntro> selfIntros = new ArrayList<>();
        saveSelfIntros(25, selfIntros, member.getId());
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<SelfIntroResponse> selfIntroResponses = selfIntroQueryRepository.findAllSelfIntroWithPaging(
                pageRequest, member.getId());

        // then
        List<SelfIntroResponse> results = selfIntroResponses.getContent();
        List<SelfIntro> expectedResults = selfIntros.stream()
                .sorted(Comparator.comparing(SelfIntro::getCreatedAt).reversed())
                .limit(10)
                .toList();

        assertSoftly(softly -> {
            softly.assertThat(selfIntroResponses.hasNext()).isTrue();
            softly.assertThat(selfIntroResponses.getTotalElements()).isEqualTo(25);
            softly.assertThat(selfIntroResponses.getTotalPages()).isEqualTo(3);
            softly.assertThat(selfIntroResponses).hasSize(10);
            IntStream.range(0, 10)
                    .forEach(index -> {
                        SelfIntro expectResult = expectedResults.get(index);
                        SelfIntroResponse result = results.get(index);
                        softly.assertThat(expectResult.getId()).isEqualTo(result.selfIntroId());
                        softly.assertThat(expectResult.getContent()).isEqualTo(result.selfIntroContent());
                    });
        });
    }

    @Test
    void 셀프_소개글을_여러가지_기준으로_필터링한_후_10개씩_페이징해서_조회한다() {
        // given
        Member member1 = 일반_유저_생성(1990, "01022222222");
        Member member2 = 일반_유저_생성(2000, "01033333333");
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<SelfIntro> selfIntros = new ArrayList<>();
        saveSelfIntros(15, selfIntros, member1.getId());
        saveSelfIntros(25, selfIntros, member2.getId());
        int minAge = 30;
        int maxAge = 40;
        boolean isOnlyOppositeGender = false;
        List<String> cities = List.of("서울시");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<SelfIntroResponse> selfIntroResponses = selfIntroQueryRepository.findAllSelfIntrosWithPagingAndFiltering(
                pageRequest, minAge, maxAge, isOnlyOppositeGender, cities, member1.getId());

        // then
        List<SelfIntroResponse> results = selfIntroResponses.getContent();
        List<SelfIntro> expectedResults = selfIntros.stream()
                .filter(selfIntro -> selfIntro.getMemberId().equals(member1.getId()))
                .sorted(Comparator.comparing(SelfIntro::getCreatedAt).reversed())
                .limit(10)
                .toList();

        assertSoftly(softly -> {
            softly.assertThat(selfIntroResponses.hasNext()).isTrue();
            softly.assertThat(selfIntroResponses.getTotalElements()).isEqualTo(15);
            softly.assertThat(selfIntroResponses.getTotalPages()).isEqualTo(2);
            softly.assertThat(selfIntroResponses).hasSize(10);
            IntStream.range(0, 10)
                    .forEach(index -> {
                        SelfIntro expectResult = expectedResults.get(index);
                        SelfIntroResponse result = results.get(index);
                        softly.assertThat(expectResult.getId()).isEqualTo(result.selfIntroId());
                        softly.assertThat(expectResult.getContent()).isEqualTo(result.selfIntroContent());
                    });
        });
    }

    private void saveSelfIntros(final int endExclusive,
                                final List<SelfIntro> selfIntros,
                                final Long memberId) {
        IntStream.range(0, endExclusive)
                .forEach(i -> selfIntros.add(selfIntroRepository.save(셀프_소개글_생성_id_없음(memberId))));
    }
}
