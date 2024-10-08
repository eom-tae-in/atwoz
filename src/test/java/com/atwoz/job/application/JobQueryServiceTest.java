package com.atwoz.job.application;

import com.atwoz.job.application.dto.JobPagingResponses;
import com.atwoz.job.domain.Job;
import com.atwoz.job.domain.JobRepository;
import com.atwoz.job.exception.exceptions.JobNotFoundException;
import com.atwoz.job.infrastructure.JobFakeRepository;
import com.atwoz.job.infrasturcture.dto.JobPagingResponse;
import com.atwoz.job.infrasturcture.dto.JobSingleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.atwoz.job.fixture.직업_응답_픽스처.직업_단건_조회_응답_픽스처.직업_단건_조회_응답_생성_직업;
import static com.atwoz.job.fixture.직업_응답_픽스처.직업_페이징_조회_응답_픽스처.직업_페이징_조회_응답_생성_직업;
import static com.atwoz.job.fixture.직업_픽스처.직업_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JobQueryServiceTest {

    private JobQueryService jobQueryService;
    private JobRepository jobRepository;

    @BeforeEach
    void setup() {
        jobRepository = new JobFakeRepository();
        jobQueryService = new JobQueryService(jobRepository);
    }

    @Nested
    class 직업_조회 {

        @Test
        void 직업을_조회한다() {
            // given
            Job savedJob = jobRepository.save(직업_생성());
            Long jobId = savedJob.getId();

            // when
            JobSingleResponse response = jobQueryService.findJob(jobId);

            // then
            JobSingleResponse expectedResponse = 직업_단건_조회_응답_생성_직업(savedJob);
            assertThat(response).usingRecursiveComparison()
                    .isEqualTo(expectedResponse);
        }

        @Test
        void 없는_직업을_조회할_경우_예외가_발생한다() {
            // given
            Long invalidJobId = 1L;

            // when & then
            assertThatThrownBy(() -> jobQueryService.findJob(invalidJobId))
                    .isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    void 직업을_페이징으로_조회한다() {
        // given
        Job savedJob = jobRepository.save(직업_생성());
        Pageable pageable = PageRequest.of(0, 10);

        // when
        JobPagingResponses jobPagingResponses = jobQueryService.findJobsWithPaging(pageable);

        // then
        assertSoftly(softly -> {
            softly.assertThat(jobPagingResponses.nowPage()).isEqualTo(0);
            softly.assertThat(jobPagingResponses.nextPage()).isEqualTo(-1);
            softly.assertThat(jobPagingResponses.totalPages()).isEqualTo(1);
            softly.assertThat(jobPagingResponses.totalElements()).isEqualTo(1);
            JobPagingResponse jobPagingResponse = jobPagingResponses.jobPagingResponses().get(0);
            JobPagingResponse expectedJobPagingResponse = 직업_페이징_조회_응답_생성_직업(savedJob);
            softly.assertThat(jobPagingResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedJobPagingResponse);
        });
    }
}
