package com.atwoz.interview.application.interview;

import com.atwoz.interview.application.interview.dto.InterviewCreateRequest;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.exception.exceptions.AlreadyExistedInterviewQuestionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;

    public Interview createInterview(final InterviewCreateRequest request) {
        validateNotExistedQuestion(request.question());

        Interview interview = Interview.createWith(request.question(), request.type());
        return interviewRepository.save(interview);
    }

    private void validateNotExistedQuestion(final String question) {
        if (interviewRepository.existsByQuestion(question)) {
            throw new AlreadyExistedInterviewQuestionException();
        }
    }
}
