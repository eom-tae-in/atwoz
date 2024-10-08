package com.atwoz.hobby.infrasturcture;

import com.atwoz.hobby.domain.Hobby;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyJpaRepository extends JpaRepository<Hobby, Long> {

    Hobby save(Hobby hobby);

    Optional<Hobby> findHobbyById(Long hobbyId);

    Optional<Hobby> findHobbyByName(String hobbyName);

    Optional<Hobby> findHobbyByCode(String hobbyCode);

    void deleteHobbyById(Long hobbyId);
}
