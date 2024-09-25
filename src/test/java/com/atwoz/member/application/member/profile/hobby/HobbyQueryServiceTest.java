package com.atwoz.member.application.member.profile.hobby;

import com.atwoz.member.application.member.profile.hobby.dto.HobbyPagingResponses;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.exception.exceptions.member.profile.hobby.HobbyNotFoundException;
import com.atwoz.member.infrastructure.member.profile.hobby.HobbyFakeRepository;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyPagingResponse;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbySingleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.atwoz.member.fixture.member.domain.HobbyFixture.취미_생성;
import static com.atwoz.member.fixture.member.dto.response.hobby.취미_응답_픽스처.취미_단건_조회_응답_픽스처.취미_단건_조회_응답_생성_취미;
import static com.atwoz.member.fixture.member.dto.response.hobby.취미_응답_픽스처.취미_페이징_조회_응답_픽스처.취미_페이징_조회_응답_생성_취미;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class HobbyQueryServiceTest {

    private HobbyQueryService hobbyQueryService;
    private HobbyRepository hobbyRepository;

    @BeforeEach
    void setup() {
        hobbyRepository = new HobbyFakeRepository();
        hobbyQueryService = new HobbyQueryService(hobbyRepository);
    }

    @Nested
    class 취미_조회 {

        @Test
        void 취미를_조회한다() {
            // given
            Hobby savedHobby = hobbyRepository.save(취미_생성());
            Long hobbyId = savedHobby.getId();

            // when
            HobbySingleResponse response = hobbyQueryService.findHobby(hobbyId);

            // then
            HobbySingleResponse expectedResponse = 취미_단건_조회_응답_생성_취미(savedHobby);
            assertThat(response).usingRecursiveComparison()
                    .isEqualTo(expectedResponse);
        }

        @Test
        void 없는_취미를_조회할_경우_예외가_발생한다() {
            // given
            Long invalidHobbyId = 1L;

            // when & then
            assertThatThrownBy(() -> hobbyQueryService.findHobby(invalidHobbyId))
                    .isInstanceOf(HobbyNotFoundException.class);
        }
    }

    @Test
    void 취미를_페이징으로_조회한다() {
        // given
        Hobby savedHobby = hobbyRepository.save(취미_생성());
        Pageable pageable = PageRequest.of(0, 10);

        // when
        HobbyPagingResponses hobbyPagingResponses = hobbyQueryService.findHobbiesWithPaging(pageable);

        // then
        assertSoftly(softly -> {
            softly.assertThat(hobbyPagingResponses.nowPage()).isEqualTo(0);
            softly.assertThat(hobbyPagingResponses.nextPage()).isEqualTo(-1);
            softly.assertThat(hobbyPagingResponses.totalPages()).isEqualTo(1);
            softly.assertThat(hobbyPagingResponses.totalElements()).isEqualTo(1);
            HobbyPagingResponse hobbyPagingResponse = hobbyPagingResponses.hobbyPagingResponses().get(0);
            HobbyPagingResponse expectedResponse = 취미_페이징_조회_응답_생성_취미(savedHobby);
            softly.assertThat(hobbyPagingResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedResponse);
        });
    }
}
