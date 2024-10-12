package com.atwoz.job.infrasturcture.dto;

import com.atwoz.job.domain.Job;

public record JobSingleResponse(
        String name,
        String code
) {

    public static JobSingleResponse from(final Job job) {
        return new JobSingleResponse(
                job.getName(),
                job.getCode()
        );
    }
}
