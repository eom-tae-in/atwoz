package com.atwoz.member.application.member;

import com.atwoz.member.application.member.dto.initial.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.update.MemberUpdateRequest;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.dto.initial.InternalProfileInitializeRequest;
import com.atwoz.member.domain.member.dto.update.InternalProfileUpdateRequest;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import com.atwoz.member.exception.exceptions.member.MemberAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.MemberNicknameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.MemberNotFoundException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.InvalidHobbyException;
import com.atwoz.member.exception.exceptions.member.profile.style.InvalidStyleException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final HobbyRepository hobbyRepository;
    private final StyleRepository styleRepository;
    private final YearManager yearManager;

    public void create(final String phoneNumber) {
        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
            throw new MemberAlreadyExistedException();
        }

        memberRepository.save(Member.createWithOAuth(phoneNumber));
    }

    public void initializeMember(final Long memberId,
                                 final MemberInitializeRequest memberInitializeRequest) {
        Member foundMember = findMemberById(memberId);
        validateNicknameIsUnique(memberInitializeRequest.nickname());
        List<Hobby> hobbies = findHobbiesByCodes(memberInitializeRequest.getHobbyCodes());
        List<Style> styles = findStylesByCodes(memberInitializeRequest.getStyleCodes());
        InternalProfileInitializeRequest internalProfileInitializeRequest = InternalProfileInitializeRequest.of(
                memberInitializeRequest,
                hobbies,
                styles,
                yearManager
        );
        Long foundRecommenderId = findRecommenderIdByNickname(memberInitializeRequest.recommenderNickname());
        foundMember.initializeWith(
                memberInitializeRequest.nickname(),
                foundRecommenderId,
                internalProfileInitializeRequest
        );
    }

    private Member findMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    private void validateNicknameIsUnique(final String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new MemberNicknameAlreadyExistedException();
        }
    }

    private List<Hobby> findHobbiesByCodes(final List<String> hobbyCodes) {
        return hobbyCodes.stream()
                .map(hobbyCode -> hobbyRepository.findHobbyByCode(hobbyCode)
                        .orElseThrow(InvalidHobbyException::new))
                .toList();
    }

    private List<Style> findStylesByCodes(final List<String> styleCodes) {
        return styleCodes.stream()
                .map(styleCode -> styleRepository.findStyleByCode(styleCode)
                        .orElseThrow(InvalidStyleException::new))
                .toList();
    }

    private Long findRecommenderIdByNickname(final String nickname) {
        if (nickname == null || nickname.isBlank()) {
            return null;
        }

        return memberRepository.findByNickname(nickname)
                .orElseThrow(MemberNotFoundException::new)
                .getId();
    }

    public void updateMember(final Long memberId,
                             final MemberUpdateRequest memberUpdateRequest) {
        Member foundMember = findMemberById(memberId);
        List<Hobby> hobbies = findHobbiesByCodes(memberUpdateRequest.getHobbyCodes());
        List<Style> styles = findStylesByCodes(memberUpdateRequest.getStyleCodes());
        InternalProfileUpdateRequest internalProfileUpdateRequest = InternalProfileUpdateRequest.of(
                memberUpdateRequest,
                hobbies,
                styles,
                yearManager
        );
        validateNickname(memberUpdateRequest.nickname(), foundMember);
        foundMember.updateWith(memberUpdateRequest.nickname(), internalProfileUpdateRequest);
    }

    private void validateNickname(final String nickname,
                                  final Member foundMember) {
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

    public void validateMemberExist(final Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }
    }
}
