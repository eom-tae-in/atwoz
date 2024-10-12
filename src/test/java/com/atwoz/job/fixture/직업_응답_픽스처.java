package com.atwoz.job.fixture;

import com.atwoz.job.application.dto.JobPagingResponses;
import com.atwoz.job.domain.Job;
import com.atwoz.job.infrasturcture.dto.JobPagingResponse;
import com.atwoz.job.infrasturcture.dto.JobSingleResponse;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class 직업_응답_픽스처 {

    public static class 직업_단건_조회_응답_픽스처 {

        private static final String DEFAULT_JOB_NAME = "job";
        private static final String DEFAULT_JOB_CODE = "code";

        public static JobSingleResponse 직업_단건_조회_응답_생성() {
            return new JobSingleResponse(
                    DEFAULT_JOB_NAME,
                    DEFAULT_JOB_CODE
            );
        }

        public static JobSingleResponse 직업_단건_조회_응답_생성_직업(final Job job) {
            return new JobSingleResponse(
                    job.getName(),
                    job.getCode()
            );
        }
    }

    public static class 직업_페이징_조회_응답_픽스처 {

        private static final long DEFAULT_JOB_ID = 1L;
        private static final String DEFAULT_JOB_NAME = "job";
        private static final String DEFAULT_JOB_CODE = "code";
        private static final int DEFAULT_NOW_PAGE = 0;
        private static final int DEFAULT_NEXT_PAGE = 1;
        private static final int DEFAULT_TOTAL_PAGES = 2;
        private static final long DEFAULT_TOTAL_ELEMENTS = 3;

        public static JobPagingResponse 직업_페이징_조회_응답_생성() {
            return new JobPagingResponse(
                    DEFAULT_JOB_ID,
                    DEFAULT_JOB_NAME,
                    DEFAULT_JOB_CODE
            );
        }

        public static JobPagingResponse 직업_페이징_조회_응답_생성_직업(final Job job) {
            return new JobPagingResponse(
                    job.getId(),
                    job.getName(),
                    job.getCode()
            );
        }

        public static JobPagingResponses 직업_페이징_조회_목록_응답_생성_직업페이징목록응답(final List<JobPagingResponse> responses) {
            return JobPagingResponses.builder()
                    .jobPagingResponses(responses)
                    .nowPage(DEFAULT_NOW_PAGE)
                    .nextPage(DEFAULT_NEXT_PAGE)
                    .totalPages(DEFAULT_TOTAL_PAGES)
                    .totalElements(DEFAULT_TOTAL_ELEMENTS)
                    .build();
        }
    }
}
