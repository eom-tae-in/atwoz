package com.atwoz.interview.application.interview;

import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.infrastructure.interview.dto.InterviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InterviewQueryService {

    private final InterviewRepository interviewRepository;

    public List<InterviewResponse> findInterviewsByType(final String type) {
        InterviewType interviewType = InterviewType.findByName(type);
        return interviewRepository.findByInterviewType(interviewType);
    }
}
