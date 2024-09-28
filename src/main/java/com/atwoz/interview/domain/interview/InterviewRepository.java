package com.atwoz.interview.domain.interview;

import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.infrastructure.interview.dto.InterviewResponse;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository {

    Interview save(Interview interview);
    Optional<Interview> findById(Long id);
    boolean existsByQuestion(String question);
    List<InterviewResponse> findByInterviewType(InterviewType interviewType);
}
