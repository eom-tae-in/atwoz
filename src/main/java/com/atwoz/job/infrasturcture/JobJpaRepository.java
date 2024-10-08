package com.atwoz.job.infrasturcture;

import com.atwoz.job.domain.Job;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobJpaRepository extends JpaRepository<Job, Long> {

    Job save(Job job);

    Optional<Job> findJobById(Long styleId);

    Optional<Job> findJobByName(String styleName);

    Optional<Job> findJobByCode(String styleCode);

    void deleteJobById(Long styleId);
}
