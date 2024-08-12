package com.atwoz.member.infrastructure.member.dto;

import com.atwoz.member.domain.member.profile.vo.Job;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    private String nickname;
    private int age;
    private String city;
    private String sector;
    private String jobCode;

    public ProfileResponse(final String nickname,
                           final int age,
                           final String city,
                           final String sector,
                           final Job job) {
        this.nickname = nickname;
        this.age = age;
        this.city = city;
        this.sector = sector;
        this.jobCode = job.getCode();
    }
}
