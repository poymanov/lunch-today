package ru.poymanov.lunchtoday;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.poymanov.lunchtoday.to.RestaurantTo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.poymanov.lunchtoday.TestUtil.readFromJsonMvcResult;
import static ru.poymanov.lunchtoday.TestUtil.readListFromJsonMvcResult;
import static ru.poymanov.lunchtoday.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int RESTAURANT_1_ID = START_SEQ + 2;
    public static final int RESTAURANT_2_ID = RESTAURANT_1_ID + 1;

    public static final RestaurantTo RESTAURANT_1 = new RestaurantTo(RESTAURANT_1_ID, "Restaurant #1");
    public static final RestaurantTo RESTAURANT_2 = new RestaurantTo(RESTAURANT_2_ID, "Restaurant #2");

    public static void assertMatch(RestaurantTo actual, RestaurantTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "createdAt");
    }

    public static void assertMatch(Iterable<RestaurantTo> actual, RestaurantTo... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<RestaurantTo> actual, Iterable<RestaurantTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("createdAt").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(RestaurantTo... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, RestaurantTo.class), List.of(expected));
    }

    public static ResultMatcher contentJson(RestaurantTo expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, RestaurantTo.class), expected);
    }
}
