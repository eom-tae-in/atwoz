package com.atwoz.member.application.member.profile.style;

import com.atwoz.member.application.member.profile.style.dto.StyleCreateRequest;
import com.atwoz.member.application.member.profile.style.dto.StyleUpdateRequest;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleCodeAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleNameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleNotFoundException;
import com.atwoz.member.infrastructure.member.profile.style.StyleFakeRepository;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.atwoz.member.fixture.member.domain.StyleFixture.스타일_생성;
import static com.atwoz.member.fixture.member.domain.StyleFixture.스타일_생성_이름_코드;
import static com.atwoz.member.fixture.member.dto.request.StyleCreateRequestFixture.스타일_생성_요청_생성;
import static com.atwoz.member.fixture.member.dto.request.StyleCreateRequestFixture.스타일_생성_요청_생성_이름_코드;
import static com.atwoz.member.fixture.member.dto.request.StyleUpdateRequestFixture.스타일_업데이트_요청_생성_이름_코드;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class StyleServiceTest {

    private StyleService styleService;
    private StyleRepository styleRepository;

    @BeforeEach
    void setup() {
        styleRepository = new StyleFakeRepository();
        styleService = new StyleService(styleRepository);
    }

    @Nested
    class 스타일_저장 {

        @Test
        void 스타일을_저장한다() {
            // given
            StyleCreateRequest styleCreateRequest = 스타일_생성_요청_생성();
            Long styleId = 1L;

            // when
            styleService.saveStyle(styleCreateRequest);
            Optional<Style> foundStyle = styleRepository.findStyleById(styleId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundStyle).isPresent();
                Style style = foundStyle.get();
                softly.assertThat(style.getName())
                        .isEqualTo(styleCreateRequest.name());
                softly.assertThat(style.getCode())
                        .isEqualTo(styleCreateRequest.code());
            });
        }

        @ParameterizedTest
        @MethodSource(value = "invalidStyleCreateRequests")
        void 이미_존재하는_스타일_이름으로_스타일_저장_할_경우_예외가_발생한다(
                final String name,
                final String styleName,
                final String styleCode,
                final Class<Exception> exception
        ) {
            // given
            styleRepository.save(스타일_생성());
            StyleCreateRequest styleCreateRequest = 스타일_생성_요청_생성_이름_코드(styleName, styleCode);

            // when & then
            assertThatThrownBy(() -> styleService.saveStyle(styleCreateRequest))
                    .isInstanceOf(exception);
        }

        private static Stream<Arguments> invalidStyleCreateRequests() {
            return Stream.of(
                    Arguments.of(
                            "이미 존재하는 스타일 이름으로 생성할 경우",
                            "style1",
                            "code2",
                            StyleNameAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "이미 존재하는 스타일 코드로 생성할 경우",
                            "style2",
                            "code1",
                            StyleCodeAlreadyExistedException.class
                    )
            );
        }
    }

    @Nested
    class 스타일_변경 {

        @Test
        void 스타일을_변경한다() {
            // given
            Style savedStyle = styleRepository.save(스타일_생성());
            StyleUpdateRequest styleUpdateRequest = 스타일_업데이트_요청_생성_이름_코드(
                    savedStyle.getName() + "1",
                    savedStyle.getCode() + "1"
            );
            Long styleId = savedStyle.getId();

            // when
            styleService.updateStyle(styleId, styleUpdateRequest);
            Optional<Style> foundStyle = styleRepository.findStyleById(styleId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundStyle)
                        .isPresent();
                Style style = foundStyle.get();
                softly.assertThat(style.getName())
                        .isEqualTo(styleUpdateRequest.name());
                softly.assertThat(style.getCode())
                        .isEqualTo(styleUpdateRequest.code());
            });
        }

        @ParameterizedTest
        @MethodSource(value = "invalidStyleUpdateRequests")
        void 기존의_스타일_이름_또는_코드가_변경이_없거나_이미_존재하는_값으로_변경할_경우_예외가_발생한다(
                final String name,
                final String styleName,
                final String styleCode,
                final Class<Exception> exception
        ) {
            // given
            styleRepository.save(스타일_생성());
            Style savedStyle = styleRepository.save(스타일_생성_이름_코드("newStyle", "newCode"));
            StyleUpdateRequest styleUpdateRequest = 스타일_업데이트_요청_생성_이름_코드(styleName, styleCode);
            Long styleId = savedStyle.getId();

            // when & then
            assertThatThrownBy(() -> styleService.updateStyle(styleId, styleUpdateRequest))
                    .isInstanceOf(exception);
        }

        private static Stream<Arguments> invalidStyleUpdateRequests() {
            return Stream.of(
                    Arguments.of(
                            "변경하려는 스타일 이름이 기존의 스타일 이름과 동일한 경우",
                            "newStyle",
                            "code2",
                            StyleNameAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "이미 존재하는 스타일 이름으로 변경할 경우",
                            "style1",
                            "code2",
                            StyleNameAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "변경하려는 스타일 코드가 기존의 스타일 코드와 동일한 경우",
                            "style2",
                            "newCode",
                            StyleCodeAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "이미 존재하는 스타일 코드로 변경할 경우",
                            "style2",
                            "code1",
                            StyleCodeAlreadyExistedException.class
                    )
            );
        }
    }

    @Nested
    class 스타일_삭제 {

        @Test
        void 스타일을_삭제한다() {
            // given
            Style savedStyle = styleRepository.save(스타일_생성());
            Long styleId = savedStyle.getId();

            // when
            styleService.deleteStyle(styleId);

            // then
            assertThat(styleRepository.findStyleById(styleId)).isEmpty();
        }

        @Test
        void 존재하지_않는_스타일을_삭제할_경우_예외가_발생한다() {
            // given
            Long notExistStyleId = 1L;

            // when & then
            assertThatThrownBy(() -> styleService.deleteStyle(notExistStyleId))
                    .isInstanceOf(StyleNotFoundException.class);
        }
    }
}
