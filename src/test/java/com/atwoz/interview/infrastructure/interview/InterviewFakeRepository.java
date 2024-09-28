package com.atwoz.interview.infrastructure.interview;

import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.infrastructure.interview.dto.InterviewResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.util.HashMap;
import java.util.List;
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

    @Override
    public List<InterviewResponse> findByInterviewType(final InterviewType interviewType) {
        return map.values()
                .stream()
                .filter(interview -> interview.isSameType(interviewType))
                .map(interview -> new InterviewResponse(interview.getId(), interview.getQuestion(), interviewType))
                .toList();
    }
}
