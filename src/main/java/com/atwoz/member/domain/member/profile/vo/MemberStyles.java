package com.atwoz.member.domain.member.profile.vo;

import com.atwoz.member.domain.member.profile.MemberStyle;
import com.atwoz.member.exception.exceptions.member.profile.InvalidStyleException;
import com.atwoz.member.exception.exceptions.member.profile.StyleDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.StyleSizeException;
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

    public void change(final List<String> styleCodes) {
        validateStyleCodes(styleCodes);
        changeStyles(styleCodes);
    }

    private void validateStyleCodes(final List<String> styleCodes) {
        validateSize(styleCodes);
        validateDuplicates(styleCodes);
        validateHasInvalidCodes(styleCodes);
    }

    private void validateSize(final List<String> styleCodes) {
        int size = styleCodes.size();

        if (size < MIN_STYLE_SIZE || MAX_STYLE_SIZE < size) {
            throw new StyleSizeException();
        }
    }

    private void validateDuplicates(final List<String> styleCodes) {
        HashSet<String> uniqueCodes = new HashSet<>(styleCodes);

        if (uniqueCodes.size() != styleCodes.size()) {
            throw new StyleDuplicateException();
        }
    }

    private void validateHasInvalidCodes(final List<String> styleCodes) {
        if (hasInvalidStyleCode(styleCodes)) {
            throw new InvalidStyleException();
        }
    }

    private boolean hasInvalidStyleCode(final List<String> styleCodes) {
        return styleCodes.stream()
                .anyMatch(styleCode -> !Style.isValidCode(styleCode));
    }

    private void changeStyles(final List<String> styleCodes) {
        styles.removeIf(memberStyle -> !memberStyle.hasMatchingStyleCodeOf(styleCodes));
        styleCodes.stream()
                .map(MemberStyle::createWith)
                .filter(memberStyle -> !isAlreadyExist(memberStyle))
                .forEach(memberStyle -> styles.add(memberStyle));
    }

    private boolean isAlreadyExist(final MemberStyle memberStyle) {
        return styles.stream()
                .anyMatch(memberStyle::isSame);
    }
}
