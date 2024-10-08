//package com.atwoz.survey.application.membersurvey;
//
//import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
//import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;
//import com.atwoz.survey.domain.survey.SurveyRepository;
//import com.atwoz.survey.exception.membersurvey.exceptions.RequiredSurveyNotSubmittedException;
//import com.atwoz.survey.exception.membersurvey.exceptions.SurveyAnswerInvalidSubmitException;
//import com.atwoz.survey.exception.membersurvey.exceptions.SurveyQuestionSubmitSizeNotMatchException;
//import com.atwoz.survey.exception.membersurvey.exceptions.SurveySubmitDuplicateException;
//import com.atwoz.survey.exception.survey.exceptions.SurveyNotFoundException;
//import com.atwoz.survey.infrastructure.membersurvey.MemberSurveysFakeRepository;
//import com.atwoz.survey.infrastructure.survey.SurveyFakeRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.List;
//import java.util.stream.Stream;
//import static com.atwoz.survey.fixture.SurveyFixture.연애고사_선택_과목_질문_두개씩;
//import static com.atwoz.survey.fixture.SurveyFixture.연애고사_선택_과목_질문_하나;
//import static com.atwoz.survey.fixture.SurveyFixture.연애고사_필수_과목_질문_두개씩;
//import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.같은_필수_과목_제출_요청;
//import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.선택_과목_같은_질문_답변_제출_요청;
//import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.선택_과목_없는_답변_제출_요청;
//import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.선택_과목_없는_질문_답변_제출_요청;
//import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.선택_과목_질문_답변_일부분만_제출_요청;
//import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.선택_과목_질문_두개_제출_요청;
//import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.없는_과목_제출_요청;
//import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.필수_과목_질문_두개_제출_요청;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@SuppressWarnings("NonAsciiCharacters")
//class MemberSurveysServiceTest {
//
//    private MemberSurveysService memberSurveysService;
//    private MemberSurveysRepository memberSurveysRepository;
//    private SurveyRepository surveyRepository;
//    private Long memberId = 1L;
//
//    @BeforeEach
//    void init() {
//        memberSurveysRepository = new MemberSurveysFakeRepository();
//        surveyRepository = new SurveyFakeRepository();
//        memberSurveysService = new MemberSurveysService(memberSurveysRepository, surveyRepository);
//    }
//
//    @Test
//    void 회원이_연애고사_과목을_응시한다() {
//        // given
//        List<SurveySubmitRequest> requests = 필수_과목_질문_두개_제출_요청();
//        surveyRepository.save(연애고사_필수_과목_질문_두개씩());
//
//        // when & then
//        assertDoesNotThrow(() -> memberSurveysService.submitSurvey(memberId, requests));
//    }
//
//    @Nested
//    class 연애고사_과목_응시_예외 {
//
//        @Test
//        void 필수_과목을_응시하지_않으면_예외가_발생한다() {
//            // given
//            List<SurveySubmitRequest> requests = 선택_과목_질문_두개_제출_요청();
//            surveyRepository.save(연애고사_필수_과목_질문_두개씩());
//            surveyRepository.save(연애고사_선택_과목_질문_두개씩());
//
//            // when & then
//            assertThatThrownBy(() -> memberSurveysService.submitSurvey(memberId, requests))
//                    .isInstanceOf(RequiredSurveyNotSubmittedException.class);
//        }
//
//        @Test
//        void 같은_과목을_응시하면_예외가_발생한다() {
//            // given
//            List<SurveySubmitRequest> requests = 같은_필수_과목_제출_요청();
//            surveyRepository.save(연애고사_필수_과목_질문_두개씩());
//            surveyRepository.save(연애고사_선택_과목_질문_두개씩());
//
//            // when & then
//            assertThatThrownBy(() -> memberSurveysService.submitSurvey(memberId, requests))
//                    .isInstanceOf(SurveySubmitDuplicateException.class);
//        }
//
//        @Test
//        void 없는_과목을_응시하면_예외가_발생한다() {
//            // given
//            List<SurveySubmitRequest> requests = 없는_과목_제출_요청();
//            surveyRepository.save(연애고사_선택_과목_질문_두개씩());
//
//            // when & then
//            assertThatThrownBy(() -> memberSurveysService.submitSurvey(memberId, requests))
//                    .isInstanceOf(SurveyNotFoundException.class);
//        }
//
//        @MethodSource("invalidQuestionSizeInputs")
//        @ParameterizedTest
//        void 과목이_가진_질문_수와_요청한_질문_수가_다르면_예외가_발생한다(final List<SurveySubmitRequest> requests) {
//            // given
//            surveyRepository.save(연애고사_선택_과목_질문_하나());
//
//            // when & then
//            assertThatThrownBy(() -> memberSurveysService.submitSurvey(memberId, requests))
//                    .isInstanceOf(SurveyQuestionSubmitSizeNotMatchException.class);
//        }
//
//        // 없는 질문에 답하거나, 같은 질문에 답하거나, 과목의 일부분 질문만 답하는 경우
//        static Stream<Arguments> invalidQuestionSizeInputs() {
//            return Stream.of(
//                    Arguments.arguments(선택_과목_같은_질문_답변_제출_요청()),
//                    Arguments.arguments(선택_과목_없는_질문_답변_제출_요청()),
//                    Arguments.arguments(선택_과목_질문_답변_일부분만_제출_요청())
//            );
//        }
//
//        @Test
//        void 없는_번호의_답변_번호를_선택하면_예외가_발생한다() {
//            // given
//            List<SurveySubmitRequest> requests = 선택_과목_없는_답변_제출_요청();
//            surveyRepository.save(연애고사_선택_과목_질문_하나());
//
//            // when & then
//            assertThatThrownBy(() -> memberSurveysService.submitSurvey(memberId, requests))
//                    .isInstanceOf(SurveyAnswerInvalidSubmitException.class);
//        }
//    }
//}
