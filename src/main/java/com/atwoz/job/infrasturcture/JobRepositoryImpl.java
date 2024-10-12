package com.atwoz.job.infrasturcture;

import com.atwoz.job.domain.Job;
import com.atwoz.job.domain.JobRepository;
import com.atwoz.job.infrasturcture.dto.JobPagingResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JobRepositoryImpl implements JobRepository {

    private final JobJpaRepository jobJpaRepository;
    private final JobQueryRepository jobQueryRepository;

    @Override
    public Job save(final Job job) {
        return jobJpaRepository.save(job);
    }

    @Override
    public Optional<Job> findJobById(final Long jobId) {
        return jobJpaRepository.findJobById(jobId);
    }

    @Override
    public Optional<Job> findJobByName(final String jobName) {
        return jobJpaRepository.findJobByName(jobName);
    }

    @Override
    public Optional<Job> findJobByCode(final String jobCode) {
        return jobJpaRepository.findJobByCode(jobCode);
    }

    @Override
    public Page<JobPagingResponse> findJobsWithPaging(final Pageable pageable) {
        return jobQueryRepository.findJobWithPaging(pageable);
    }

    @Override
    public void deleteJobById(final Long jobId) {
        jobJpaRepository.deleteJobById(jobId);
    }
}
