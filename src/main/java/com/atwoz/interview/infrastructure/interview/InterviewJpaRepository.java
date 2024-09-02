package com.atwoz.interview.infrastructure.interview;

import com.atwoz.interview.domain.interview.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewJpaRepository extends JpaRepository<Interview, Long> {

    Interview save(Interview interview);
    Optional<Interview> findById(Long id);
    boolean existsByQuestion(String question);
}
