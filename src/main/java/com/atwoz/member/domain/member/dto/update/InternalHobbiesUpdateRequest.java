package com.atwoz.member.domain.member.dto.update;

import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.MemberHobby;
import java.util.List;

public record InternalHobbiesUpdateRequest(
        List<MemberHobby> hobbies
) {

    public static InternalHobbiesUpdateRequest from(final List<Hobby> hobbies) {
        return new InternalHobbiesUpdateRequest(convertToMemberHobbies(hobbies));
    }

    private static List<MemberHobby> convertToMemberHobbies(final List<Hobby> hobbies) {
        return hobbies.stream()
                .map(MemberHobby::createWith)
                .toList();
    }
}
