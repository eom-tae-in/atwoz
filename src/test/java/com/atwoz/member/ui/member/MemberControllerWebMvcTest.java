package com.atwoz.member.ui.member;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.member.application.member.dto.ProfileCityFilterRequest;
import com.atwoz.member.application.member.dto.ProfileFilterRequest;
import com.atwoz.member.application.member.dto.initial.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.update.MemberUpdateRequest;
import com.atwoz.member.fixture.member.domain.MemberFixture;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.atwoz.helper.RestDocsHelper.customDocument;
import static com.atwoz.member.fixture.member.dto.request.MemberInitializeRequestFixture.회원_초기화_요청;
import static com.atwoz.member.fixture.member.dto.request.MemberUpdateRequestFixture.회원_업데이트_요청;
import static com.atwoz.member.fixture.member.dto.request.ProfileFilterRequestFixture.프로필_필터_요청서_생성;
import static com.atwoz.member.fixture.member.dto.response.MemberResponseFixture.회원_정보_응답서_요청;
import static com.atwoz.member.fixture.member.dto.response.ProfileResponseFixture.프로필_응답서_생성;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
class MemberControllerWebMvcTest extends MockBeanInjection {

    private static final String BEARER_TOKEN = "Bearer token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 닉네임이_중복되는지_확인한다() throws Exception {
        // given
        String nickname = "nickname";

