package com.atwoz.member.ui.member.profile.style;

import com.atwoz.member.application.member.profile.style.StyleQueryService;
import com.atwoz.member.application.member.profile.style.StyleService;
import com.atwoz.member.application.member.profile.style.dto.StyleCreateRequest;
import com.atwoz.member.application.member.profile.style.dto.StylePagingResponses;
import com.atwoz.member.application.member.profile.style.dto.StyleUpdateRequest;
import com.atwoz.member.infrastructure.member.profile.style.dto.StyleSingleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members/styles")
public class StyleController {

    private final StyleService styleService;
    private final StyleQueryService styleQueryService;

    @PostMapping
    public ResponseEntity<Void> savaStyle(@RequestBody @Valid final StyleCreateRequest styleCreateRequest) {
        styleService.saveStyle(styleCreateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/{styleId}")
    public ResponseEntity<StyleSingleResponse> findStyle(@PathVariable("styleId") final Long styleId) {
        return ResponseEntity.ok(styleQueryService.findStyle(styleId));
    }

    @GetMapping
    public ResponseEntity<StylePagingResponses> findStylesWithPaging(@PageableDefault(sort = "id") final Pageable pageable) {
        return ResponseEntity.ok(styleQueryService.findStylesWithPaging(pageable));
    }

    @PatchMapping("/{styleId}")
    public ResponseEntity<Void> updateStyle(@PathVariable("styleId") final Long styleId,
                                            @RequestBody @Valid final StyleUpdateRequest styleUpdateRequest) {
        styleService.updateStyle(styleId, styleUpdateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{styleId}")
    public ResponseEntity<Void> deleteStyle(@PathVariable("styleId") final Long styleId) {
        styleService.deleteStyle(styleId);
        return ResponseEntity.noContent()
                .build();
    }
}
