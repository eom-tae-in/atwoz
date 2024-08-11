package com.atwoz.member.domain.member.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class MemberStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "style_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Style style;

    public static MemberStyle createWith(final Style style) {
        return MemberStyle.builder()
                .style(style)
                .build();
    }

    public boolean isSame(final MemberStyle memberStyle) {
        return this.equals(memberStyle);
    }
}
