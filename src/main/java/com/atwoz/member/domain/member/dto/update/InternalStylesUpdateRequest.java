package com.atwoz.member.domain.member.dto.update;

import com.atwoz.member.domain.member.profile.MemberStyle;
import com.atwoz.member.domain.member.profile.Style;
import java.util.List;

public record InternalStylesUpdateRequest(
        List<MemberStyle> styles
) {

    public static InternalStylesUpdateRequest from(final List<Style> styles) {
        return new InternalStylesUpdateRequest(convertToMemberStyles(styles));
    }

    private static List<MemberStyle> convertToMemberStyles(final List<Style> hobbies) {
        return hobbies.stream()
                .map(MemberStyle::createWith)
                .toList();
    }
}
