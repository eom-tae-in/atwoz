package com.atwoz.member.application.selfintro;

import com.atwoz.member.application.selfintro.dto.SelfIntroCreateRequest;
import com.atwoz.member.application.selfintro.dto.SelfIntroUpdateRequest;
import com.atwoz.member.domain.selfintro.SelfIntro;
import com.atwoz.member.domain.selfintro.SelfIntroRepository;
import com.atwoz.member.exception.exceptions.selfintro.SelfIntroNotFoundException;
import com.atwoz.member.infrastructure.selfintro.SelfIntroFakeRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.selfintro.SelfIntroFixture.셀프_소개글_생성_id_있음;
import static com.atwoz.member.fixture.selfintro.SelfIntroRequestFixture.셀프_소개글_생성_요청서;
import static com.atwoz.member.fixture.selfintro.SelfIntroRequestFixture.셀프_소개글_수정_요청서;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SelfIntroServiceTest {

    private SelfIntroService selfIntroService;
    private SelfIntroRepository selfIntroRepository;

    @BeforeEach
    void setup() {
        selfIntroRepository = new SelfIntroFakeRepository();
        selfIntroService = new SelfIntroService(selfIntroRepository);
    }

    @Test
    void 셀프_소개글을_저장한다() {
        // given
        SelfIntroCreateRequest selfIntroCreateRequest = 셀프_소개글_생성_요청서();
        Long memberId = 1L;

        // when
        selfIntroService.saveSelfIntro(selfIntroCreateRequest, memberId);
        Optional<SelfIntro> foundSelfIntro = selfIntroRepository.findById(1L);

        // then
        assertSoftly(softly -> {
            softly.assertThat(foundSelfIntro).isNotEmpty();
            SelfIntro selfIntro = foundSelfIntro.get();
            softly.assertThat(selfIntro.getContent()).isEqualTo(selfIntroCreateRequest.content());
        });
    }

    @Nested
    class 셀프_소개글_수정 {

        @Test
        void 셀프_소개글을_수정한다() {
            // given
            SelfIntro createdSelfIntro = 셀프_소개글_생성_id_있음(1L);
            selfIntroRepository.save(createdSelfIntro);
            SelfIntroUpdateRequest selfIntroUpdateRequest = 셀프_소개글_수정_요청서();

            // when
            selfIntroService.updateSelfIntro(selfIntroUpdateRequest, createdSelfIntro.getId(),
                    createdSelfIntro.getMemberId());
            Optional<SelfIntro> foundSelfIntro = selfIntroRepository.findById(createdSelfIntro.getId());

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundSelfIntro).isNotEmpty();
                SelfIntro selfIntro = foundSelfIntro.get();
                softly.assertThat(selfIntro).usingRecursiveComparison()
                        .isEqualTo(selfIntro);
            });
        }

        @Test
        void 수정하려는_셀프_소개글이_존재하지_않으면_예외가_발생한다() {
            // given
            SelfIntro createdSelfIntro = 셀프_소개글_생성_id_있음(1L);
            selfIntroRepository.save(createdSelfIntro);
            SelfIntroUpdateRequest selfIntroUpdateRequest = 셀프_소개글_수정_요청서();
            Long inValidSelfIntroId = 2L;

            // when & then
            assertThatThrownBy(() -> selfIntroService.updateSelfIntro(selfIntroUpdateRequest, inValidSelfIntroId,
                    createdSelfIntro.getMemberId())).isInstanceOf(SelfIntroNotFoundException.class);
        }
    }

    @Nested
    class 셀프_소개글_삭제 {

        @Test
        void 셀프_소개글을_삭제한다() {
            // given
            SelfIntro createdSelfIntro = 셀프_소개글_생성_id_있음(1L);
            selfIntroRepository.save(createdSelfIntro);

            // when
            selfIntroService.deleteSelfIntro(createdSelfIntro.getId(), createdSelfIntro.getMemberId());

            // then
            assertThat(selfIntroRepository.findById(createdSelfIntro.getId())).isEmpty();
        }

        @Test
        void 삭제하려는_셀프_소개글이_존재하지_않으면_예외가_발생한다() {
            SelfIntro createdSelfIntro = 셀프_소개글_생성_id_있음(1L);
            selfIntroRepository.save(createdSelfIntro);
            Long inValidSelfIntroId = 2L;

            // when & then
            assertThatThrownBy(() -> selfIntroService.deleteSelfIntro(inValidSelfIntroId, createdSelfIntro.getId()))
                    .isInstanceOf(SelfIntroNotFoundException.class);
        }
    }
}
