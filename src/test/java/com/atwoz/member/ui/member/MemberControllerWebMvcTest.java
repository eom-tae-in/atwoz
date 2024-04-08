package com.atwoz.member.ui.member;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.member.application.member.dto.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.MemberNicknameRequest;
import com.atwoz.member.application.member.dto.MemberUpdateRequest;
import com.atwoz.member.fixture.MemberRequestFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.atwoz.member.fixture.MemberRequestFixture.회원_정보_초기화_요청서_요청;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(MemberController.class)
class MemberControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 닉네임이_중복되는지_확인한다() throws Exception {
        // given
        String bearerToken = "Bearer token";
        MemberNicknameRequest memberNicknameRequest = new MemberNicknameRequest("nickname");

        // when & then
        mockMvc.perform(get("/api/members/nickname/existence")
                        .header(AUTHORIZATION, bearerToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberNicknameRequest)))
                .andExpect(status().isOk());

    }

    @Test
    void 회원_정보를_초기화한다() throws Exception {
        // given
        Long memberId = 1L;
        String bearerToken = "Bearer token";
        MemberInitializeRequest memberInitializeRequest = 회원_정보_초기화_요청서_요청();

        // when & then
        mockMvc.perform(post("/api/members/{memberId}", memberId)
                        .header(AUTHORIZATION, bearerToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberInitializeRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void 회원_정보를_수정한다() throws Exception {
        // given
        Long memberId = 1L;
        String bearerToken = "Bearer token";
        MemberUpdateRequest memberUpdateRequest = MemberRequestFixture.회원_정보_수정_요청서_요청();

        // when & then
        mockMvc.perform(patch("/api/members/{memberId}", memberId)
                        .header(AUTHORIZATION, bearerToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberUpdateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void 회원을_삭제한다() throws Exception {
        // given
        Long memberId = 1L;
        String bearerToken = "Bearer token";

        // when & then
        mockMvc.perform(delete("/api/members/{memberId}", memberId)
                        .header(AUTHORIZATION, bearerToken))
                .andExpect(status().isNoContent());

    }
}
