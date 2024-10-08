package com.atwoz.job.fixture;

import com.atwoz.job.domain.Job;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class 직업_픽스처 {

    private static final String DEFAULT_NAME = "job1";
    private static final String DEFAULT_CODE = "code1";

    public static Job 직업_생성() {
        return Job.builder()
                .name(DEFAULT_NAME)
                .code(DEFAULT_CODE)
                .build();
    }

    public static Job 직업_생성_이름_코드(final String name, final String code) {
        return Job.builder()
                .name(name)
                .code(code)
                .build();
    }
}
