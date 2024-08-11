package com.atwoz.member.domain.member.dto.initial;

import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.MemberHobby;
import java.util.List;

public record InternalHobbiesInitializeRequest(
        List<MemberHobby> memberHobbies
) {

    public static InternalHobbiesInitializeRequest from(final List<Hobby> hobbies) {
        return new InternalHobbiesInitializeRequest(convertToMemberHobbies(hobbies));
    }

    private static List<MemberHobby> convertToMemberHobbies(final List<Hobby> hobbies) {
        return hobbies.stream()
                .map(MemberHobby::createWith)
                .toList();
    }
}
