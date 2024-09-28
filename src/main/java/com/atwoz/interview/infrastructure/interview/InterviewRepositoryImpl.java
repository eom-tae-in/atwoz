package com.atwoz.interview.infrastructure.interview;

import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.infrastructure.interview.dto.InterviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class InterviewRepositoryImpl implements InterviewRepository {

    private final InterviewJpaRepository interviewJpaRepository;
    private final InterviewQueryRepository interviewQueryRepository;

    @Override
    public Interview save(final Interview interview) {
        return interviewJpaRepository.save(interview);
    }

    @Override
    public Optional<Interview> findById(final Long id) {
        return interviewJpaRepository.findById(id);
    }

    @Override
    public boolean existsByQuestion(final String question) {
        return interviewJpaRepository.existsByQuestion(question);
    }

    @Override
    public List<InterviewResponse> findByInterviewType(final InterviewType interviewType) {
        return interviewQueryRepository.findByInterviewType(interviewType);
    }
}
