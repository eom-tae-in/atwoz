package com.atwoz.profile.infrastructure;

import com.atwoz.profile.domain.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {

    boolean existsByNickname(String nickname);

    Profile save(Profile profile);

    Optional<Profile> findById(Long profileId);

    Optional<Long> findMemberIdByNickname(String nickname);

    void deleteById(Long profileId);
}
