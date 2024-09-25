package com.atwoz.member.ui.member.profile.hobby;

import com.atwoz.member.application.member.profile.hobby.HobbyQueryService;
import com.atwoz.member.application.member.profile.hobby.HobbyService;
import com.atwoz.member.application.member.profile.hobby.dto.HobbyCreateRequest;
import com.atwoz.member.application.member.profile.hobby.dto.HobbyPagingResponses;
import com.atwoz.member.application.member.profile.hobby.dto.HobbyUpdateRequest;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbySingleResponse;
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
@RequestMapping("/api/members/hobbies")
public class HobbyController {

    private final HobbyService hobbyService;
    private final HobbyQueryService hobbyQueryService;

    @PostMapping
    public ResponseEntity<Void> saveHobby(@RequestBody @Valid final HobbyCreateRequest hobbyCreateRequest) {
        hobbyService.saveHobby(hobbyCreateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/{hobbyId}")
    public ResponseEntity<HobbySingleResponse> findHobby(@PathVariable("hobbyId") final Long hobbyId) {
        return ResponseEntity.ok(hobbyQueryService.findHobby(hobbyId));
    }

    @GetMapping
    public ResponseEntity<HobbyPagingResponses> findHobbiesWithPaging(@PageableDefault(sort = "id") final Pageable pageable) {
        return ResponseEntity.ok(hobbyQueryService.findHobbiesWithPaging(pageable));
    }

    @PatchMapping("/{hobbyId}")
    public ResponseEntity<Void> updateHobby(@PathVariable("hobbyId") final Long hobbyId,
                                            @RequestBody @Valid final HobbyUpdateRequest hobbyUpdateRequest) {
        hobbyService.updateHobby(hobbyId, hobbyUpdateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{hobbyId}")
    public ResponseEntity<Void> deleteHobby(@PathVariable("hobbyId") final Long hobbyId) {
        hobbyService.deleteHobby(hobbyId);
        return ResponseEntity.noContent()
                .build();
    }
}
