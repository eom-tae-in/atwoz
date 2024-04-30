package com.atwoz.member.infrastructure.member.dto;

import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.domain.member.profile.vo.Drink;
import com.atwoz.member.domain.member.profile.vo.Graduate;
import com.atwoz.member.domain.member.profile.vo.Job;
import com.atwoz.member.domain.member.profile.vo.Location;
import com.atwoz.member.domain.member.profile.vo.Mbti;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.profile.vo.Smoke;
import lombok.Builder;

@Builder
public record MemberProfileResponse(
        String nickname,
        String phoneNumber,
        String job,
        String city,
        String sector,
        String graduate,
        String smoke,
        String drink,
        String religion,
        String mbti,
        int age,
        int height,
        String gender
) {

    public static MemberProfileResponse createWith(final String nickname,
                                                   final String phoneNumber,
                                                   final Job job,
                                                   final Location location,
                                                   final Graduate graduate,
                                                   final Smoke smoke,
                                                   final Drink drink,
                                                   final Religion religion,
                                                   final Mbti mbti,
                                                   final int age,
                                                   final int height,
                                                   final Gender gender) {
        return MemberProfileResponse.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .job(job.getName())
                .city(location.getCity())
                .sector(location.getSector())
                .graduate(graduate.getName())
                .smoke(smoke.getName())
                .drink(drink.getName())
                .religion(religion.getName())
                .mbti(mbti.name())
                .age(age)
                .height(height)
                .gender(gender.getName())
                .build();
    }
}
