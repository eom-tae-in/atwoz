package com.atwoz.selfintro.ui;


import com.atwoz.member.ui.auth.support.AuthMember;
import com.atwoz.selfintro.application.SelfIntroQueryService;
import com.atwoz.selfintro.application.SelfIntroService;
import com.atwoz.selfintro.application.dto.SelfIntroCreateRequest;
import com.atwoz.selfintro.application.dto.SelfIntroFilterRequest;
import com.atwoz.selfintro.application.dto.SelfIntroQueryResponses;
import com.atwoz.selfintro.application.dto.SelfIntroUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/self-intros")
public class SelfIntroController {

    private static final String CREATION_TIME = "createdAt";

    private final SelfIntroService selfIntroService;
    private final SelfIntroQueryService selfIntroQueryService;

    @PostMapping("/me")
    public ResponseEntity<Void> saveSelfIntro(
            @AuthMember final Long memberId,
            @RequestBody @Valid final SelfIntroCreateRequest request
    ) {
        selfIntroService.saveSelfIntro(request, memberId);

        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/filter")
    public ResponseEntity<SelfIntroQueryResponses> findAllSelfIntrosWithPagingAndFiltering(
            @AuthMember final Long memberId,
            @ModelAttribute final SelfIntroFilterRequest request,
            @PageableDefault(sort = CREATION_TIME, direction = Direction.DESC) final Pageable pageable
    ) {
        return ResponseEntity.ok(selfIntroQueryService.findAllSelfIntrosWithPagingAndFiltering(
                pageable,
                request,
                memberId
        ));
    }

    @PatchMapping("/{selfIntroId}")
    public ResponseEntity<Void> updateSelfIntro(
            @PathVariable("selfIntroId") final Long selfIntroId,
            @RequestBody @Valid final SelfIntroUpdateRequest selfIntroUpdateRequest,
            @AuthMember final Long memberId
    ) {
        selfIntroService.updateSelfIntro(selfIntroUpdateRequest, selfIntroId, memberId);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{selfIntroId}")
    public ResponseEntity<Void> deleteSelfIntro(
            @PathVariable("selfIntroId") final Long selfIntroId,
            @AuthMember final Long memberId
    ) {
        selfIntroService.deleteSelfIntro(selfIntroId, memberId);

        return ResponseEntity.noContent()
                .build();
    }
}
