package ru.poymanov.lunchtoday;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.poymanov.lunchtoday.to.RestaurantMenuTo;
import ru.poymanov.lunchtoday.web.restaurant.RestaurantController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.poymanov.lunchtoday.RestaurantTestData.RESTAURANT_1_ID;
import static ru.poymanov.lunchtoday.TestUtil.readFromJsonMvcResult;
import static ru.poymanov.lunchtoday.TestUtil.readListFromJsonMvcResult;
import static ru.poymanov.lunchtoday.model.AbstractBaseEntity.START_SEQ;

public class RestaurantMenuTestData {
    public static final int MENU_1_ID = START_SEQ + 4;
    public static final int MENU_2_ID = MENU_1_ID + 1;

    public static final RestaurantMenuTo MENU_1 = new RestaurantMenuTo(MENU_1_ID, RestaurantTestData.RESTAURANT_1_ID, RestaurantMenuItemTestData.ITEM_1, RestaurantMenuItemTestData.ITEM_2);
    public static final RestaurantMenuTo MENU_2 = new RestaurantMenuTo(MENU_2_ID, RestaurantTestData.RESTAURANT_2_ID, RestaurantMenuItemTestData.ITEM_3, RestaurantMenuItemTestData.ITEM_4);

    public static final String REST_URL_RESTAURANT_1 = RestaurantController.REST_URL + "/" + RESTAURANT_1_ID + "/menu";
    public static final String REST_URL_MENU_1 = REST_URL_RESTAURANT_1 + "/" + MENU_1_ID;

    public static void assertMatch(RestaurantMenuTo actual, RestaurantMenuTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "date");
    }

    public static void assertMatch(Iterable<RestaurantMenuTo> actual, RestaurantMenuTo... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<RestaurantMenuTo> actual, Iterable<RestaurantMenuTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("date").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(RestaurantMenuTo... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, RestaurantMenuTo.class), List.of(expected));
    }

    public static ResultMatcher contentJson(RestaurantMenuTo expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, RestaurantMenuTo.class), expected);
    }

    public static ResultMatcher contentJsonAsList(RestaurantMenuTo expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, RestaurantMenuTo.class), List.of(expected));
    }
}
