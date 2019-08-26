package ru.poymanov.lunchtoday;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.poymanov.lunchtoday.model.RestaurantMenu;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.poymanov.lunchtoday.TestUtil.readFromJsonMvcResult;
import static ru.poymanov.lunchtoday.TestUtil.readListFromJsonMvcResult;
import static ru.poymanov.lunchtoday.model.AbstractBaseEntity.START_SEQ;

public class RestaurantMenuTestData {
    public static final int RESTAURANT_MENU_1_ID = START_SEQ + 4;
    public static final int RESTAURANT_MENU_2_ID = RESTAURANT_MENU_1_ID + 1;

    public static final RestaurantMenu RESTAURANT_MENU_1 = new RestaurantMenu(RESTAURANT_MENU_1_ID, RestaurantTestData.RESTAURANT_1);
    public static final RestaurantMenu RESTAURANT_MENU_2 = new RestaurantMenu(RESTAURANT_MENU_2_ID, RestaurantTestData.RESTAURANT_2);

    public static void assertMatch(RestaurantMenu actual, RestaurantMenu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "date");
    }

    public static void assertMatch(Iterable<RestaurantMenu> actual, RestaurantMenu... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<RestaurantMenu> actual, Iterable<RestaurantMenu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("date").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(RestaurantMenu... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, RestaurantMenu.class), List.of(expected));
    }

    public static ResultMatcher contentJson(RestaurantMenu expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, RestaurantMenu.class), expected);
    }
}
