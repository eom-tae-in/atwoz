package com.atwoz.member.domain.member.profile.vo;

import com.atwoz.member.exception.exceptions.member.profile.InvalidStyleException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Style {

    FASHION("패션 센스", "C001"),
    FRIENDLY("다정다감", "C002"),
    FUNNY("유머 감각", "C003"),
    GOOD_RATIO("좋은 비율", "C004"),
    PRETTY_SMILE("웃는 미소가 예쁨", "C005"),
    LOW_VOICE("저음 목소리", "C006"),
    CONSIDERABLE("넓은 배려심", "C007"),
    EXTROVERT("외향형", "C008"),
    INTROVERT("내향형", "C009"),
    ATTRACTIVE("애교쟁이", "C010"),
    GOOD_SKIN("좋은 피부", "C011"),
    POSITIVE("긍정적", "C012"),
    CUTE("귀여움", "C013"),
    RELIABLE("듬직함", "C014"),
    PRETTY_HAND("예쁜 손", "C015"),
    LOVELY("사랑스러움", "C016"),
    MOOD("분위기 있음", "C017"),
    REAL_BETTER("실물이 더 좋음", "C018"),
    INNOCENT("천진난만", "C019"),
    INTELLECTUAL("지적 섹시", "C020"),
    POLITE("예의 바름", "C021"),
    GENTLE("진중함", "C022"),
    GOOD_SPEECH("나긋나긋한 말투", "C023"),
    ONE_LOVE("순정파", "C024"),
    PURE("청순함", "C025"),
    BOLD_DOUBLE_EYELID("짙은 쌍꺼풀", "C026"),
    NONE_DOUBLE_EYELID("무쌍꺼풀", "C027"),
    INNER_DOUBLE_EYELID("속쌍꺼풀", "C028"),
    PROUD("도도함", "C029"),
    MAKER("분위기 메이커", "C030");

    private static final int MAX_SIZE = 3;

    private final String name;
    private final String code;

    Style(final String name, final String code) {
        this.name = name;
        this.code = code;
    }

    public static Style findByCode(final String code) {
        return Arrays.stream(values())
                .filter(style -> style.isSameCode(code))
                .findFirst()
                .orElseThrow(InvalidStyleException::new);
    }

    private boolean isSameCode(final String code) {
        return code.equals(this.code);
    }

    public static boolean isValidCode(final String code) {
        return Arrays.stream(values())
                .anyMatch(style -> code.equals(style.code));
    }
}
