package com.atwoz.member.infrastructure.member.profile.hobby;

import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyResponse;
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
    public Page<HobbyResponse> findHobbiesWithPaging(final Pageable pageable) {
        return hobbyQueryRepository.findHobbiesWithPaging(pageable);
    }

    @Override
    public void deleteById(final Long hobbyId) {
        hobbyJpaRepository.deleteHobbyById(hobbyId);
    }
}
