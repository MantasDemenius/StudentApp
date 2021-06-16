package com.app.ktu.common;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateCheckTest {

    @Test
    void isLocalDateTimeEqualOrBetween_DateIsBetween_True() {
        // Setup
        final LocalDateTime date = LocalDateTime.of(2021, 1, 1, 1, 1);
        final LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);;
        final LocalDateTime endDate = LocalDateTime.of(2022, 1, 1, 1, 1);;

        // Run the test
        final boolean result = DateCheck.isLocalDateTimeEqualOrBetween(date, startDate, endDate);

//         Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void isLocalDateTimeEqualOrBetween_StartDateIsEqual_True() {
        // Setup
        final LocalDateTime date = LocalDateTime.of(2021, 1, 1, 1, 1);
        final LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 1, 1);;
        final LocalDateTime endDate = LocalDateTime.of(2022, 1, 1, 1, 1);;

        // Run the test
        final boolean result = DateCheck.isLocalDateTimeEqualOrBetween(date, startDate, endDate);

//         Verify the results
        assertThat(result).isTrue();
    }
}
