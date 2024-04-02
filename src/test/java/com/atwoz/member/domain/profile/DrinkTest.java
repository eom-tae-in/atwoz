package com.atwoz.member.domain.profile;

import com.atwoz.member.domain.member.profile.Drink;
import com.atwoz.member.exception.exceptions.member.profile.InvalidDrinkException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class DrinkTest {


    @DisplayName("음주_정보_값으로_Drink를_찾는다")
    @Nested
    class DrinkSearch {

        @Test
        void 음주_정보가_유효하면_음주_정보_값으로_Drink를_찾아_반환한다() {
            // given
            String validDrink = "금주 중";

            // when
            Drink drink = Drink.findByName(validDrink);

            // then
            assertThat(drink.getName()).isEqualTo(validDrink);
        }

        @Test
        void 음주_정보가_유효하지_않으면_에외가_발생한다() {
            // given
            String drink = "금연";

            // when & then
            assertThatThrownBy(() -> Drink.findByName(drink))
                    .isInstanceOf(InvalidDrinkException.class);
        }
    }
}
