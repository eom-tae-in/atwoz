package com.atwoz.hobby.infrasturcture;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.hobby.domain.Hobby;
import com.atwoz.hobby.domain.HobbyRepository;
import com.atwoz.hobby.infrasturcture.dto.HobbyPagingResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.atwoz.hobby.fixture.취미_응답_픽스처.취미_페이징_조회_응답_픽스처.취미_페이징_조회_응답_생성_취미;
import static com.atwoz.hobby.fixture.취미_픽스처.취미_생성_이름_코드;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class HobbyQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private HobbyQueryRepository hobbyQueryRepository;

    private List<Hobby> hobbies;

    @BeforeEach
    void setup() {
        hobbies = 취미_목록_저장();
    }

    @Test
    void 취미를_페이징해서_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<HobbyPagingResponse> result = hobbyQueryRepository.findHobbiesWithPaging(pageable);

        // then
        List<HobbyPagingResponse> hobbyPagingRespons = result.getContent();
        assertSoftly(softly -> {
            softly.assertThat(result.hasNext()).isTrue();
            softly.assertThat(result.getTotalElements()).isEqualTo(15);
            softly.assertThat(result.getTotalPages()).isEqualTo(2);
            softly.assertThat(result.getNumber()).isEqualTo(0);
            softly.assertThat(result).hasSize(10);
            IntStream.range(0, 10)
                    .forEach(index -> {
                        HobbyPagingResponse hobbyPagingResponse = hobbyPagingRespons.get(index);
                        softly.assertThat(hobbyPagingResponse)
                                .usingRecursiveComparison()
                                .isEqualTo(취미_페이징_조회_응답_생성_취미(hobbies.get(index)));
                    });
        });
    }

    private List<Hobby> 취미_목록_저장() {
        List<Hobby> savedHobbies = new ArrayList<>();
        IntStream.range(0, 15)
                .forEach(index -> {
                    Hobby hobby = 취미_생성_이름_코드("hobby" + index, "code" + index);
                    hobbyRepository.save(hobby);
                    savedHobbies.add(hobby);
                });

        return savedHobbies;
    }
}

