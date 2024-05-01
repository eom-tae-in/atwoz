package com.atwoz.member.fixture;

import com.atwoz.member.domain.member.dto.HobbiesDto;
import com.atwoz.member.domain.member.dto.MemberProfileDto;
import com.atwoz.member.domain.member.dto.PhysicalProfileDto;
import com.atwoz.member.domain.member.dto.StylesDto;
import com.atwoz.member.infrastructure.member.physical.FakeYearManager;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class MemberProfileDtoFixture {

    public static MemberProfileDto 회원_프로핑_DTO_요청() {
        return MemberProfileDto.builder()
                .physicalProfileDto(new PhysicalProfileDto(2000, 170, new FakeYearManager()))
                .hobbiesdto(new HobbiesDto(List.of("B001", "B002")))
                .stylesDto(new StylesDto(List.of("C001", "C002")))
                .city("서울시")
                .sector("강남구")
                .job("A001")
                .graduate("서울 4년제")
                .smoke("비흡연")
                .drink("전혀 마시지 않음")
                .religion("기독교")
                .mbti("INFP")
                .build();
    }
}
