package com.atwoz.member.application.info;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.atwoz.member.application.event.HobbyWroteEvent;
import com.atwoz.member.application.event.OptionWroteEvent;
import com.atwoz.member.application.event.ProfileWroteEvent;
import com.atwoz.member.application.event.StyleWroteEvent;
import com.atwoz.member.application.info.dto.HobbyWriteRequest;
import com.atwoz.member.application.info.dto.InfoWriteRequest;
import com.atwoz.member.application.info.dto.StyleWriteRequest;
import com.atwoz.member.application.info.dto.option.OptionWriteRequest;
import com.atwoz.member.application.info.dto.profile.LocationWriteRequest;
import com.atwoz.member.application.info.dto.profile.PositionWriteRequest;
import com.atwoz.member.application.info.dto.profile.ProfileWriteRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import java.math.BigDecimal;
import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@RecordApplicationEvents
class InfoServiceTest {

    @Autowired
    private InfoService infoService;

    @Autowired
    private ApplicationEvents events;
    
    @Test
    void 모든_정보를_저장한다() {
        // given
        Long memberId = 1L;

        int birthYear = 2000;
        int height = 171;
        String gender = "남성";
        LocationWriteRequest location = new LocationWriteRequest("서울시", "강남구");
        BigDecimal latitude = BigDecimal.valueOf(70.5);
        BigDecimal longitude = BigDecimal.valueOf(140.3);
        PositionWriteRequest position = new PositionWriteRequest(latitude, longitude);
        String job = "A001";
        ProfileWriteRequest profileWriteRequest = new ProfileWriteRequest(
                birthYear,
                height,
                gender,
                location,
                position,
                job
        );

        String drink = "전혀 마시지 않음";
        String graduate = "서울 4년제";
        String religion = "기독교";
        String smoke = "비흡연";
        String mbti = "INFJ";
        OptionWriteRequest optionWriteRequest = new OptionWriteRequest(
                drink,
                graduate,
                religion,
                smoke,
                mbti
        );

        List<HobbyWriteRequest> hobbyWriteRequests = List.of(
            new HobbyWriteRequest("B006"),
            new HobbyWriteRequest("B007")
        );

        List<StyleWriteRequest> styleWriteRequests = List.of(
            new StyleWriteRequest("C012"),
            new StyleWriteRequest("C021")
        );

        InfoWriteRequest request = new InfoWriteRequest(
                profileWriteRequest,
                optionWriteRequest,
                hobbyWriteRequests,
                styleWriteRequests
        );


        // when
        infoService.writeInfo(memberId, request);

        // then
        int profileWroteEventCount = (int) events.stream(ProfileWroteEvent.class).count();
        int optionWroteEventCount = (int) events.stream(OptionWroteEvent.class).count();
        int hobbyWroteEventCount = (int) events.stream(HobbyWroteEvent.class).count();
        int styleWroteEventCount = (int) events.stream(StyleWroteEvent.class).count();

        assertSoftly(softly -> {
            softly.assertThat(profileWroteEventCount).isEqualTo(1);
            softly.assertThat(optionWroteEventCount).isEqualTo(1);
            softly.assertThat(hobbyWroteEventCount).isEqualTo(1);
            softly.assertThat(styleWroteEventCount).isEqualTo(1);
        });
    }
}
