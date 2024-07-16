package com.atwoz.helper;

import com.atwoz.admin.application.auth.AdminAuthService;
import com.atwoz.admin.ui.auth.interceptor.AdminLoginValidCheckerInterceptor;
import com.atwoz.admin.ui.auth.support.AdminAuthenticationContext;
import com.atwoz.admin.ui.auth.support.AdminAuthenticationExtractor;
import com.atwoz.admin.ui.auth.support.resolver.AdminAuthArgumentResolver;
import com.atwoz.admin.ui.auth.support.resolver.AdminRefreshTokenExtractionArgumentResolver;
import com.atwoz.member.application.auth.MemberAuthService;
import com.atwoz.member.application.member.MemberQueryService;
import com.atwoz.member.application.member.MemberService;
import com.atwoz.member.application.selfintro.SelfIntroQueryService;
import com.atwoz.member.application.selfintro.SelfIntroService;
import com.atwoz.member.domain.auth.JsonMapper;
import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.ui.auth.interceptor.MemberLoginValidCheckerInterceptor;
import com.atwoz.member.ui.auth.support.MemberAuthenticationContext;
import com.atwoz.member.ui.auth.support.MemberAuthenticationExtractor;
import com.atwoz.member.ui.auth.support.OAuthProperties;
import com.atwoz.member.ui.auth.support.resolver.MemberAuthArgumentResolver;
import com.atwoz.member.ui.auth.support.resolver.OAuthArgumentResolver;
import com.atwoz.memberlike.application.MemberLikeQueryService;
import com.atwoz.memberlike.application.MemberLikeService;
import com.atwoz.mission.application.membermission.MemberMissionsQueryService;
import com.atwoz.mission.application.membermission.MemberMissionsService;
import com.atwoz.mission.application.mission.MissionQueryService;
import com.atwoz.mission.application.mission.MissionService;
import com.atwoz.report.application.ReportService;
import com.atwoz.survey.application.membersurvey.MemberSurveysQueryService;
import com.atwoz.survey.application.membersurvey.MemberSurveysService;
import com.atwoz.survey.application.survey.SurveyService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@MockBean(JpaMetamodelMappingContext.class)
public class MockBeanInjection {

    // Member
    @MockBean
    protected OAuthArgumentResolver oAuthArgumentResolver;

    @MockBean
    protected MemberAuthArgumentResolver memberAuthArgumentResolver;

    @MockBean
    protected MemberLoginValidCheckerInterceptor memberLoginValidCheckerInterceptor;

    @MockBean
    protected MemberAuthenticationContext memberAuthenticationContext;

    @MockBean
    protected MemberAuthenticationExtractor authenticationExtractor;

    @MockBean
    protected OAuthProperties oAuthProperties;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected MemberQueryService memberQueryService;

    @MockBean
    protected MemberAuthService memberAuthService;

    @MockBean
    protected MemberTokenProvider memberTokenProvider;

    @MockBean
    protected MemberRepository memberRepository;

    @MockBean
    protected JsonMapper jsonMapper;

    // Admin
    @MockBean
    protected AdminAuthArgumentResolver adminAuthArgumentResolver;

    @MockBean
    protected AdminRefreshTokenExtractionArgumentResolver adminRefreshTokenExtractionArgumentResolver;

    @MockBean
    protected AdminLoginValidCheckerInterceptor adminLoginValidCheckerInterceptor;

    @MockBean
    protected AdminAuthenticationContext adminAuthenticationContext;

    @MockBean
    protected AdminAuthenticationExtractor adminAuthenticationExtractor;

    @MockBean
    protected AdminAuthService adminAuthService;

    // Mission
    @MockBean
    protected MissionService missionService;

    @MockBean
    protected MissionQueryService missionQueryService;

    @MockBean
    protected MemberMissionsService memberMissionsService;

    @MockBean
    protected MemberMissionsQueryService memberMissionsQueryService;

    // Survey
    @MockBean
    protected SurveyService surveyService;

    @MockBean
    protected MemberSurveysService memberSurveysService;

    @MockBean
    protected MemberSurveysQueryService memberSurveysQueryService;

    // Report
    @MockBean
    protected ReportService reportService;

    @MockBean
    protected MemberLikeQueryService memberLikeQueryService;

    @MockBean
    protected MemberLikeService memberLikeService;

    // SelfIntro
    @MockBean
    protected SelfIntroQueryService selfIntroQueryService;

    @MockBean
    protected SelfIntroService selfIntroService;
}
