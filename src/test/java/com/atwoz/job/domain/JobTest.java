package com.atwoz.job.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static com.atwoz.job.fixture.직업_픽스처.직업_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JobTest {

    private Job job;

    @BeforeEach
    void setup() {
        job = 직업_생성();
    }

    @Test
    void 이름과_코드_정보로_직업을_생성한다() {
        // given
        String jobName = "job1";
        String jobCode = "code1";

        // when
        Job createdJob = Job.createWith(jobName, jobCode);

        // then
        assertSoftly(softly -> {
            softly.assertThat(createdJob.getName()).isEqualTo(jobName);
            softly.assertThat(createdJob.getCode()).isEqualTo(jobCode);
        });
    }

    @Test
    void 직업의_이름과_코드를_업데이트한다() {
        // given
        String updateJobName = "job2";
        String updateJobCode = "code2";

        // when
        job.update(updateJobName, updateJobCode);

        // then
        assertSoftly(softly -> {
            softly.assertThat(job.getName()).isEqualTo(updateJobName);
            softly.assertThat(job.getCode()).isEqualTo(updateJobCode);
        });
    }

    @Test
    void 직업_정보가_일치하면_true를_반환한다() {
        // given
        Job newJob = 직업_생성();
        
        // when
        boolean result = job.isSame(newJob);

        // then
        assertThat(result).isTrue();
    }
}
