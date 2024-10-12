package com.atwoz.job.infrasturcture;

import com.atwoz.job.domain.Job;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobJpaRepository extends JpaRepository<Job, Long> {

    Job save(Job job);

    Optional<Job> findJobById(Long jobId);

    Optional<Job> findJobByName(String jobName);

    Optional<Job> findJobByCode(String jobCode);

    void deleteJobById(Long jobId);
}
