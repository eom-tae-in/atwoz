//package com.atwoz.profile.ui;
//
//import com.atwoz.helper.MockBeanInjection;
//import com.atwoz.member.ui.member.MemberController;
//import com.atwoz.profile.application.ProfileQueryService;
//import com.atwoz.profile.application.ProfileService;
//import com.atwoz.profile.application.dto.ProfileCreateRequest;
//import com.atwoz.profile.application.dto.ProfileFilterRequest;
//import com.atwoz.profile.application.dto.ProfileUpdateRequest;
//import com.atwoz.profile.infrastructure.dto.ProfileRecommendationResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.util.List;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static com.atwoz.helper.RestDocsHelper.customDocument;
//import static com.atwoz.profile.fixture.프로필_요청_픽스처.프로필_생성_요청_픽스처.프로필_생성_요청_생성;
//import static com.atwoz.profile.fixture.프로필_요청_픽스처.프로필_수정_요청_픽스처.프로필_수정_요청_생성;
//import static com.atwoz.profile.fixture.프로필_요청_픽스처.프로필_필터_요청_픽스처.프로필_필터_요청_생성;
//import static com.atwoz.profile.fixture.프로필_응답_픽스처.추천_프로필_조회_응답_픽스처.추천_프로필_조회_응답_생성;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
//import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
//import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@DisplayNameGeneration(ReplaceUnderscores.class)
//@SuppressWarnings("NonAsciiCharacters")
//@AutoConfigureRestDocs
//@WebMvcTest(MemberController.class)
//class ProfileControllerWebMvcTest extends MockBeanInjection {
//
//    private static final String BEARER_TOKEN = "Bearer token";
//    private static final String COMMA = ",";
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private ProfileService profileService;
//    @Autowired
//    private ProfileQueryService profileQueryService;
//
//    @Test
//    void 닉네임이_중복되는지_확인한다() throws Exception {
//        // given
//        String nickname = "nickname";
//
//        // when & then
//        mockMvc.perform(get("/api/profiles/{nickname}/existence", nickname)
//                        .header(AUTHORIZATION, BEARER_TOKEN)
//                ).andExpect(status().isOk())
//                .andDo(customDocument("닉네임_중복_확인",
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
//                        ),
//                        pathParameters(
//                                parameterWithName("nickname").description("중복을 확인할 닉네임")
//                        )
//                ));
//    }
//
//    @Test
//    void 프로필을_저장한다() throws Exception {
//        // given
//        Long memberId = 1L;
//        ProfileCreateRequest request = 프로필_생성_요청_생성();
//        doNothing().when(profileService).saveProfile(memberId, request);
//
//        // when
//        mockMvc.perform(post("/api/profiles")
//                        .header(AUTHORIZATION, BEARER_TOKEN)
//                        .content(objectMapper.writeValueAsString(request))
//                        .contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(status().isOk())
//                .andDo(customDocument("프로필_생성",
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
//                        ),
//                        requestFields(
//                                fieldWithPath("nickname").description("회원의 닉네임"),
//                                fieldWithPath("recommender").description("추천인 닉네임"),
//                                fieldWithPath("jobCode").description("직업 코드"),
//                                fieldWithPath("graduate").description("회원의 학위 정보"),
//                                fieldWithPath("smoke").description("회원의 흡연 정보"),
//                                fieldWithPath("drink").description("회원의 음주 정보"),
//                                fieldWithPath("religion").description("회원의 종교 정보"),
//                                fieldWithPath("mbti").description("회원의 mbti 정보"),
//                                fieldWithPath("birthYear").description("회원의 출생년도 정보"),
//                                fieldWithPath("height").description("회원의 키 정보"),
//                                fieldWithPath("gender").description("회원의 성별 정보"),
//                                fieldWithPath("city").description("회원이 거주하는 광역시/도 정보"),
//                                fieldWithPath("sector").description("회원이 거주하는 시/군/자치구 정보"),
//                                fieldWithPath("hobbyCodes").description("회원의 취미코드 정보 (최대 3개까지 가능)")
//                        )
//                ));
//    }
//
//    @Test
//    void 오늘의_이성_추천_프로필을_조회한다() throws Exception {
//        // given
//        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청_생성();
//        List<ProfileRecommendationResponse> responses = List.of(추천_프로필_조회_응답_생성());
//        when(profileQueryService.findTodayProfiles(anyLong(), any(ProfileFilterRequest.class))).thenReturn(responses);
//
//        // when & then
//        mockMvc.perform(get("/api/profiles/today")
//                        .param("minAge", profileFilterRequest.minAge().toString())
//                        .param("maxAge", profileFilterRequest.maxAge().toString())
//                        .param("smoke", profileFilterRequest.smoke())
//                        .param("drink", profileFilterRequest.drink())
//                        .param("religion", profileFilterRequest.religion())
//                        .param("hobbyCode", profileFilterRequest.hobbyCode())
//                        .param("cities", profileFilterRequest.cities().toArray(new String[0]))
//                        .header(AUTHORIZATION, BEARER_TOKEN)
//                ).andExpect(status().isOk())
//                .andDo(customDocument("오늘의_이성_추천_프로필_조회",
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
//                        ),
//                        requestParts(
//                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
//                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
//                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
//                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
//                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
//                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
//                                partWithName("cities").description("선호하는 지역(검색 조건)").optional()
//                        ),
//                        responseFields(
//                                fieldWithPath("profileRecommendationResponses[].nickname").description("조회된 이성의 닉네임"),
//                                fieldWithPath("profileRecommendationResponses[].age").description("조회된 이성의 나이"),
//                                fieldWithPath("profileRecommendationResponses[].city").description("조회된 이성의 광역시/도"),
//                                fieldWithPath("profileRecommendationResponses[].sector").description("조회된 이성의 시/군/자치구"),
//                                fieldWithPath("profileRecommendationResponses[].jobCode").description("조회된 이성의 직업 코드")
//                        )
//                ));
//    }
//
//    @Test
//    void 인기있는_이성_프로필을_조회한다() throws Exception {
//        // given
//        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청_생성();
//        ProfileRecommendationResponse response = 추천_프로필_조회_응답_생성();
//        when(profileQueryService.findProfileByPopularity(anyLong(), any(ProfileFilterRequest.class)))
//                .thenReturn(response);
//
//        // when & then
//        mockMvc.perform(get("/api/profiles/popularity")
//                        .param("maxAge", profileFilterRequest.maxAge().toString())
//                        .param("minAge", profileFilterRequest.minAge().toString())
//                        .param("smoke", profileFilterRequest.smoke())
//                        .param("drink", profileFilterRequest.drink())
//                        .param("religion", profileFilterRequest.religion())
//                        .param("hobbyCode", profileFilterRequest.hobbyCode())
//                        .param("cities", profileFilterRequest.cities().toArray(new String[0]))
//                        .header(AUTHORIZATION, BEARER_TOKEN)
//                ).andExpect(status().isOk())
//                .andDo(customDocument("인기있는_이성_프로필_조회",
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
//                        ),
//                        requestParts(
//                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
//                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
//                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
//                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
//                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
//                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
//                                partWithName("cities").description("선호하는 지역(검색 조건)").optional()
//                        ),
//                        responseFields(
//                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
//                                fieldWithPath("age").description("조회된 이성의 나이"),
//                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
//                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
//                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
//                        )
//                ));
//    }
//
//    @Test
//    void 오늘_방문한_이성_프로필을_조회한다() throws Exception {
//        // given
//        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청_생성();
//        ProfileRecommendationResponse response = 추천_프로필_조회_응답_생성();
//        when(profileQueryService.findProfileByTodayVisit(anyLong(), any(ProfileFilterRequest.class)))
//                .thenReturn(response);
//
//        // when & then
//        mockMvc.perform(get("/api/profiles/today-visit")
//                        .param("minAge", profileFilterRequest.minAge().toString())
//                        .param("maxAge", profileFilterRequest.maxAge().toString())
//                        .param("smoke", profileFilterRequest.smoke())
//                        .param("drink", profileFilterRequest.drink())
//                        .param("religion", profileFilterRequest.religion())
//                        .param("hobbyCode", profileFilterRequest.hobbyCode())
//                        .param("cities", profileFilterRequest.cities().toArray(new String[0]))
//                        .header(AUTHORIZATION, BEARER_TOKEN)
//                ).andExpect(status().isOk())
//                .andDo(customDocument("오늘_방문한_이성_프로필_조회",
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
//                        ),
//                        requestParts(
//                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
//                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
//                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
//                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
//                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
//                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
//                                partWithName("profileCityRequests").description("선호하는 지역(검색 조건)").optional()
//                        ),
//                        responseFields(
//                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
//                                fieldWithPath("age").description("조회된 이성의 나이"),
//                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
//                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
//                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
//                        )
//                ));
//    }
//
//    @Test
//    void 근처에_있는_이성_프로필을_조회한다() throws Exception {
//        // given
//        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청_생성();
//        ProfileRecommendationResponse response = 추천_프로필_조회_응답_생성();
//        when(profileQueryService.findNearbyProfile(anyLong(), any(ProfileFilterRequest.class))).thenReturn(response);
//
//        // when & then
//        mockMvc.perform(get("/api/profiles/nearby")
//                        .param("minAge", profileFilterRequest.minAge().toString())
//                        .param("maxAge", profileFilterRequest.maxAge().toString())
//                        .param("smoke", profileFilterRequest.smoke())
//                        .param("drink", profileFilterRequest.drink())
//                        .param("religion", profileFilterRequest.religion())
//                        .param("hobbyCode", profileFilterRequest.hobbyCode())
//                        .param("cities", profileFilterRequest.cities().toArray(new String[0]))
//                        .header(AUTHORIZATION, BEARER_TOKEN)
//                ).andExpect(status().isOk())
//                .andDo(customDocument("근처에_있는_이성_프로필_조회",
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
//                        ),
//                        requestParts(
//                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
//                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
//                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
//                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
//                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
//                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
//                                partWithName("cities").description("선호하는 지역(검색 조건)").optional()
//                        ),
//                        responseFields(
//                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
//                                fieldWithPath("age").description("조회된 이성의 나이"),
//                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
//                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
//                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
//                        )
//                ));
//    }
//
//    @Test
//    void 최근에_가입한_이성_프로필을_조회한다() throws Exception {
//        // given
//        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청_생성();
//        ProfileRecommendationResponse response = 추천_프로필_조회_응답_생성();
//        when(profileQueryService.findRecentProfile(anyLong(), any(ProfileFilterRequest.class))).thenReturn(response);
//
//        // when & then
//        mockMvc.perform(get("/api/profiles/recency")
//                        .param("minAge", profileFilterRequest.minAge().toString())
//                        .param("maxAge", profileFilterRequest.maxAge().toString())
//                        .param("smoke", profileFilterRequest.smoke())
//                        .param("drink", profileFilterRequest.drink())
//                        .param("religion", profileFilterRequest.religion())
//                        .param("hobbyCode", profileFilterRequest.hobbyCode())
//                        .param("cities", profileFilterRequest.cities().toArray(new String[0]))
//                        .header(AUTHORIZATION, BEARER_TOKEN)
//                ).andExpect(status().isOk())
//                .andDo(customDocument("최근에_가입한_이성_프로필을_조회한다",
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
//                        ),
//                        requestParts(
//                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
//                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
//                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
//                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
//                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
//                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
//                                partWithName("cities").description("선호하는 지역(검색 조건)").optional()
//                        ),
//                        responseFields(
//                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
//                                fieldWithPath("age").description("조회된 이성의 나이"),
//                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
//                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
//                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
//                        )
//                ));
//    }
//
//    @Test
//    void 종교가_같은_이성_프로필을_조회한다() throws Exception {
//        // given
//        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청_생성();
//        ProfileRecommendationResponse response = 추천_프로필_조회_응답_생성();
//        when(profileQueryService.findProfileByReligion(anyLong(), any(ProfileFilterRequest.class)))
//                .thenReturn(response);
//
//        // when & then
//        mockMvc.perform(get("/api/profiles/religion")
//                        .param("minAge", profileFilterRequest.minAge().toString())
//                        .param("maxAge", profileFilterRequest.maxAge().toString())
//                        .param("smoke", profileFilterRequest.smoke())
//                        .param("drink", profileFilterRequest.drink())
//                        .param("religion", profileFilterRequest.religion())
//                        .param("hobbyCode", profileFilterRequest.hobbyCode())
//                        .param("cities", profileFilterRequest.cities().toArray(new String[0]))
//                        .header(AUTHORIZATION, BEARER_TOKEN)
//                ).andExpect(status().isOk())
//                .andDo(customDocument("종교가_같은_이성_프로필_조회",
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
//                        ),
//                        requestParts(
//                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
//                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
//                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
//                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
//                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
//                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
//                                partWithName("cities").description("선호하는 지역(검색 조건)").optional()
//                        ),
//                        responseFields(
//                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
//                                fieldWithPath("age").description("조회된 이성의 나이"),
//                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
//                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
//                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
//                        )
//                ));
//    }
//
//    @Test
//    void 취미가_같은_이성_프로필을_조회한다() throws Exception {
//        // given
//        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청_생성();
//        ProfileRecommendationResponse response = 추천_프로필_조회_응답_생성();
//        when(profileQueryService.findProfileByHobbies(anyLong(), any(ProfileFilterRequest.class))).thenReturn(response);
//
//        // when & then
//        mockMvc.perform(get("/api/profiles/hobbies")
//                        .param("minAge", profileFilterRequest.minAge().toString())
//                        .param("maxAge", profileFilterRequest.maxAge().toString())
//                        .param("smoke", profileFilterRequest.smoke())
//                        .param("drink", profileFilterRequest.drink())
//                        .param("religion", profileFilterRequest.religion())
//                        .param("hobbyCode", profileFilterRequest.hobbyCode())
//                        .param("cities", profileFilterRequest.cities().toArray(new String[0]))
//                        .header(AUTHORIZATION, BEARER_TOKEN)
//                ).andExpect(status().isOk())
//                .andDo(customDocument("취미가_같은_이성_프로필_조회",
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
//                        ),
//                        requestParts(
//                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
//                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
//                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
//                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
//                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
//                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
//                                partWithName("cities").description("선호하는 지역(검색 조건)").optional()
//                        ),
//                        responseFields(
//                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
//                                fieldWithPath("age").description("조회된 이성의 나이"),
//                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
//                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
//                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
//                        )
//                ));
//    }
//
//    @Test
//    void 프로필을_수정한다() throws Exception {
//        // given
//        Long memberId = 1L;
//        Long profileId = 1L;
//        ProfileUpdateRequest request = 프로필_수정_요청_생성();
//        doNothing().when(profileService).updateProfile(memberId, profileId, request);
//
//        // when
//        mockMvc.perform(patch("/api/profiles/{profileId}", profileId)
//                        .header(AUTHORIZATION, BEARER_TOKEN)
//                        .content(objectMapper.writeValueAsString(request))
//                        .contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(status().isOk())
//                .andDo(customDocument("프로필_수정",
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
//                        ),
//                        pathParameters(
//                                parameterWithName("profileId").description("수정하려는 프로필의 id")
//                        ),
//                        requestFields(
//                                fieldWithPath("nickname").description("회원의 닉네임"),
//                                fieldWithPath("jobCode").description("직업 코드"),
//                                fieldWithPath("graduate").description("회원의 학위 정보"),
//                                fieldWithPath("smoke").description("회원의 흡연 정보"),
//                                fieldWithPath("drink").description("회원의 음주 정보"),
//                                fieldWithPath("religion").description("회원의 종교 정보"),
//                                fieldWithPath("mbti").description("회원의 mbti 정보"),
//                                fieldWithPath("birthYear").description("회원의 출생년도 정보"),
//                                fieldWithPath("height").description("회원의 키 정보"),
//                                fieldWithPath("gender").description("회원의 성별 정보"),
//                                fieldWithPath("city").description("회원이 거주하는 광역시/도 정보"),
//                                fieldWithPath("sector").description("회원이 거주하는 시/군/자치구 정보"),
//                                fieldWithPath("hobbyCodes").description("회원의 취미코드 정보 (최대 3개까지 가능)")
//                        )
//                ));
//    }
//
//    @Test
//    void 프로필을_삭제한다() throws Exception {
//        // given
//        Long memberId = 1L;
//        Long profileId = 1L;
//        doNothing().when(profileService).deleteProfile(memberId, profileId);
//
//        // when
//        mockMvc.perform(delete("/api/profiles/{profileId}", profileId)
//                        .header(AUTHORIZATION, BEARER_TOKEN)
//                ).andExpect(status().isNoContent())
//                .andDo(customDocument("프로필_삭제",
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
//                        ),
//                        pathParameters(
//                                parameterWithName("profileId").description("수정하려는 프로필의 id")
//                        )
//                ));
//    }
//}
