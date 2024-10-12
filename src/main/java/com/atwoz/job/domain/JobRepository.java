package com.atwoz.job.domain;

import com.atwoz.job.infrasturcture.dto.JobPagingResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobRepository {

    Job save(Job job);

    Optional<Job> findJobById(Long jobId);

    Optional<Job> findJobByName(String jobName);

    Optional<Job> findJobByCode(String jobCode);

    Page<JobPagingResponse> findJobsWithPaging(Pageable pageable);

    void deleteJobById(Long jobId);
}
