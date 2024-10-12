package com.atwoz.job.ui;

import com.atwoz.job.application.JobQueryService;
import com.atwoz.job.application.JobService;
import com.atwoz.job.application.dto.JobCreateRequest;
import com.atwoz.job.application.dto.JobPagingResponses;
import com.atwoz.job.application.dto.JobUpdateRequest;
import com.atwoz.job.infrasturcture.dto.JobSingleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final JobQueryService jobQueryService;

    @PostMapping
    public ResponseEntity<Void> savaJob(@RequestBody @Valid final JobCreateRequest jobCreateRequest) {
        jobService.saveJob(jobCreateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobSingleResponse> findJob(@PathVariable("jobId") final Long jobId) {
        return ResponseEntity.ok(jobQueryService.findJob(jobId));
    }

    @GetMapping
    public ResponseEntity<JobPagingResponses> findJobsWithPaging(
            @PageableDefault(sort = "id") final Pageable pageable) {
        return ResponseEntity.ok(jobQueryService.findJobsWithPaging(pageable));
    }

    @PatchMapping("/{jobId}")
    public ResponseEntity<Void> updateJob(
            @PathVariable("jobId") final Long jobId,
            @RequestBody @Valid final JobUpdateRequest jobUpdateRequest
    ) {
        jobService.updateJob(jobId, jobUpdateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable("jobId") final Long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent()
                .build();
    }
}
