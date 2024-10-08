package com.atwoz.hobby.application;

import com.atwoz.global.application.BaseQueryService;
import com.atwoz.hobby.application.dto.HobbyPagingResponses;
import com.atwoz.hobby.domain.Hobby;
import com.atwoz.hobby.domain.HobbyRepository;
import com.atwoz.hobby.exception.exceptions.HobbyNotFoundException;
import com.atwoz.hobby.infrasturcture.dto.HobbyPagingResponse;
import com.atwoz.hobby.infrasturcture.dto.HobbySingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HobbyQueryService extends BaseQueryService<HobbyPagingResponse> {

    private final HobbyRepository hobbyRepository;

    public HobbySingleResponse findHobby(final Long hobbyId) {
        Hobby foundHobby = findMemberHobbyById(hobbyId);
        return HobbySingleResponse.from(foundHobby);
    }

    private Hobby findMemberHobbyById(final Long hobbyId) {
        return hobbyRepository.findHobbyById(hobbyId)
                .orElseThrow(HobbyNotFoundException::new);
    }

    public HobbyPagingResponses findHobbiesWithPaging(final Pageable pageable) {
        Page<HobbyPagingResponse> hobbyResponses = hobbyRepository.findHobbiesWithPaging(pageable);
        int nextPage = getNextPage(pageable.getPageNumber(), hobbyResponses);

        return HobbyPagingResponses.of(hobbyResponses, pageable, nextPage);
    }
}
