package com.atwoz.job.application;

import com.atwoz.job.application.dto.JobCreateRequest;
import com.atwoz.job.application.dto.JobUpdateRequest;
import com.atwoz.job.domain.Job;
import com.atwoz.job.domain.JobRepository;
import com.atwoz.job.exception.exceptions.JobCodeAlreadyExistedException;
import com.atwoz.job.exception.exceptions.JobNameAlreadyExistedException;
import com.atwoz.job.exception.exceptions.JobNotFoundException;
import com.atwoz.job.infrastructure.JobFakeRepository;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.atwoz.job.fixture.직업_요청_픽스처.직업_생성_요청_픽스처.직업_생성_요청_생성;
import static com.atwoz.job.fixture.직업_요청_픽스처.직업_생성_요청_픽스처.직업_생성_요청_생성_이름_코드;
import static com.atwoz.job.fixture.직업_요청_픽스처.직업_수정_요청_픽스처.직업_업데이트_요청_생성_이름_코드;
import static com.atwoz.job.fixture.직업_픽스처.직업_생성;
import static com.atwoz.job.fixture.직업_픽스처.직업_생성_이름_코드;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JobServiceTest {

    private JobService jobService;
    private JobRepository jobRepository;

    @BeforeEach
    void setup() {
        jobRepository = new JobFakeRepository();
        jobService = new JobService(jobRepository);
    }

    @Nested
    class 직업_저장 {

        @Test
        void 직업을_저장한다() {
            // given
            JobCreateRequest jobCreateRequest = 직업_생성_요청_생성();
            Long jobId = 1L;

            // when
            jobService.saveJob(jobCreateRequest);
            Optional<Job> foundJob = jobRepository.findJobById(jobId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundJob).isPresent();
                Job job = foundJob.get();
                softly.assertThat(job.getName()).isEqualTo(jobCreateRequest.name());
                softly.assertThat(job.getCode()).isEqualTo(jobCreateRequest.code());
            });
        }

        @ParameterizedTest
        @MethodSource(value = "invalidJobCreateRequests")
        void 이미_존재하는_직업_이름으로_직업을_저장_할_경우_예외가_발생한다(
                final String name,
                final String jobName,
                final String jobCode,
                final Class<Exception> exception
        ) {
            // given
            jobRepository.save(직업_생성());
            JobCreateRequest jobCreateRequest = 직업_생성_요청_생성_이름_코드(jobName, jobCode);

            // when & then
            assertThatThrownBy(() -> jobService.saveJob(jobCreateRequest))
                    .isInstanceOf(exception);
        }

        private static Stream<Arguments> invalidJobCreateRequests() {
            return Stream.of(
                    Arguments.of(
                            "이미 존재하는 직업 이름으로 생성할 경우",
                            "job1",
                            "code2",
                            JobNameAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "이미 존재하는 직업 코드로 생성할 경우",
                            "job2",
                            "code1",
                            JobCodeAlreadyExistedException.class
                    )
            );
        }
    }

    @Nested
    class 직업_변경 {

        @Test
        void 직업을_변경한다() {
            // given
            Job savedJob = jobRepository.save(직업_생성());
            JobUpdateRequest jobUpdateRequest = 직업_업데이트_요청_생성_이름_코드(
                    savedJob.getName() + "1",
                    savedJob.getCode() + "1"
            );
            Long jobId = savedJob.getId();

            // when
            jobService.updateJob(jobId, jobUpdateRequest);
            Optional<Job> foundJob = jobRepository.findJobById(jobId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundJob).isPresent();
                Job job = foundJob.get();
                softly.assertThat(job.getName()).isEqualTo(jobUpdateRequest.name());
                softly.assertThat(job.getCode()).isEqualTo(jobUpdateRequest.code());
            });
        }

        @ParameterizedTest
        @MethodSource(value = "invalidJobUpdateRequests")
        void 기존의_직업_이름_또는_코드가_변경이_없거나_이미_존재하는_값으로_변경할_경우_예외가_발생한다(
                final String name,
                final String jobName,
                final String jobCode,
                final Class<Exception> exception
        ) {
            // given
            jobRepository.save(직업_생성());
            Job savedJob = jobRepository.save(직업_생성_이름_코드("newJob", "newCode"));
            JobUpdateRequest jobUpdateRequest = 직업_업데이트_요청_생성_이름_코드(jobName, jobCode);
            Long jobId = savedJob.getId();

            // when & then
            assertThatThrownBy(() -> jobService.updateJob(jobId, jobUpdateRequest))
                    .isInstanceOf(exception);
        }

        private static Stream<Arguments> invalidJobUpdateRequests() {
            return Stream.of(
                    Arguments.of(
                            "변경하려는 직업 이름이 기존의 직업 이름과 동일한 경우",
                            "newJob",
                            "code2",
                            JobNameAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "이미 존재하는 직업 이름으로 변경할 경우",
                            "job1",
                            "code2",
                            JobNameAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "변경하려는 직업 코드가 기존의 직업 코드와 동일한 경우",
                            "job2",
                            "newCode",
                            JobCodeAlreadyExistedException.class
                    ),
                    Arguments.of(
                            "이미 존재하는 직업 코드로 변경할 경우",
                            "job2",
                            "code1",
                            JobCodeAlreadyExistedException.class
                    )
            );
        }
    }

    @Nested
    class 직업_삭제 {

        @Test
        void 직업을_삭제한다() {
            // given
            Job savedJob = jobRepository.save(직업_생성());
            Long jobId = savedJob.getId();

            // when
            jobService.deleteJob(jobId);

            // then
            assertThat(jobRepository.findJobById(jobId)).isEmpty();
        }

        @Test
        void 존재하지_않는_직업을_삭제할_경우_예외가_발생한다() {
            // given
            Long notExistJobId = 1L;

            // when & then
            assertThatThrownBy(() -> jobService.deleteJob(notExistJobId))
                    .isInstanceOf(JobNotFoundException.class);
        }
    }
}
