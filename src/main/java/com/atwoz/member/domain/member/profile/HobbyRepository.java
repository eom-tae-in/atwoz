package com.atwoz.member.domain.member.profile;

import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyPagingResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HobbyRepository {

    Hobby save(Hobby hobby);

    Optional<Hobby> findHobbyById(Long hobbyId);

    Optional<Hobby> findHobbyByName(String hobbyName);

    Optional<Hobby> findHobbyByCode(String hobbyCode);

    Page<HobbyPagingResponse> findHobbiesWithPaging(Pageable pageable);

    void deleteById(Long hobbyId);
}
