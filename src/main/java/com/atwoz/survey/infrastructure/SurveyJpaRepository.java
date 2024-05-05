package com.atwoz.survey.infrastructure;

import com.atwoz.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyJpaRepository extends JpaRepository<Survey, Long> {

    Survey save(Survey survey);
    boolean existsByName(String name);
}
