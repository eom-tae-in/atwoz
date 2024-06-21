package com.atwoz.helper;

import com.atwoz.member.application.auth.AuthService;
import com.atwoz.member.application.member.MemberQueryService;
import com.atwoz.member.application.member.MemberService;
import com.atwoz.member.domain.auth.TokenProvider;
import com.atwoz.member.ui.auth.interceptor.LoginValidCheckerInterceptor;
import com.atwoz.member.ui.auth.interceptor.ParseMemberIdFromTokenInterceptor;
import com.atwoz.member.ui.auth.interceptor.TokenRegenerateInterceptor;
import com.atwoz.member.ui.auth.support.auth.AuthenticationContext;
import com.atwoz.member.ui.auth.support.auth.OAuthProperties;
import com.atwoz.member.ui.auth.support.resolver.AuthArgumentResolver;
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

    @MockBean
    protected TokenProvider tokenProvider;

    @MockBean
    protected AuthenticationContext authenticationContext;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected OAuthProperties oAuthProperties;

    @MockBean
    protected OAuthArgumentResolver oAuthArgumentResolver;

    @MockBean
    protected AuthArgumentResolver authArgumentResolver;

    @MockBean
    protected ParseMemberIdFromTokenInterceptor parseMemberIdFromTokenInterceptor;

    @MockBean
    protected LoginValidCheckerInterceptor loginValidCheckerInterceptor;

    @MockBean
    protected TokenRegenerateInterceptor tokenRegenerateInterceptor;

    @MockBean
    protected MissionService missionService;

    @MockBean
    protected MissionQueryService missionQueryService;

    @MockBean
    protected MemberMissionsService memberMissionsService;

    @MockBean
    protected MemberMissionsQueryService memberMissionsQueryService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected SurveyService surveyService;

    @MockBean
    protected MemberSurveysService memberSurveysService;

    @MockBean
    protected MemberSurveysQueryService memberSurveysQueryService;

    @MockBean
    protected MemberQueryService memberQueryService;

    @MockBean
    protected ReportService reportService;

    @MockBean
    protected MemberLikeQueryService memberLikeQueryService;

    @MockBean
    protected MemberLikeService memberLikeService;
}
