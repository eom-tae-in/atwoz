package com.atwoz.job.fixture;

import com.atwoz.job.application.dto.JobCreateRequest;
import com.atwoz.job.application.dto.JobUpdateRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class 직업_요청_픽스처 {

    public static class 직업_생성_요청_픽스처 {

        private static final String DEFAULT_JOB_NAME = "job";
        private static final String DEFAULT_JOB_CODE = "code";

        public static JobCreateRequest 직업_생성_요청_생성() {
            return new JobCreateRequest(DEFAULT_JOB_NAME, DEFAULT_JOB_CODE);
        }

        public static JobCreateRequest 직업_생성_요청_생성_이름_코드(final String jobName, final String jobCode) {
            return new JobCreateRequest(jobName, jobCode);
        }
    }

    public static class 직업_수정_요청_픽스처 {

        private static final String DEFAULT_JOB_NAME = "job";
        private static final String DEFAULT_JOB_CODE = "code";

        public static JobUpdateRequest 직업_업데이트_요청_생성() {
            return new JobUpdateRequest(DEFAULT_JOB_NAME, DEFAULT_JOB_CODE);
        }

        public static JobUpdateRequest 직업_업데이트_요청_생성_이름_코드(final String jobName, final String jobCode) {
            return new JobUpdateRequest(jobName, jobCode);
        }
    }
}
