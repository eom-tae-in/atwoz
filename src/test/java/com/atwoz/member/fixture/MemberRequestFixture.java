package com.atwoz.member.fixture;

import com.atwoz.member.application.member.dto.HobbiesRequest;
import com.atwoz.member.application.member.dto.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.MemberUpdateRequest;
import com.atwoz.member.application.member.dto.ProfileInitializeRequest;
import com.atwoz.member.application.member.dto.ProfileUpdateRequest;
import com.atwoz.member.application.member.dto.StylesRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class MemberRequestFixture {

    public static MemberInitializeRequest 회원_정보_초기화_요청서_요청() {
        ProfileInitializeRequest profileInitializeRequest = 회원_프로필_정보_초기화_요청서_요청();
        String nickname = "nickname";
        String recommender = null;

        return new MemberInitializeRequest(profileInitializeRequest, nickname, recommender);
    }

    private static ProfileInitializeRequest 회원_프로필_정보_초기화_요청서_요청() {
        int birthYear = 2000;
        int height = 170;
        String city = "서울특별시";
        String sector = "강남구";
        String job = "A001";
        String graduate = "서울 4년제";
        String drink = "금주 중";
        String smoke = "비흡연";
        String religion = "무교";
        String mbti = "ENFP";
        List<String> hobbyCodes = List.of("B001", "B002", "B003");
        List<String> styleCodes = List.of("C001", "C002", "C003");

        return new ProfileInitializeRequest(birthYear, height, city, sector, job, graduate, drink, smoke, religion,
                mbti, new HobbiesRequest(hobbyCodes), new StylesRequest(styleCodes));
    }

    public static MemberUpdateRequest 회원_정보_수정_요청서_요청() {
        ProfileUpdateRequest profileUpdateRequest = 회원_프로필_정보_수정_요청서_요청();
        String nickname = "updateNickname";

        return new MemberUpdateRequest(profileUpdateRequest, nickname);
    }

    public static MemberUpdateRequest 회원_정보_수정_요청서_요청(final String nickname) {
        ProfileUpdateRequest profileUpdateRequest = 회원_프로필_정보_수정_요청서_요청();

        return new MemberUpdateRequest(profileUpdateRequest, nickname);
    }

    public static MemberUpdateRequest 회원_정보_수정_요청서_요청(final List<String> hobbyCodes, final List<String> styleCodes) {
        ProfileUpdateRequest profileUpdateRequest = 회원_프로필_정보_수정_요청서_요청(hobbyCodes, styleCodes);
        String nickname = "updateNickname";

        return new MemberUpdateRequest(profileUpdateRequest, nickname);
    }

    private static ProfileUpdateRequest 회원_프로필_정보_수정_요청서_요청() {
        int birthYear = 1995;
        int height = 175;
        String city = "경기도";
        String sector = "용인시";
        String job = "A005";
        String graduate = "석사";
        String drink = "전혀 마시지 않음";
        String smoke = "전자담배";
        String religion = "기독교";
        String mbti = "INFP";
        List<String> hobbyCodes = List.of("B005", "B006", "B007");
        List<String> styleCodes = List.of("C005", "C006", "C007");

        return new ProfileUpdateRequest(birthYear, height, city, sector, job, graduate, drink, smoke, religion,
                mbti, new HobbiesRequest(hobbyCodes), new StylesRequest(styleCodes));
    }

    private static ProfileUpdateRequest 회원_프로필_정보_수정_요청서_요청(final List<String> hobbyCodes,
                                                            final List<String> styleCodes) {
        int birthYear = 1995;
        int height = 175;
        String city = "경기도";
        String sector = "용인시";
        String job = "A005";
        String graduate = "석사";
        String drink = "전혀 마시지 않음";
        String smoke = "전자담배";
        String religion = "기독교";
        String mbti = "INFP";

        return new ProfileUpdateRequest(birthYear, height, city, sector, job, graduate, drink, smoke, religion,
                mbti, new HobbiesRequest(hobbyCodes), new StylesRequest(styleCodes));
    }
}
