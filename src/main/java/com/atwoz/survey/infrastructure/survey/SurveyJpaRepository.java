package com.atwoz.survey.infrastructure.survey;

import com.atwoz.survey.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SurveyJpaRepository extends JpaRepository<Survey, Long> {

    Survey save(Survey survey);
    boolean existsByName(String name);
    Optional<Survey> findById(Long id);

    @Query("SELECT s.id FROM Survey s WHERE s.required = true")
    List<Long> findAllRequiredSurveyIds();
}
