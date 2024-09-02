package com.atwoz.interview.domain.interview;

import java.util.Optional;

public interface InterviewRepository {

    Interview save(Interview interview);
    Optional<Interview> findById(Long id);
    boolean existsByQuestion(String question);
}
