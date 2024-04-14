package com.atwoz.member.domain.member.profile.vo;

import com.atwoz.member.exception.exceptions.member.profile.InvalidStyleException;
import com.atwoz.member.exception.exceptions.member.profile.StyleDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.StyleSizeException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MemberStyles {

    private static final int MIN_STYLE_SIZE = 1;
    private static final int MAX_STYLE_SIZE = 3;

    @ElementCollection
    @CollectionTable(name = "STYLES", joinColumns = @JoinColumn(name = "profile_id"))
    @Enumerated(EnumType.STRING)
    private Set<Style> styles = new HashSet<>();

    public MemberStyles changeWith(final List<String> styleCodes) {
        validateStyleCodes(styleCodes);
        Set<Style> uniqueStyles = convertToUniqueStyles(styleCodes);

        if (isSameAsCurrentValue(uniqueStyles)) {
            return new MemberStyles(this.styles);
        }

        return new MemberStyles(uniqueStyles);
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

    private Set<Style> convertToUniqueStyles(final List<String> styleCodes) {
        return styleCodes.stream()
                .map(Style::findByCode)
                .collect(Collectors.toSet());
    }

    private boolean isSameAsCurrentValue(final Set<Style> uniqueStyles) {
        return uniqueStyles.equals(this.styles);
    }
}
