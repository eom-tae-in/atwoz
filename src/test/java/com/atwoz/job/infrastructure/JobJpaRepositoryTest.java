package com.atwoz.job.infrastructure;

import com.atwoz.job.domain.Job;
import com.atwoz.job.infrasturcture.JobJpaRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.atwoz.job.fixture.직업_픽스처.직업_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class JobJpaRepositoryTest {

    @Autowired
    private JobJpaRepository jobJpaRepository;

    private Job job;

    @BeforeEach
    void setup() {
        job = 직업_생성();
    }

    @Test
    void 직업을_저장한다() {
        // when
        Job savedJob = jobJpaRepository.save(job);

        // then
        assertThat(savedJob).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(job);
    }

    @Nested
    class 직업_조회 {

        @Test
        void 직업을_id로_조회한다() {
            // given
            Job savedJob = jobJpaRepository.save(job);
            Long jobId = savedJob.getId();

            // when
            Optional<Job> foundJob = jobJpaRepository.findJobById(jobId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundJob).isPresent();
                softly.assertThat(savedJob)
                        .usingRecursiveComparison()
                        .isEqualTo(foundJob.get());
            });
        }

        @Test
        void 직업을_이름으로_조회한다() {
            // given
            Job savedJob = jobJpaRepository.save(job);
            String jobName = savedJob.getName();

            // when
            Optional<Job> foundJob = jobJpaRepository.findJobByName(jobName);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundJob).isPresent();
                softly.assertThat(savedJob)
                        .usingRecursiveComparison()
                        .isEqualTo(foundJob.get());
            });
        }

        @Test
        void 직업을_코드로_조회한다() {
            // given
            Job savedJob = jobJpaRepository.save(job);
            String jobCode = savedJob.getCode();

            // when
            Optional<Job> foundJob = jobJpaRepository.findJobByCode(jobCode);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundJob).isPresent();
                softly.assertThat(savedJob)
                        .usingRecursiveComparison()
                        .isEqualTo(foundJob.get());
            });
        }
    }

    @Test
    void 직업을_삭제한다() {
        // given
        Job savedJob = jobJpaRepository.save(job);
        Long jobId = savedJob.getId();

        // when
        jobJpaRepository.deleteJobById(jobId);

        // then
        Optional<Job> foundJob = jobJpaRepository.findJobById(jobId);
        assertThat(foundJob).isEmpty();
    }
}
