package com.atwoz.member.infrastructure.member.dto;

import com.atwoz.member.domain.member.profile.vo.Hobby;
import java.util.List;

public record HobbiesResponse(
        List<String> hobbies
) {

    public static HobbiesResponse createWith(final List<Hobby> hobbies) {
        return new HobbiesResponse(hobbies.stream()
                .map(Hobby::getName)
                .toList());
    }
}
