package com.atwoz.selfintro.domain;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.selfintro.exception.exceptions.InvalidContentException;
import com.atwoz.selfintro.exception.exceptions.WriterNotEqualsException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class SelfIntro extends BaseEntity {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(length = MAX_LENGTH, nullable = false)
    private String content;

    public static SelfIntro createWith(final Long memberId,
                                       final String content) {
        validateContent(content);
        return SelfIntro.builder()
                .memberId(memberId)
                .content(content)
                .build();
    }

    private static void validateContent(final String content) {
        int contentLength = content.length();
        if (contentLength < MIN_LENGTH || MAX_LENGTH < contentLength) {
            throw new InvalidContentException();
        }
    }

    public void validateSameWriter(final Long memberId) {
        if (!Objects.equals(this.memberId, memberId)) {
            throw new WriterNotEqualsException();
        }
    }

    public void update(final String content) {
        validateContent(content);
        this.content = content;
    }
}
