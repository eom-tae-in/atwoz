package com.atwoz.member.domain.member.profile;

import com.atwoz.member.domain.member.dto.initial.InternalStylesInitializeRequest;
import com.atwoz.member.domain.member.dto.update.InternalStylesUpdateRequest;
import com.atwoz.member.exception.exceptions.member.profile.style.MemberStyleDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.style.MemberStyleSizeException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class MemberStyles {

    private static final int MIN_STYLE_SIZE = 1;
    private static final int MAX_STYLE_SIZE = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_styles_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<MemberStyle> styles = new HashSet<>();

    public void initialize(final InternalStylesInitializeRequest request) {
        List<MemberStyle> requestedMemberStyles = request.memberStyles();
        validateStyleCodes(requestedMemberStyles);
        changeStyles(requestedMemberStyles);
    }

    public void update(final InternalStylesUpdateRequest request) {
        List<MemberStyle> requestedMemberStyles = request.styles();
        validateStyleCodes(requestedMemberStyles);
        changeStyles(requestedMemberStyles);
    }

    private void validateStyleCodes(final List<MemberStyle> memberStyles) {
        validateSize(memberStyles);
        validateDuplicates(memberStyles);
    }

    private void validateSize(final List<MemberStyle> memberStyles) {
        int size = memberStyles.size();

        if (size < MIN_STYLE_SIZE || MAX_STYLE_SIZE < size) {
            throw new MemberStyleSizeException();
        }
    }

    private void validateDuplicates(final List<MemberStyle> memberStyles) {
        Set<String> uniqueStyleKeys = new HashSet<>();

        for (MemberStyle memberStyle : memberStyles) {
            Style style = memberStyle.getStyle();
            String key = style.getName() + style.getCode();
            if (!uniqueStyleKeys.add(key)) {
                throw new MemberStyleDuplicateException();
            }
        }
    }

    private void changeStyles(final List<MemberStyle> requestedMemberStyles) {
        styles.removeIf(memberStyle -> !requestedMemberStyles.contains(memberStyle));
        requestedMemberStyles.stream()
                .filter(this::isNewStyle)
                .forEach(requestedMemberStyle -> styles.add(requestedMemberStyle));
    }

    private boolean isNewStyle(final MemberStyle requestedMemberStyle) {
        return styles.stream()
                .noneMatch(memberStyle -> memberStyle.isSame(requestedMemberStyle));
    }
}
