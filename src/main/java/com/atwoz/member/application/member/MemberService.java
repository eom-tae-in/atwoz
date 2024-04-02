package com.atwoz.member.application.member;

import com.atwoz.member.application.member.dto.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.MemberNicknameRequest;
import com.atwoz.member.application.member.dto.MemberUpdateRequest;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.dto.MemberProfileInfo;
import com.atwoz.member.domain.member.profile.physical_profile.YearManager;
import com.atwoz.member.exception.exceptions.member.MemberNicknameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final YearManager yearManager;

    public void create(final String phoneNumber) {
        if (!memberRepository.existsByPhoneNumber(phoneNumber)) {
            memberRepository.save(Member.createWithOAuth(phoneNumber));
        }
    }

    @Transactional(readOnly = true)
    public void checkMemberExists(final MemberNicknameRequest memberNicknameRequest) {
        validateNicknameIsUnique(memberNicknameRequest.nickname());
    }

    private void validateNicknameIsUnique(final String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new MemberNicknameAlreadyExistedException();
        }
    }

    public void initializeMember(final Long memberId, final MemberInitializeRequest memberInitializeRequest) {
        Member foundMember = findMemberById(memberId);
        MemberProfileInfo memberProfileInfo = MemberProfileInfo.createWith(
                memberInitializeRequest.profileInitializeRequest(), yearManager);
        validateNicknameIsUnique(memberInitializeRequest.nickname());
        validateRecommenderNickname(memberInitializeRequest.recommender());
        foundMember.initializeWith(memberInitializeRequest.nickname(), memberInitializeRequest.recommender(),
                memberProfileInfo);
    }

    private Member findMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    private void validateRecommenderNickname(final String recommender) {
        if (recommender != null && !recommender.isBlank()) {
            validateNicknameExists(recommender);
        }
    }

    private void validateNicknameExists(final String nickname) {
        if (!memberRepository.existsByNickname(nickname)) {
            throw new MemberNotFoundException();
        }
    }

    public void updateMember(final Long memberId, final MemberUpdateRequest memberUpdateRequest) {
        Member foundMember = findMemberById(memberId);
        MemberProfileInfo memberProfileInfo = MemberProfileInfo.createWith(memberUpdateRequest.profileUpdateRequest(),
                yearManager);
        validateUpdateNickname(memberUpdateRequest.nickname(), foundMember);
        foundMember.updateWith(memberUpdateRequest.nickname(), memberProfileInfo);
    }

    private void validateUpdateNickname(final String nickname, final Member foundMember) {
        if (!nickname.equals(foundMember.getNickname())) {
            validateNicknameIsUnique(nickname);
        }
    }

    public void deleteMember(final Long memberId) {
        checkMemberExists(memberId);
        memberRepository.deleteById(memberId);
    }

    private void checkMemberExists(final Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }
    }
}
