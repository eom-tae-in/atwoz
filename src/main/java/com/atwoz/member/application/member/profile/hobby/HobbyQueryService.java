package com.atwoz.member.application.member.profile.hobby;

import com.atwoz.global.application.BaseQueryService;
import com.atwoz.member.application.member.profile.hobby.dto.HobbyResponses;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.exception.exceptions.member.profile.hobby.HobbyNotFoundException;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HobbyQueryService extends BaseQueryService<HobbyResponse> {

    private final HobbyRepository hobbyRepository;

    public HobbyResponse findHobby(final Long hobbyId) {
        Hobby foundHobby = findMemberHobbyById(hobbyId);
        return HobbyResponse.from(foundHobby);
    }

    private Hobby findMemberHobbyById(final Long hobbyId) {
        return hobbyRepository.findHobbyById(hobbyId)
                .orElseThrow(HobbyNotFoundException::new);
    }

    public HobbyResponses findHobbiesWithPaging(final Pageable pageable) {
        Page<HobbyResponse> hobbyResponses = hobbyRepository.findHobbiesWithPaging(pageable);
        int nextPage = getNextPage(pageable.getPageNumber(), hobbyResponses);

        return HobbyResponses.of(hobbyResponses, pageable, nextPage);
    }
}
