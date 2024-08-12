package com.atwoz.member.application.member;

import com.atwoz.member.application.member.dto.ProfileFilterRequest;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.exception.exceptions.member.MemberNicknameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.HobbyNotFoundException;
import com.atwoz.member.infrastructure.member.dto.InternalProfileFilterRequest;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;
    private final HobbyRepository hobbyRepository;

    public void checkMemberExists(final String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new MemberNicknameAlreadyExistedException();
        }
    }

    public MemberResponse findMember(final Long memberId) {
        return memberRepository.findMemberWithId(memberId);
    }

    public List<ProfileResponse> findTodayProfiles(final ProfileFilterRequest profileFilterRequest,
                                                   final Long memberId) {
        validateHobbyCode(profileFilterRequest.hobbyCode());
        return memberRepository.findTodayProfiles(InternalProfileFilterRequest.from(profileFilterRequest), memberId);
    }

    public ProfileResponse findProfileByPopularity(final ProfileFilterRequest profileFilterRequest,
                                                   final Long memberId) {
        validateHobbyCode(profileFilterRequest.hobbyCode());

        return memberRepository.findProfileByPopularity(InternalProfileFilterRequest.from(profileFilterRequest),
                memberId);
    }

    public ProfileResponse findProfileByTodayVisit(final ProfileFilterRequest profileFilterRequest,
                                                   final Long memberId) {
        validateHobbyCode(profileFilterRequest.hobbyCode());

        return memberRepository.findProfileByTodayVisit(InternalProfileFilterRequest.from(profileFilterRequest),
                memberId);
    }

    public ProfileResponse findNearbyProfile(final ProfileFilterRequest profileFilterRequest,
                                             final Long memberId) {
        validateHobbyCode(profileFilterRequest.hobbyCode());

        return memberRepository.findNearbyProfile(InternalProfileFilterRequest.from(profileFilterRequest), memberId);
    }

    public ProfileResponse findRecentProfile(final ProfileFilterRequest profileFilterRequest,
                                             final Long memberId) {
        validateHobbyCode(profileFilterRequest.hobbyCode());

        return memberRepository.findRecentProfile(InternalProfileFilterRequest.from(profileFilterRequest), memberId);
    }

    public ProfileResponse findProfileByReligion(final ProfileFilterRequest profileFilterRequest,
                                                 final Long memberId) {
        validateHobbyCode(profileFilterRequest.hobbyCode());

        return memberRepository.findProfileByReligion(InternalProfileFilterRequest.from(profileFilterRequest),
                memberId);
    }

    public ProfileResponse findProfileByHobbies(final ProfileFilterRequest profileFilterRequest,
                                                final Long memberId) {
        validateHobbyCode(profileFilterRequest.hobbyCode());

        return memberRepository.findProfileByHobbies(InternalProfileFilterRequest.from(profileFilterRequest), memberId);
    }

    private void validateHobbyCode(final String hobbyCode) {
        if (hobbyCode == null || hobbyCode.isEmpty()) {
            return;
        }
        hobbyRepository.findHobbyByCode(hobbyCode)
                .orElseThrow(HobbyNotFoundException::new);
    }
}
