package com.atwoz.mission.ui.membermission;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.mission.domain.membermission.MemberMission;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionPagingResponse;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionSimpleResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import static com.atwoz.helper.RestDocsHelper.customDocument;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_안함_데일리_id_시간있음;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(MemberMissionsController.class)
class MemberMissionsControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 회원의_모든_미션_목록을_조회한다() throws Exception {
        // given
        String bearerToken = "Bearer token";
        List<MemberMissionSimpleResponse> details = new ArrayList<>();
        for (long id = 1; id <= 2; id++) {
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함_데일리_id_시간있음(id);
            Mission mission = memberMission.getMission();
            MemberMissionSimpleResponse detail = new MemberMissionSimpleResponse(
                    mission.getId(),
                    memberMission.isDoesGetReward(),
                    mission.getReward(),
                    mission.getMissionType(),
                    memberMission.getCreatedAt());
            details.add(detail);
        }

        when(memberMissionsQueryService.findMemberMissionsWithPaging(any(), any(Pageable.class)))
                .thenReturn(new MemberMissionPagingResponse(details, 1));

        // when & then
        mockMvc.perform(get("/api/members/me/missions")
                .param("page", "0")
                .param("size", "2")
                .header(AUTHORIZATION, bearerToken))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("회원_미션_페이징조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("page").description("페이지 번호").optional(),
                                partWithName("size").description("몇 개씩 조회를 할 것인지").optional()
                        ),
                        responseFields(
                                fieldWithPath("memberMissions").description("회원 미션 목록"),
                                fieldWithPath("memberMissions[].missionId").description("미션 id"),
                                fieldWithPath("memberMissions[].doesGetReward").description("보상 가능 여부"),
                                fieldWithPath("memberMissions[].reward").description("미션 보상으로 받는 하트 개수"),
                                fieldWithPath("memberMissions[].missionType").description("미션 타입 (챌린지, 데일리)"),
                                fieldWithPath("memberMissions[].createdAt").description("회원 미션 완료 날짜"),
                                fieldWithPath("nextPage").description("다음 페이지가 존재하면 1, 없다면 -1")
                        )
                ));
    }
}
