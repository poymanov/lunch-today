package ru.poymanov.lunchtoday;

import mockit.Mock;
import mockit.MockUp;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.poymanov.lunchtoday.to.UserVoteTo;

import java.time.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.poymanov.lunchtoday.TestUtil.readFromJsonMvcResult;
import static ru.poymanov.lunchtoday.TestUtil.readListFromJsonMvcResult;

public class UserVoteTestData {
    public static final UserVoteTo VOTE = new UserVoteTo(RestaurantMenuTestData.MENU_4_ID + 1, RestaurantMenuTestData.MENU_1_ID, UserTestData.USER_ID);
    public static final UserVoteTo VOTE_2 = new UserVoteTo(RestaurantMenuTestData.MENU_4_ID + 2, RestaurantMenuTestData.MENU_2_ID, UserTestData.USER_ID);

    public static void assertMatch(UserVoteTo actual, UserVoteTo expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<UserVoteTo> actual, UserVoteTo... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<UserVoteTo> actual, Iterable<UserVoteTo> expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }

    public static ResultMatcher contentJson(UserVoteTo... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, UserVoteTo.class), List.of(expected));
    }

    public static ResultMatcher contentJson(UserVoteTo expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, UserVoteTo.class), expected);
    }

    public static void mockTime(String hour) {
        Clock clock = Clock.fixed(Instant.parse(LocalDate.now().toString() + "T" + hour + ":00:00.00Z"), ZoneId.of("UTC"));
        new MockUp<LocalDateTime>() {
            @Mock
            public LocalDateTime now() {
                return LocalDateTime.now(clock);
            }
        };
    }
}
