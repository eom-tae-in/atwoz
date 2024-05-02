package com.atwoz.member.ui.member;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.member.application.member.dto.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.MemberUpdateRequest;
import com.atwoz.member.fixture.MemberRequestFixture;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.atwoz.helper.RestDocsHelper.customDocument;
import static com.atwoz.member.fixture.MemberFixture.일반_유저_생성;
import static com.atwoz.member.fixture.MemberRequestFixture.회원_정보_초기화_요청서_요청;
import static com.atwoz.member.fixture.MemberResponseFixture.회원_정보_응답서_요청;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
        MemberInitializeRequest memberInitializeRequest = 회원_정보_초기화_요청서_요청();

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
                                fieldWithPath("profileInitializeRequest.birthYear").description("출생년도"),
                                fieldWithPath("profileInitializeRequest.height").description("키"),
                                fieldWithPath("profileInitializeRequest.city").description("광역시/도"),
                                fieldWithPath("profileInitializeRequest.sector").description("시/군/자치구"),
                                fieldWithPath("profileInitializeRequest.job").description("직업 코"),
                                fieldWithPath("profileInitializeRequest.graduate").description("최종학력"),
                                fieldWithPath("profileInitializeRequest.drink").description("음주 단계"),
                                fieldWithPath("profileInitializeRequest.smoke").description("흡연 단계"),
                                fieldWithPath("profileInitializeRequest.religion").description("종교"),
                                fieldWithPath("profileInitializeRequest.mbti").description("MBTI"),
                                fieldWithPath("profileInitializeRequest.hobbiesRequest").description("취미 코드 요청 정보"),
                                fieldWithPath("profileInitializeRequest.hobbiesRequest.hobbies").description("취미 코드들"),
                                fieldWithPath("profileInitializeRequest.stylesRequest").description("스타일 코드 요청 정보"),
                                fieldWithPath("profileInitializeRequest.stylesRequest.styles").description("스타일 코드들"),
                                fieldWithPath("nickname").description("회원이 사용할 닉네임"),
                                fieldWithPath("recommender").description("추천인"))
                ));
    }

    @Test
    void 회원_정보를_조회한다() throws Exception {
        // given
        Long memberId = 1L;
        MemberResponse memberResponse = 회원_정보_응답서_요청(일반_유저_생성());
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
                                fieldWithPath("memberProfileResponse.nickname").description("회원 닉네임"),
                                fieldWithPath("memberProfileResponse.phoneNumber").description("회원 전화번호"),
                                fieldWithPath("memberProfileResponse.job").description("회원 직업"),
                                fieldWithPath("memberProfileResponse.city").description("회원이 거주하는 광역시/도"),
                                fieldWithPath("memberProfileResponse.sector").description("회원이 거주하는 시/군/자치구"),
                                fieldWithPath("memberProfileResponse.graduate").description("회원 학위 정보"),
                                fieldWithPath("memberProfileResponse.smoke").description("회원 흡연 정보"),
                                fieldWithPath("memberProfileResponse.drink").description("회원 음주 정보"),
                                fieldWithPath("memberProfileResponse.religion").description("회원 종교 정보"),
                                fieldWithPath("memberProfileResponse.mbti").description("회원 MBTI 정보"),
                                fieldWithPath("memberProfileResponse.age").description("회원 나이"),
                                fieldWithPath("memberProfileResponse.height").description("회원 키"),
                                fieldWithPath("memberProfileResponse.gender").description("회원 성별"),
                                fieldWithPath("hobbiesResponse.hobbies").description("회원 취미"),
                                fieldWithPath("stylesResponse.styles").description("회원 스타일")
                        )
                ));
    }

    @Test
    void 회원_정보를_수정한다() throws Exception {
        // given
        MemberUpdateRequest memberUpdateRequest = MemberRequestFixture.회원_정보_수정_요청서_요청();

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
                                fieldWithPath("profileUpdateRequest").description("회원 프로필 수정 요청 정보"),
                                fieldWithPath("profileUpdateRequest.birthYear").description("출생년도"),
                                fieldWithPath("profileUpdateRequest.height").description("키"),
                                fieldWithPath("profileUpdateRequest.city").description("광역시/도"),
                                fieldWithPath("profileUpdateRequest.sector").description("시/군/자치구"),
                                fieldWithPath("profileUpdateRequest.job").description("직업 코"),
                                fieldWithPath("profileUpdateRequest.graduate").description("최종학력"),
                                fieldWithPath("profileUpdateRequest.drink").description("음주 단계"),
                                fieldWithPath("profileUpdateRequest.smoke").description("흡연 단계"),
                                fieldWithPath("profileUpdateRequest.religion").description("종교"),
                                fieldWithPath("profileUpdateRequest.mbti").description("MBTI"),
                                fieldWithPath("profileUpdateRequest.hobbiesRequest").description("취미 코드 요청 정보"),
                                fieldWithPath("profileUpdateRequest.hobbiesRequest.hobbies").description("취미 코드들"),
                                fieldWithPath("profileUpdateRequest.stylesRequest").description("스타일 코드 요청 정보"),
                                fieldWithPath("profileUpdateRequest.stylesRequest.styles").description("스타일 코드들"),
                                fieldWithPath("nickname").description("회원이 변경할 닉네임")
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
