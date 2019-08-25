package ru.poymanov.lunchtoday;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.poymanov.lunchtoday.model.Restaurant;
import ru.poymanov.lunchtoday.model.User;
import ru.poymanov.lunchtoday.web.json.JsonUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.poymanov.lunchtoday.TestUtil.readFromJsonMvcResult;
import static ru.poymanov.lunchtoday.TestUtil.readListFromJsonMvcResult;
import static ru.poymanov.lunchtoday.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int RESTAURANT_1_ID = START_SEQ + 2;
    public static final int RESTAURANT_2_ID = RESTAURANT_1_ID + 1;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_1_ID, "restaurant_1", "Restaurant #1");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_2_ID, "restaurant_2", "Restaurant #2");

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "createdAt");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("createdAt").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Restaurant... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), expected);
    }
}
