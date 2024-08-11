package com.atwoz.member.fixture.member.generator;

import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.infrastructure.member.profile.style.StyleJpaRepository;

import static com.atwoz.member.fixture.member.domain.StyleFixture.스타일_생성_이름_코드;

@SuppressWarnings("NonAsciiCharacters")
public class StyleGenerator {

    public static void 스타일_목록_생성(final StyleRepository styleRepository) {
        styleRepository.save(스타일_생성_이름_코드("style1", "code1"));
        styleRepository.save(스타일_생성_이름_코드("style2", "code2"));
        styleRepository.save(스타일_생성_이름_코드("style3", "code3"));
        styleRepository.save(스타일_생성_이름_코드("style4", "code4"));
        styleRepository.save(스타일_생성_이름_코드("style5", "code5"));
        styleRepository.save(스타일_생성_이름_코드("style6", "code6"));
    }

    public static Style 스타일_생성(final StyleRepository styleRepository,
                               final String styleName,
                               final String styleCode) {
        return styleRepository.save(스타일_생성_이름_코드(styleName, styleCode));
    }

    public static Style 스타일_생성(final StyleJpaRepository styleJpaRepository,
                               final String styleName,
                               final String styleCode) {
        return styleJpaRepository.save(스타일_생성_이름_코드(styleName, styleCode));
    }
}
