package com.atwoz.job.infrasturcture.dto;

import com.atwoz.job.domain.Job;

public record JobPagingResponse(
        Long jobId,
        String name,
        String code
) {

    public static JobPagingResponse from(final Job job) {
        return new JobPagingResponse(
                job.getId(),
                job.getName(),
                job.getCode()
        );
    }
}
