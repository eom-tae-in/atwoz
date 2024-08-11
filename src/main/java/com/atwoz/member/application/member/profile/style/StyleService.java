package com.atwoz.member.application.member.profile.style;

import com.atwoz.member.application.member.profile.style.dto.StyleCreateRequest;
import com.atwoz.member.application.member.profile.style.dto.StyleUpdateRequest;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleCodeAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleNameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class StyleService {

    private final StyleRepository styleRepository;

    public void saveStyle(final StyleCreateRequest styleCreateRequest) {
        Style createdStyle = Style.createWith(styleCreateRequest.name(), styleCreateRequest.code());
        validateStyleCreateRequest(styleCreateRequest);
        styleRepository.save(createdStyle);
    }

    private void validateStyleCreateRequest(final StyleCreateRequest styleCreateRequest) {
        validateAlreadyExistedStyleName(styleCreateRequest.name());
        validateAlreadyExistedStyleCode(styleCreateRequest.code());
    }

    private void validateAlreadyExistedStyleName(final String styleName) {
        Optional<Style> foundStyle = styleRepository.findStyleByName(styleName);

        if (foundStyle.isPresent()) {
            throw new StyleNameAlreadyExistedException();
        }
    }

    private void validateAlreadyExistedStyleCode(final String styleCode) {
        Optional<Style> foundStyle = styleRepository.findStyleByCode(styleCode);

        if (foundStyle.isPresent()) {
            throw new StyleCodeAlreadyExistedException();
        }
    }

    public void updateStyle(final Long styleId,
                            final StyleUpdateRequest styleUpdateRequest) {
        Style foundStyle = findStyle(styleId);
        validateStyleUpdateRequest(foundStyle, styleUpdateRequest);
        foundStyle.update(styleUpdateRequest.name(), styleUpdateRequest.code());
    }

    private void validateStyleUpdateRequest(final Style style,
                                            final StyleUpdateRequest styleUpdateRequest) {
        validateStyleNameNotChanged(style.getName(), styleUpdateRequest.name());
        validateStyleCodeNotChanged(style.getCode(), styleUpdateRequest.code());
        validateAlreadyExistedStyleName(styleUpdateRequest.name());
        validateAlreadyExistedStyleCode(styleUpdateRequest.code());
    }

    private void validateStyleNameNotChanged(final String currentStyleName,
                                             final String newStyleName) {
        if (currentStyleName.equals(newStyleName)) {
            throw new StyleNameAlreadyExistedException();
        }
    }

    private void validateStyleCodeNotChanged(final String currentStyleCode,
                                             final String newStyleCode) {
        if (currentStyleCode.equals(newStyleCode)) {
            throw new StyleCodeAlreadyExistedException();
        }
    }

    public void deleteStyle(final Long styleId) {
        Style foundStyle = findStyle(styleId);
        styleRepository.deleteStyleById(foundStyle.getId());
    }

    private Style findStyle(final Long styleId) {
        return styleRepository.findStyleById(styleId)
                .orElseThrow(StyleNotFoundException::new);
    }
}
