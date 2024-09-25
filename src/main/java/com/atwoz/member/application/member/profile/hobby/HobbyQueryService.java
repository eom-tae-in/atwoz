package com.atwoz.member.application.member.profile.hobby;

import com.atwoz.global.application.BaseQueryService;
import com.atwoz.member.application.member.profile.hobby.dto.HobbyPagingResponses;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.exception.exceptions.member.profile.hobby.HobbyNotFoundException;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyPagingResponse;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbySingleResponse;
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
