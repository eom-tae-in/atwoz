package com.atwoz.job.application;

import com.atwoz.global.application.BaseQueryService;
import com.atwoz.job.application.dto.JobPagingResponses;
import com.atwoz.job.domain.Job;
import com.atwoz.job.domain.JobRepository;
import com.atwoz.job.exception.exceptions.JobNotFoundException;
import com.atwoz.job.infrasturcture.dto.JobPagingResponse;
import com.atwoz.job.infrasturcture.dto.JobSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class JobQueryService extends BaseQueryService<JobPagingResponse> {

    private final JobRepository jobRepository;

    public JobSingleResponse findJob(final Long jobId) {
        Job foundJob = findJobById(jobId);
        return JobSingleResponse.from(foundJob);
    }

    private Job findJobById(final Long jobId) {
        return jobRepository.findJobById(jobId)
                .orElseThrow(JobNotFoundException::new);
    }

    public JobPagingResponses findJobsWithPaging(final Pageable pageable) {
        Page<JobPagingResponse> jobResponses = jobRepository.findJobsWithPaging(pageable);
        int nextPage = getNextPage(pageable.getPageNumber(), jobResponses);
        return JobPagingResponses.of(jobResponses, pageable, nextPage);
    }
}
