package com.atwoz.selfintro.application;

import com.atwoz.selfintro.application.dto.SelfIntroFilterRequest;
import com.atwoz.selfintro.application.dto.SelfIntroQueryResponses;
import com.atwoz.selfintro.domain.SelfIntro;
import com.atwoz.selfintro.domain.SelfIntroRepository;
import com.atwoz.selfintro.infrastructure.SelfIntroFakeRepository;
import com.atwoz.selfintro.infrastructure.dto.SelfIntroQueryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import static com.atwoz.selfintro.fixture.셀프소개글_요청_픽스처.셀프소개글_필터_요청_픽스처.셀프소개글_필터_요청서;
import static com.atwoz.selfintro.fixture.셀프소개글_픽스처.셀프소개글_생성_셀프소개글아이디;
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
    void 필터를_적용한_셀프_소개글을_페이징으로_조회한다() {
        // given
        SelfIntro selfIntro = 셀프소개글_생성_셀프소개글아이디(1L);
        SelfIntroFilterRequest selfIntroFilterRequest = 셀프소개글_필터_요청서();
        PageRequest pageRequest = PageRequest.of(0, 10);
        selfIntroRepository.save(selfIntro);

        // when
        SelfIntroQueryResponses result = selfIntroQueryService.findAllSelfIntrosWithPagingAndFiltering(pageRequest,
                selfIntroFilterRequest, selfIntro.getMemberId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.nowPage()).isEqualTo(0);
            softly.assertThat(result.nextPage()).isEqualTo(-1);
            softly.assertThat(result.totalPages()).isEqualTo(1);
            SelfIntroQueryResponse selfIntroQueryResponse = result.selfIntros().get(0);
            softly.assertThat(selfIntroQueryResponse.selfIntroId()).isEqualTo(selfIntro.getId());
            softly.assertThat(selfIntroQueryResponse.selfIntroContent()).isEqualTo(selfIntro.getContent());
        });
    }
}
