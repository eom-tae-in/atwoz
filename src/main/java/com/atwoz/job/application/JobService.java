package com.atwoz.job.application;

import com.atwoz.job.application.dto.JobCreateRequest;
import com.atwoz.job.application.dto.JobUpdateRequest;
import com.atwoz.job.domain.Job;
import com.atwoz.job.domain.JobRepository;
import com.atwoz.job.exception.exceptions.JobCodeAlreadyExistedException;
import com.atwoz.job.exception.exceptions.JobNameAlreadyExistedException;
import com.atwoz.job.exception.exceptions.JobNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class JobService {

    private final JobRepository jobRepository;

    public void saveJob(final JobCreateRequest jobCreateRequest) {
        Job createdJob = Job.createWith(jobCreateRequest.name(), jobCreateRequest.code());
        validateStyleCreateRequest(jobCreateRequest);
        jobRepository.save(createdJob);
    }

    private void validateStyleCreateRequest(final JobCreateRequest jobCreateRequest) {
        validateAlreadyExistedStyleName(jobCreateRequest.name());
        validateAlreadyExistedStyleCode(jobCreateRequest.code());
    }

    private void validateAlreadyExistedStyleName(final String styleName) {
        jobRepository.findJobByName(styleName)
                .ifPresent(style -> {
                    throw new JobNameAlreadyExistedException();
                });
    }

    private void validateAlreadyExistedStyleCode(final String styleCode) {
        jobRepository.findJobByCode(styleCode)
                .ifPresent(style -> {
                    throw new JobCodeAlreadyExistedException();
                });
    }

    public void updateJob(final Long styleId,
                          final JobUpdateRequest jobUpdateRequest) {
        Job foundJob = findStyle(styleId);
        validateStyleUpdateRequest(foundJob, jobUpdateRequest);
        foundJob.update(jobUpdateRequest.name(), jobUpdateRequest.code());
    }

    private void validateStyleUpdateRequest(final Job job,
                                            final JobUpdateRequest jobUpdateRequest) {
        validateStyleNameNotChanged(job.getName(), jobUpdateRequest.name());
        validateStyleCodeNotChanged(job.getCode(), jobUpdateRequest.code());
        validateAlreadyExistedStyleName(jobUpdateRequest.name());
        validateAlreadyExistedStyleCode(jobUpdateRequest.code());
    }

    private void validateStyleNameNotChanged(final String currentStyleName,
                                             final String newStyleName) {
        if (currentStyleName.equals(newStyleName)) {
            throw new JobNameAlreadyExistedException();
        }
    }

    private void validateStyleCodeNotChanged(final String currentStyleCode,
                                             final String newStyleCode) {
        if (currentStyleCode.equals(newStyleCode)) {
            throw new JobCodeAlreadyExistedException();
        }
    }

    public void deleteJob(final Long styleId) {
        Job foundJob = findStyle(styleId);
        jobRepository.deleteJobById(foundJob.getId());
    }

    private Job findStyle(final Long styleId) {
        return jobRepository.findJobById(styleId)
                .orElseThrow(JobNotFoundException::new);
    }
}
