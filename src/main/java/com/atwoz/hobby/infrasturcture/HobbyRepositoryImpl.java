package com.atwoz.hobby.infrasturcture;

import com.atwoz.hobby.domain.Hobby;
import com.atwoz.hobby.domain.HobbyRepository;
import com.atwoz.hobby.infrasturcture.dto.HobbyPagingResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class HobbyRepositoryImpl implements HobbyRepository {

    private final HobbyJpaRepository hobbyJpaRepository;
    private final HobbyQueryRepository hobbyQueryRepository;

    @Override
    public Hobby save(final Hobby hobby) {
        return hobbyJpaRepository.save(hobby);
    }

    @Override
    public Optional<Hobby> findHobbyById(final Long hobbyId) {
        return hobbyJpaRepository.findHobbyById(hobbyId);
    }

    @Override
    public Optional<Hobby> findHobbyByName(final String hobbyName) {
        return hobbyJpaRepository.findHobbyByName(hobbyName);
    }

    @Override
    public Optional<Hobby> findHobbyByCode(final String hobbyCode) {
        return hobbyJpaRepository.findHobbyByCode(hobbyCode);
    }

    @Override
    public Page<HobbyPagingResponse> findHobbiesWithPaging(final Pageable pageable) {
        return hobbyQueryRepository.findHobbiesWithPaging(pageable);
    }

    @Override
    public void deleteById(final Long hobbyId) {
        hobbyJpaRepository.deleteHobbyById(hobbyId);
    }
}
