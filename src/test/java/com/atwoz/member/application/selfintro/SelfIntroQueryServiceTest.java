package com.atwoz.member.application.selfintro;

import com.atwoz.member.application.selfintro.dto.SelfIntroFilterRequest;
import com.atwoz.member.application.selfintro.dto.SelfIntrosResponse;
import com.atwoz.member.domain.selfintro.SelfIntro;
import com.atwoz.member.domain.selfintro.SelfIntroRepository;
import com.atwoz.member.infrastructure.selfintro.SelfIntroFakeRepository;
import com.atwoz.member.infrastructure.selfintro.dto.SelfIntroResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import static com.atwoz.member.fixture.selfintro.SelfIntroFixture.셀프_소개글_생성_id_있음;
import static com.atwoz.member.fixture.selfintro.SelfIntroRequestFixture.셀프_소개글_필터_요청서;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SelfIntroQueryServiceTest {

    private SelfIntroQueryService selfIntroQueryService;
    private SelfIntroRepository selfIntroRepository;

    @BeforeEach
    void setup() {
        selfIntroRepository = new SelfIntroFakeRepository();
        selfIntroQueryService = new SelfIntroQueryService(selfIntroRepository);
    }

    @Test
    void 셀프_소개글을_페이징으로_조회한다() {
        // given
        SelfIntro selfIntro = 셀프_소개글_생성_id_있음(1L);
        PageRequest pageRequest = PageRequest.of(0, 10);
        selfIntroRepository.save(selfIntro);

        // when
        SelfIntrosResponse result = selfIntroQueryService.findAllSelfIntrosWithPaging(pageRequest);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.nowPage()).isEqualTo(0);
            softly.assertThat(result.nextPage()).isEqualTo(-1);
            softly.assertThat(result.totalPages()).isEqualTo(1);
            softly.assertThat(result.totalElements()).isEqualTo(1);
            SelfIntroResponse selfIntroResponse = result.selfIntros().get(0);
            softly.assertThat(selfIntroResponse.selfIntroId()).isEqualTo(selfIntro.getId());
            softly.assertThat(selfIntroResponse.selfIntroContent()).isEqualTo(selfIntro.getContent());
        });
    }

    @Test
    void 필터를_적용한_셀프_소개글을_페이징으로_조회한다() {
        // given
        SelfIntro selfIntro = 셀프_소개글_생성_id_있음(1L);
        SelfIntroFilterRequest selfIntroFilterRequest = 셀프_소개글_필터_요청서();
        PageRequest pageRequest = PageRequest.of(0, 10);
        selfIntroRepository.save(selfIntro);

        // when
        SelfIntrosResponse result = selfIntroQueryService.findAllSelfIntrosWithPagingAndFiltering(pageRequest,
                selfIntroFilterRequest, selfIntro.getMemberId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.nowPage()).isEqualTo(0);
            softly.assertThat(result.nextPage()).isEqualTo(-1);
            softly.assertThat(result.totalPages()).isEqualTo(1);
            SelfIntroResponse selfIntroResponse = result.selfIntros().get(0);
            softly.assertThat(selfIntroResponse.selfIntroId()).isEqualTo(selfIntro.getId());
            softly.assertThat(selfIntroResponse.selfIntroContent()).isEqualTo(selfIntro.getContent());
        });
    }
}
