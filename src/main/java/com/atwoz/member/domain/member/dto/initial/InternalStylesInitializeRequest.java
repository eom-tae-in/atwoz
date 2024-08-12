package com.atwoz.member.domain.member.dto.initial;

import com.atwoz.member.domain.member.profile.MemberStyle;
import com.atwoz.member.domain.member.profile.Style;
import java.util.List;

public record InternalStylesInitializeRequest(
        List<MemberStyle> memberStyles
) {

    public static InternalStylesInitializeRequest from(final List<Style> styles) {
        return new InternalStylesInitializeRequest(convertToMemberStyles(styles));
    }

    private static List<MemberStyle> convertToMemberStyles(final List<Style> styles) {
        return styles.stream()
                .map(MemberStyle::createWith)
                .toList();
    }
}
