package com.atwoz.interview.infrastructure.interview;

import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class InterviewFakeRepository implements InterviewRepository {

    private final Map<Long, Interview> map = new HashMap<>();

    private Long id = 0L;

    @Override
    public Interview save(final Interview interview) {
        id++;
        Interview savedInterview = Interview.builder()
                .id(id)
                .interviewType(interview.getInterviewType())
                .question(interview.getQuestion())
                .build();
        map.put(id, savedInterview);

        return savedInterview;
    }

    @Override
    public Optional<Interview> findById(final Long id) {
        return map.values()
                .stream()
                .filter(interview -> id.equals(interview.getId()))
                .findAny();
    }

    @Override
    public boolean existsByQuestion(final String question) {
        return map.values()
                .stream()
                .anyMatch(interview -> question.equals(interview.getQuestion()));
    }
}