        // when & then
        mockMvc.perform(get("/api/members/{nickname}/existence", nickname)
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("닉네임_중복_확인",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("nickname").description("중복을 확인할 닉네임")
                        )
                ));
    }

    @Test
    void 회원_정보를_초기화한다() throws Exception {
        // given
        MemberInitializeRequest memberInitializeRequest = 회원_초기화_요청();

        // when & then
        mockMvc.perform(post("/api/members/me")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberInitializeRequest)))
                .andExpect(status().isCreated())
                .andDo(customDocument("회원_정보_초기화",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestFields(
                                fieldWithPath("profileInitializeRequest").description("프로필 초기화 요청 정보"),
                                fieldWithPath("profileInitializeRequest.physicalProfileInitialRequest")
                                        .description("신체 프로필 초기화 요청 정보"),
                                fieldWithPath("profileInitializeRequest.physicalProfileInitialRequest.birthYear")
                                        .description("출생년도"),
                                fieldWithPath("profileInitializeRequest.physicalProfileInitialRequest.height")
                                        .description("키"),
                                fieldWithPath("profileInitializeRequest.hobbiesInitializeRequest")
                                        .description("취미 관련 초기화 정보"),
                                fieldWithPath(
                                        "profileInitializeRequest.hobbiesInitializeRequest.hobbyCodes")
                                        .description("취미 코드 목록"),
                                fieldWithPath("profileInitializeRequest.stylesInitializeRequest")
                                        .description("스타일 관련 초기화 정보"),
                                fieldWithPath("profileInitializeRequest.stylesInitializeRequest.styleCodes")
                                        .description("스타일 코드 목록"),
                                fieldWithPath("profileInitializeRequest.city").description("광역시/도"),
                                fieldWithPath("profileInitializeRequest.sector").description("시/군/자치구"),
                                fieldWithPath("profileInitializeRequest.job").description("직업 코"),
                                fieldWithPath("profileInitializeRequest.graduate").description("최종학력"),
                                fieldWithPath("profileInitializeRequest.drink").description("음주 단계"),
                                fieldWithPath("profileInitializeRequest.smoke").description("흡연 단계"),
                                fieldWithPath("profileInitializeRequest.religion").description("종교"),
                                fieldWithPath("profileInitializeRequest.mbti").description("MBTI"),
                                fieldWithPath("nickname").description("회원 닉네임"),
                                fieldWithPath("recommenderNickname").description("추천인")
                        )));
    }

    @Test
    void 회원_정보를_조회한다() throws Exception {
        // given
        Long memberId = 1L;
        MemberResponse memberResponse = 회원_정보_응답서_요청(MemberFixture.회원_생성());
        given(memberQueryService.findMember(any())).willReturn(memberResponse);

        // when & then
        mockMvc.perform(get("/api/members/{memberId}", memberId)
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("회원_정보_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("memberId").description("회원 Id")
                        ),
                        responseFields(
                                fieldWithPath("nickname").description("회원 닉네임"),
                                fieldWithPath("phoneNumber").description("회원 전화번호"),
                                fieldWithPath("age").description("회원 나이"),
                                fieldWithPath("height").description("회원 키"),
                                fieldWithPath("gender").description("회원 성별"),
                                fieldWithPath("job").description("회원 직업"),
                                fieldWithPath("city").description("회원이 거주하는 광역시/도"),
                                fieldWithPath("sector").description("회원이 거주하는 시/군/자치구"),
                                fieldWithPath("graduate").description("회원 학위 정보"),
                                fieldWithPath("smoke").description("회원 흡연 정보"),
                                fieldWithPath("drink").description("회원 음주 정보"),
                                fieldWithPath("religion").description("회원 종교 정보"),
                                fieldWithPath("mbti").description("회원 MBTI 정보"),
                                fieldWithPath("hobbyCodes").description("회원 취미 코드"),
                                fieldWithPath("styleCodes").description("회원 스타일 코드")
                        )
                ));
    }

    @Test
    void 오늘의_이성_추천_프로필을_조회한다() throws Exception {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        List<ProfileResponse> profileResponses = List.of(프로필_응답서_생성());
        when(memberQueryService.findTodayProfiles(any(ProfileFilterRequest.class), any())).thenReturn(profileResponses);

        // when & then
        mockMvc.perform(get("/api/members/profiles/today")
                        .param("maxAge", profileFilterRequest.maxAge().toString())
                        .param("minAge", profileFilterRequest.minAge().toString())
                        .param("smoke", profileFilterRequest.smoke())
                        .param("drink", profileFilterRequest.drink())
                        .param("religion", profileFilterRequest.religion())
                        .param("hobbyCode", profileFilterRequest.hobbyCode())
                        .param("profileCityRequests", profileFilterRequest.profileCityFilterRequests()
                                .stream()
                                .map(ProfileCityFilterRequest::city)
                                .collect(Collectors.joining(",")))
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("오늘의_이성_추천_프로필_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
                                partWithName("profileCityRequests").description("선호하는 지역(검색 조건)").optional()
                        ),
                        responseFields(
                                fieldWithPath("profileResponses[].nickname").description("조회된 이성의 닉네임"),
                                fieldWithPath("profileResponses[].age").description("조회된 이성의 나이"),
                                fieldWithPath("profileResponses[].city").description("조회된 이성의 광역시/도"),
                                fieldWithPath("profileResponses[].sector").description("조회된 이성의 시/군/자치구"),
                                fieldWithPath("profileResponses[].jobCode").description("조회된 이성의 직업 코드")
                        )
                ));
    }

    @Test
    void 인기있는_이성_프로필을_조회한다() throws Exception {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        ProfileResponse profileResponse = 프로필_응답서_생성();
        when(memberQueryService.findProfileByPopularity(any(ProfileFilterRequest.class), any()))
                .thenReturn(profileResponse);

        // when & then
        mockMvc.perform(get("/api/members/profiles/popularity")
                        .param("maxAge", profileFilterRequest.maxAge().toString())
                        .param("minAge", profileFilterRequest.minAge().toString())
                        .param("smoke", profileFilterRequest.smoke())
                        .param("drink", profileFilterRequest.drink())
                        .param("religion", profileFilterRequest.religion())
                        .param("hobbyCode", profileFilterRequest.hobbyCode())
                        .param("profileCityRequests", profileFilterRequest.profileCityFilterRequests()
                                .stream()
                                .map(ProfileCityFilterRequest::city)
                                .collect(Collectors.joining(", ")))
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("인기있는_이성_프로필_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
                                partWithName("profileCityRequests").description("선호하는 지역(검색 조건)").optional()
                        ),
                        responseFields(
                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
                                fieldWithPath("age").description("조회된 이성의 나이"),
                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
                        )
                ));
    }

    @Test
    void 오늘_방문한_이성_프로필을_조회한다() throws Exception {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        ProfileResponse profileResponse = 프로필_응답서_생성();
        when(memberQueryService.findProfileByTodayVisit(any(ProfileFilterRequest.class), any()))
                .thenReturn(profileResponse);

        // when & then
        mockMvc.perform(get("/api/members/profiles/today-visit")
                        .param("maxAge", profileFilterRequest.maxAge().toString())
                        .param("minAge", profileFilterRequest.minAge().toString())
                        .param("smoke", profileFilterRequest.smoke())
                        .param("drink", profileFilterRequest.drink())
                        .param("religion", profileFilterRequest.religion())
                        .param("hobbyCode", profileFilterRequest.hobbyCode())
                        .param("profileCityRequests", profileFilterRequest.profileCityFilterRequests()
                                .stream()
                                .map(ProfileCityFilterRequest::city)
                                .collect(Collectors.joining(", ")))
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("오늘_방문한_이성_프로필_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
                                partWithName("profileCityRequests").description("선호하는 지역(검색 조건)").optional()
                        ),
                        responseFields(
                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
                                fieldWithPath("age").description("조회된 이성의 나이"),
                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
                        )
                ));
    }

    @Test
    void 근처에_있는_이성_프로필을_조회한다() throws Exception {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        ProfileResponse profileResponse = 프로필_응답서_생성();
        when(memberQueryService.findNearbyProfile(any(ProfileFilterRequest.class), any()))
                .thenReturn(profileResponse);

        // when & then
        mockMvc.perform(get("/api/members/profiles/nearby")
                        .param("maxAge", profileFilterRequest.maxAge().toString())
                        .param("minAge", profileFilterRequest.minAge().toString())
                        .param("smoke", profileFilterRequest.smoke())
                        .param("drink", profileFilterRequest.drink())
                        .param("religion", profileFilterRequest.religion())
                        .param("hobbyCode", profileFilterRequest.hobbyCode())
                        .param("profileCityRequests", profileFilterRequest.profileCityFilterRequests()
                                .stream()
                                .map(ProfileCityFilterRequest::city)
                                .collect(Collectors.joining(", ")))
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("근처에_있는_이성_프로필_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
                                partWithName("profileCityRequests").description("선호하는 지역(검색 조건)").optional()
                        ),
                        responseFields(
                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
                                fieldWithPath("age").description("조회된 이성의 나이"),
                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
                        )
                ));
    }

    @Test
    void 최근에_가입한_이성_프로필을_조회한다() throws Exception {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        ProfileResponse profileResponse = 프로필_응답서_생성();
        when(memberQueryService.findRecentProfile(any(ProfileFilterRequest.class), any()))
                .thenReturn(profileResponse);

        // when & then
        mockMvc.perform(get("/api/members/profiles/recency")
                        .param("maxAge", profileFilterRequest.maxAge().toString())
                        .param("minAge", profileFilterRequest.minAge().toString())
                        .param("smoke", profileFilterRequest.smoke())
                        .param("drink", profileFilterRequest.drink())
                        .param("religion", profileFilterRequest.religion())
                        .param("hobbyCode", profileFilterRequest.hobbyCode())
                        .param("profileCityRequests", profileFilterRequest.profileCityFilterRequests()
                                .stream()
                                .map(ProfileCityFilterRequest::city)
                                .collect(Collectors.joining(", ")))
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("최근에_가입한_이성_프로필을_조회한다",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
                                partWithName("profileCityRequests").description("선호하는 지역(검색 조건)").optional()
                        ),
                        responseFields(
                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
                                fieldWithPath("age").description("조회된 이성의 나이"),
                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
                        )
                ));
    }

    @Test
    void 종교가_같은_이성_프로필을_조회한다() throws Exception {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        ProfileResponse profileResponse = 프로필_응답서_생성();
        when(memberQueryService.findProfileByReligion(any(ProfileFilterRequest.class), any()))
                .thenReturn(profileResponse);

        // when & then
        mockMvc.perform(get("/api/members/profiles/religion")
                        .param("maxAge", profileFilterRequest.maxAge().toString())
                        .param("minAge", profileFilterRequest.minAge().toString())
                        .param("smoke", profileFilterRequest.smoke())
                        .param("drink", profileFilterRequest.drink())
                        .param("religion", profileFilterRequest.religion())
                        .param("hobbyCode", profileFilterRequest.hobbyCode())
                        .param("profileCityRequests", profileFilterRequest.profileCityFilterRequests()
                                .stream()
                                .map(ProfileCityFilterRequest::city)
                                .collect(Collectors.joining(", ")))
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("종교가_같은_이성_프로필_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
                                partWithName("profileCityRequests").description("선호하는 지역(검색 조건)").optional()
                        ),
                        responseFields(
                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
                                fieldWithPath("age").description("조회된 이성의 나이"),
                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
                        )
                ));
    }

    @Test
    void 취미가_같은_이성_프로필을_조회한다() throws Exception {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        ProfileResponse profileResponse = 프로필_응답서_생성();
        when(memberQueryService.findProfileByHobbies(any(ProfileFilterRequest.class), any()))
                .thenReturn(profileResponse);

        // when & then
        mockMvc.perform(get("/api/members/profiles/hobbies")
                        .param("maxAge", profileFilterRequest.maxAge().toString())
                        .param("minAge", profileFilterRequest.minAge().toString())
                        .param("smoke", profileFilterRequest.smoke())
                        .param("drink", profileFilterRequest.drink())
                        .param("religion", profileFilterRequest.religion())
                        .param("hobbyCode", profileFilterRequest.hobbyCode())
                        .param("profileCityRequests", profileFilterRequest.profileCityFilterRequests()
                                .stream()
                                .map(ProfileCityFilterRequest::city)
                                .collect(Collectors.joining(", ")))
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("취미가_같은_이성_프로필_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("maxAge").description("최대 나이(검색 조건)").optional(),
                                partWithName("minAge").description("최소 나이(검색 조건)").optional(),
                                partWithName("smoke").description("흡연 정보(검색 조건)").optional(),
                                partWithName("drink").description("음주 정보(검색 조건)").optional(),
                                partWithName("religion").description("종교 정보(검색 조건)").optional(),
                                partWithName("hobbyCode").description("취미 코드(검색 조건)").optional(),
                                partWithName("profileCityRequests").description("선호하는 지역(검색 조건)").optional()
                        ),
                        responseFields(
                                fieldWithPath("nickname").description("조회된 이성의 닉네임"),
                                fieldWithPath("age").description("조회된 이성의 나이"),
                                fieldWithPath("city").description("조회된 이성의 광역시/도"),
                                fieldWithPath("sector").description("조회된 이성의 시/군/자치구"),
                                fieldWithPath("jobCode").description("조회된 이성의 직업 코드")
                        )
                ));
    }

    @Test
    void 회원_정보를_수정한다() throws Exception {
        // given
        MemberUpdateRequest memberUpdateRequest = 회원_업데이트_요청();

        // when & then
        mockMvc.perform(patch("/api/members/me")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(customDocument("회원_정보_수정",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("인증 토큰 정보")
                        ),
                        requestFields(
                                fieldWithPath("profileUpdateRequest").description("프로필 수정 요청 정보"),
                                fieldWithPath("profileUpdateRequest.physicalProfileUpdateRequest").description(
                                        "변경하려는 신체 프로필 수정 요청 정보"),
                                fieldWithPath(
                                        "profileUpdateRequest.physicalProfileUpdateRequest.birthYear").description(
                                        "출생년도"),
                                fieldWithPath("profileUpdateRequest.physicalProfileUpdateRequest.height").description(
                                        "키"),
                                fieldWithPath("profileUpdateRequest.hobbiesUpdateRequest").description(
                                        "변경하려는 취미 관련 정보"),
                                fieldWithPath("profileUpdateRequest.hobbiesUpdateRequest.hobbyCodes").description(
                                        "취미 코드 목록"),
                                fieldWithPath("profileUpdateRequest.stylesUpdateRequest").description(
                                        "변경하려는 스타일 관련 정보"),
                                fieldWithPath("profileUpdateRequest.stylesUpdateRequest.styleCodes").description(
                                        "스타일 코드 목록"),
                                fieldWithPath("profileUpdateRequest.city").description("광역시/도"),
                                fieldWithPath("profileUpdateRequest.sector").description("시/군/자치구"),
                                fieldWithPath("profileUpdateRequest.job").description("직업 코"),
                                fieldWithPath("profileUpdateRequest.graduate").description("최종학력"),
                                fieldWithPath("profileUpdateRequest.drink").description("음주 단계"),
                                fieldWithPath("profileUpdateRequest.smoke").description("흡연 단계"),
                                fieldWithPath("profileUpdateRequest.religion").description("종교"),
                                fieldWithPath("profileUpdateRequest.mbti").description("MBTI"),
                                fieldWithPath("nickname").description("변경할 회원 닉네임")
                        )
                ));
    }

    @Test
    void 회원을_삭제한다() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/members/me")
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(customDocument("회원_삭제",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("인증 토큰 정보")
                        )
                ));
    }
}
