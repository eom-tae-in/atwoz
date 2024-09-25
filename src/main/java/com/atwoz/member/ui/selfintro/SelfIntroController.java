package com.atwoz.member.ui.selfintro;


import com.atwoz.member.application.selfintro.SelfIntroQueryService;
import com.atwoz.member.application.selfintro.SelfIntroService;
import com.atwoz.member.application.selfintro.dto.SelfIntroCreateRequest;
import com.atwoz.member.application.selfintro.dto.SelfIntroFilterRequest;
import com.atwoz.member.application.selfintro.dto.SelfIntroResponses;
import com.atwoz.member.application.selfintro.dto.SelfIntroUpdateRequest;
import com.atwoz.member.ui.auth.support.AuthMember;
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
@RequestMapping("/api/members/self-intros")
public class SelfIntroController {

    private static final String CREATION_TIME = "createdAt";

    private final SelfIntroService selfIntroService;
    private final SelfIntroQueryService selfIntroQueryService;

    @PostMapping("/me")
    public ResponseEntity<Void> saveSelfIntro(
            @RequestBody @Valid final SelfIntroCreateRequest selfIntroCreateRequest,
            @AuthMember final Long memberId
    ) {
        selfIntroService.saveSelfIntro(selfIntroCreateRequest, memberId);

        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/filter")
    public ResponseEntity<SelfIntroResponses> findAllSelfIntrosWithPagingAndFiltering(
            @PageableDefault(sort = CREATION_TIME, direction = Direction.DESC) final Pageable pageable,
            @ModelAttribute final SelfIntroFilterRequest selfIntroFilterRequest,
            @AuthMember final Long memberId
    ) {
        return ResponseEntity.ok(selfIntroQueryService.findAllSelfIntrosWithPagingAndFiltering(
                pageable,
                selfIntroFilterRequest,
                memberId
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSelfIntro(
            @PathVariable("id") final Long selfIntroId,
            @RequestBody @Valid final SelfIntroUpdateRequest selfIntroUpdateRequest,
            @AuthMember final Long memberId
    ) {
        selfIntroService.updateSelfIntro(selfIntroUpdateRequest, selfIntroId, memberId);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSelfIntro(
            @PathVariable("id") final Long selfIntroId,
            @AuthMember final Long memberId
    ) {
        selfIntroService.deleteSelfIntro(selfIntroId, memberId);

        return ResponseEntity.noContent()
                .build();
    }
}
