package com.atwoz.hobby.application;

import com.atwoz.hobby.application.dto.HobbyCreateRequest;
import com.atwoz.hobby.application.dto.HobbyUpdateRequest;
import com.atwoz.hobby.domain.Hobby;
import com.atwoz.hobby.domain.HobbyRepository;
import com.atwoz.hobby.exception.exceptions.HobbyCodeAlreadyExistedException;
import com.atwoz.hobby.exception.exceptions.HobbyNameAlreadyExistedException;
import com.atwoz.hobby.exception.exceptions.HobbyNotFoundException;
import com.atwoz.hobby.infrasturcture.HobbyFakeRepository;
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

import static com.atwoz.hobby.fixture.취미_요청_픽스처.취미_생성_요청_픽스처.취미_생성_요청_생성;
import static com.atwoz.hobby.fixture.취미_요청_픽스처.취미_생성_요청_픽스처.취미_생성_요청_생성_이름_코드;
import static com.atwoz.hobby.fixture.취미_요청_픽스처.취미_수정_요청_픽스처.취미_업데이트_요청_생성_이름_코드;
import static com.atwoz.hobby.fixture.취미_픽스처.취미_생성;
import static com.atwoz.hobby.fixture.취미_픽스처.취미_생성_이름_코드;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class HobbyServiceTest {

    private HobbyService hobbyService;
    private HobbyRepository hobbyRepository;

    @BeforeEach
    void setup() {
        hobbyRepository = new HobbyFakeRepository();
        hobbyService = new HobbyService(hobbyRepository);
    }

    @Nested
    class 취미_저장 {

        @Test
        void 취미를_저장한다() {
            // given
            HobbyCreateRequest hobbyCreateRequest = 취미_생성_요청_생성();
            Long hobbyId = 1L;

            // when
            hobbyService.saveHobby(hobbyCreateRequest);
            Optional<Hobby> foundHobby = hobbyRepository.findHobbyById(hobbyId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundHobby).isPresent();
                Hobby hobby = foundHobby.get();
                softly.assertThat(hobby.getName())
                        .isEqualTo(hobbyCreateRequest.name());
                softly.assertThat(hobby.getCode())
                        .isEqualTo(hobbyCreateRequest.code());
            });
        }

        @ParameterizedTest
        @MethodSource(value = "invalidHobbyCreateRequests")
        void 이미_존재하는_취미_이름으로_취미를_저장_할_경우_예외가_발생한다(
                final String name,
                final String hobbyName,
                final String hobbyCode,
                final Class<Exception> exception
        ) {
            // given
            hobbyRepository.save(취미_생성());
            HobbyCreateRequest hobbyCreateRequest = 취미_생성_요청_생성_이름_코드(hobbyName, hobbyCode);

            // when & then
            assertThatThrownBy(() -> hobbyService.saveHobby(hobbyCreateRequest))
                    .isInstanceOf(exception);
        }

        private static Stream<Arguments> invalidHobbyCreateRequests() {
            return Stream.of(
                    Arguments.of(
                            "이미 존재하는 취미 이름으로 생성할 경우",
                            "hobby1",
                            "code2",
                            HobbyNameAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "이미 존재하는 취미 코드로 생성할 경우",
                            "hobby2",
                            "code1",
                            HobbyCodeAlreadyExistedException.class
                    )
            );
        }
    }

    @Nested
    class 취미_변경 {

        @Test
        void 취미를_변경한다() {
            // given
            Hobby savedHobby = hobbyRepository.save(취미_생성());
            HobbyUpdateRequest hobbyUpdateRequest = 취미_업데이트_요청_생성_이름_코드(
                    savedHobby.getName() + "1",
                    savedHobby.getCode() + "1"
            );
            Long hobbyId = savedHobby.getId();

            // when
            hobbyService.updateHobby(hobbyId, hobbyUpdateRequest);
            Optional<Hobby> foundHobby = hobbyRepository.findHobbyById(hobbyId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundHobby)
                        .isPresent();
                Hobby hobby = foundHobby.get();
                softly.assertThat(hobby.getName())
                        .isEqualTo(hobbyUpdateRequest.name());
                softly.assertThat(hobby.getCode())
                        .isEqualTo(hobbyUpdateRequest.code());
            });
        }

        @ParameterizedTest
        @MethodSource(value = "invalidHobbyUpdateRequests")
        void 기존의_취미_이름_또는_코드가_변경이_없거나_이미_존재하는_값으로_변경할_경우_예외가_발생한다(
                final String name,
                final String hobbyName,
                final String hobbyCode,
                final Class<Exception> exception
        ) {
            // given
            hobbyRepository.save(취미_생성());
            Hobby savedHobby = hobbyRepository.save(취미_생성_이름_코드("newHobby", "newCode"));
            HobbyUpdateRequest hobbyUpdateRequest = 취미_업데이트_요청_생성_이름_코드(hobbyName, hobbyCode);
            Long hobbyId = savedHobby.getId();

            // when & then
            assertThatThrownBy(() -> hobbyService.updateHobby(hobbyId, hobbyUpdateRequest))
                    .isInstanceOf(exception);
        }

        private static Stream<Arguments> invalidHobbyUpdateRequests() {
            return Stream.of(
                    Arguments.of(
                            "변경하려는 취미 이름이 기존의 취미 이름과 동일한 경우",
                            "newHobby",
                            "code2",
                            HobbyNameAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "이미 존재하는 취미 이름으로 변경할 경우",
                            "hobby1",
                            "code2",
                            HobbyNameAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "변경하려는 취미 코드가 기존의 취미 코드와 동일한 경우",
                            "hobby2",
                            "newCode",
                            HobbyCodeAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "이미 존재하는 취미 코드로 변경할 경우",
                            "hobby2",
                            "code1",
                            HobbyCodeAlreadyExistedException.class
                    )
            );
        }
    }

    @Nested
    class 취미_삭제 {

        @Test
        void 취미를_삭제한다() {
            // given
            Hobby savedHobby = hobbyRepository.save(취미_생성());
            Long hobbyId = savedHobby.getId();

            // when
            hobbyService.deleteHobby(hobbyId);

            // then
            assertThat(hobbyRepository.findHobbyById(hobbyId)).isEmpty();
        }

        @Test
        void 존재하지_않는_취미를_삭제할_경우_예외가_발생한다() {
            // given
            Long notExistHobbyId = 1L;

            // when & then
            assertThatThrownBy(() -> hobbyService.deleteHobby(notExistHobbyId))
                    .isInstanceOf(HobbyNotFoundException.class);
        }
    }
}
