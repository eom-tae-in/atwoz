package com.atwoz.interview.application.interview;

import com.atwoz.interview.application.interview.dto.InterviewCreateRequest;
import com.atwoz.interview.application.interview.dto.InterviewUpdateRequest;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.exception.exceptions.AlreadyExistedInterviewQuestionException;
import com.atwoz.interview.exception.exceptions.InterviewNotFoundException;
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

    public Interview updateInterview(final Long id, final InterviewUpdateRequest request) {
        Interview findInterview = findInterviewById(id);
        validateNotExistedQuestion(request.question());

        findInterview.updateInterview(request.question(), request.type());

        return findInterview;
    }

    private Interview findInterviewById(final Long id) {
        return interviewRepository.findById(id)
                .orElseThrow(InterviewNotFoundException::new);
    }
}
