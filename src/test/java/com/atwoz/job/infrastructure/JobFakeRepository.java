package com.atwoz.job.infrastructure;

import com.atwoz.job.domain.Job;
import com.atwoz.job.domain.JobRepository;
import com.atwoz.job.infrasturcture.dto.JobPagingResponse;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class JobFakeRepository implements JobRepository {

    private final Map<Long, Job> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public Job save(final Job job) {
        Job createdJob = Job.builder()
                .id(id)
                .name(job.getName())
                .code(job.getCode())
                .build();
        map.put(id++, createdJob);

        return createdJob;
    }

    @Override
    public Optional<Job> findJobById(final Long jobId) {
        return Optional.ofNullable(map.get(jobId));
    }

    @Override
    public Optional<Job> findJobByName(final String jobName) {
        return map.values()
                .stream()
                .filter(style -> jobName.equals(style.getName()))
                .findAny();
    }

    @Override
    public Optional<Job> findJobByCode(final String jobCode) {
        return map.values()
                .stream()
                .filter(style -> jobCode.equals(style.getCode()))
                .findAny();
    }

    @Override
    public Page<JobPagingResponse> findJobsWithPaging(final Pageable pageable) {
        List<JobPagingResponse> jobPagingRespons = map.values()
                .stream()
                .sorted(Comparator.comparing(Job::getId))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(JobPagingResponse::from)
                .toList();

        return new PageImpl<>(jobPagingRespons);
    }

    @Override
    public void deleteJobById(final Long jobId) {
        map.remove(jobId);
    }
}
