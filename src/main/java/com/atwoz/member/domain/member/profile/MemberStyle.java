package com.atwoz.member.domain.member.profile;

import com.atwoz.member.domain.member.profile.vo.Style;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Style style;

    public static MemberStyle createWith(final String styleCode) {
        return MemberStyle.builder()
                .style(Style.findByCode(styleCode))
                .build();
    }

    public boolean hasMatchingStyleCodeOf(final List<String> styleCodes) {
        return styleCodes.contains(style.getCode());
    }

    public boolean isSame(final MemberStyle memberStyle) {
        return this.style.equals(memberStyle.style);
    }
}
