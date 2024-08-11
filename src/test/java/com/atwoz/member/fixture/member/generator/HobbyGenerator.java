package com.atwoz.member.fixture.member.generator;

import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.infrastructure.member.profile.hobby.HobbyJpaRepository;

import static com.atwoz.member.fixture.member.domain.HobbyFixture.취미_생성_이름_코드;

@SuppressWarnings("NonAsciiCharacters")
public class HobbyGenerator {

    public static void 취미_목록_생성(final HobbyRepository hobbyRepository) {
        hobbyRepository.save(취미_생성_이름_코드("hobby1", "code1"));
        hobbyRepository.save(취미_생성_이름_코드("hobby2", "code2"));
        hobbyRepository.save(취미_생성_이름_코드("hobby3", "code3"));
        hobbyRepository.save(취미_생성_이름_코드("hobby4", "code4"));
        hobbyRepository.save(취미_생성_이름_코드("hobby5", "code5"));
        hobbyRepository.save(취미_생성_이름_코드("hobby6", "code6"));
    }

    public static Hobby 취미_생성(final HobbyRepository hobbyRepository,
                              final String hobbyName,
                              final String hobbyCode) {
        return hobbyRepository.save(취미_생성_이름_코드(hobbyName, hobbyCode));
    }

    public static Hobby 취미_생성(final HobbyJpaRepository hobbyJpaRepository,
                               final String hobbyName,
                               final String hobbyCode) {
        return hobbyJpaRepository.save(취미_생성_이름_코드(hobbyName, hobbyCode));
    }
}
