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
@Transactional
@Service
public class JobService {

    private final JobRepository jobRepository;

    public void saveJob(final JobCreateRequest jobCreateRequest) {
        Job createdJob = Job.createWith(jobCreateRequest.name(), jobCreateRequest.code());
        validateJobCreateRequest(jobCreateRequest);
        jobRepository.save(createdJob);
    }

    private void validateJobCreateRequest(final JobCreateRequest jobCreateRequest) {
        validateAlreadyExistedJobName(jobCreateRequest.name());
        validateAlreadyExistedJobCode(jobCreateRequest.code());
    }

    private void validateAlreadyExistedJobName(final String jobName) {
        jobRepository.findJobByName(jobName)
                .ifPresent(style -> {
                    throw new JobNameAlreadyExistedException();
                });
    }

    private void validateAlreadyExistedJobCode(final String jobCode) {
        jobRepository.findJobByCode(jobCode)
                .ifPresent(style -> {
                    throw new JobCodeAlreadyExistedException();
                });
    }

    public void updateJob(final Long jobId, final JobUpdateRequest request) {
        Job foundJob = findJob(jobId);
        validateJobUpdateRequest(foundJob, request);
        foundJob.update(request.name(), request.code());
    }

    private void validateJobUpdateRequest(final Job job, final JobUpdateRequest request) {
        validateJobNameNotChanged(job.getName(), request.name());
        validateJobCodeNotChanged(job.getCode(), request.code());
        validateAlreadyExistedJobName(request.name());
        validateAlreadyExistedJobCode(request.code());
    }

    private void validateJobNameNotChanged(final String currentJobName, final String newJobName) {
        if (currentJobName.equals(newJobName)) {
            throw new JobNameAlreadyExistedException();
        }
    }

    private void validateJobCodeNotChanged(final String currentJobCode, final String newJobCode) {
        if (currentJobCode.equals(newJobCode)) {
            throw new JobCodeAlreadyExistedException();
        }
    }

    public void deleteJob(final Long jobId) {
        Job foundJob = findJob(jobId);
        jobRepository.deleteJobById(foundJob.getId());
    }

    private Job findJob(final Long jobId) {
        return jobRepository.findJobById(jobId)
                .orElseThrow(JobNotFoundException::new);
    }
}
