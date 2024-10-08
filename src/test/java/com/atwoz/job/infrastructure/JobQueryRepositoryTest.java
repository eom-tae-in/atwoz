package com.atwoz.job.infrastructure;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.job.domain.Job;
import com.atwoz.job.domain.JobRepository;
import com.atwoz.job.infrasturcture.JobQueryRepository;
import com.atwoz.job.infrasturcture.dto.JobPagingResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.atwoz.job.fixture.직업_응답_픽스처.직업_페이징_조회_응답_픽스처.직업_페이징_조회_응답_생성_직업;
import static com.atwoz.job.fixture.직업_픽스처.직업_생성_이름_코드;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JobQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobQueryRepository jobQueryRepository;

    private List<Job> jobs;

    @BeforeEach
    void setup() {
        jobs = 직업_목록_저장();
    }

    @Test
    void 직업을_페이징해서_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<JobPagingResponse> result = jobQueryRepository.findJobWithPaging(pageable);

        // then
        List<JobPagingResponse> jobPagingResponses = result.getContent();
        assertSoftly(softly -> {
            softly.assertThat(result.hasNext()).isTrue();
            softly.assertThat(result.getTotalElements()).isEqualTo(15);
            softly.assertThat(result.getTotalPages()).isEqualTo(2);
            softly.assertThat(result.getNumber()).isEqualTo(0);
            softly.assertThat(result).hasSize(10);
            IntStream.range(0, 10)
                    .forEach(index -> {
                        JobPagingResponse jobPagingResponse = jobPagingResponses.get(index);
                        softly.assertThat(jobPagingResponse)
                                .usingRecursiveComparison()
                                .isEqualTo(직업_페이징_조회_응답_생성_직업(jobs.get(index)));
                    });
        });
    }

    private List<Job> 직업_목록_저장() {
        List<Job> savedJobs = new ArrayList<>();
        IntStream.range(0, 15)
                .forEach(index -> {
                    Job job = 직업_생성_이름_코드("job" + index, "code" + index);
                    jobRepository.save(job);
                    savedJobs.add(job);
                });

        return savedJobs;
    }
}
