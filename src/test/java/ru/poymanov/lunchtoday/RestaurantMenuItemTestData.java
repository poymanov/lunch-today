package ru.poymanov.lunchtoday;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.poymanov.lunchtoday.to.RestaurantMenuItemTo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.poymanov.lunchtoday.TestUtil.readFromJsonMvcResult;
import static ru.poymanov.lunchtoday.TestUtil.readListFromJsonMvcResult;
import static ru.poymanov.lunchtoday.model.AbstractBaseEntity.START_SEQ;
import static ru.poymanov.lunchtoday.RestaurantMenuTestData.REST_URL_MENU_1;

public class RestaurantMenuItemTestData {
    public static final int ITEM_1_ID = START_SEQ + 6;
    public static final int ITEM_2_ID = ITEM_1_ID + 1;
    public static final int ITEM_3_ID = ITEM_2_ID + 1;
    public static final int ITEM_4_ID = ITEM_3_ID + 1;

    public static final RestaurantMenuItemTo ITEM_1 = new RestaurantMenuItemTo(ITEM_1_ID, RestaurantMenuTestData.MENU_1_ID, "Item 1", 100);
    public static final RestaurantMenuItemTo ITEM_2 = new RestaurantMenuItemTo(ITEM_2_ID, RestaurantMenuTestData.MENU_1_ID, "Item 2", 200);
    public static final RestaurantMenuItemTo ITEM_3 = new RestaurantMenuItemTo(ITEM_3_ID, RestaurantMenuTestData.MENU_2_ID, "Item 1", 100);
    public static final RestaurantMenuItemTo ITEM_4 = new RestaurantMenuItemTo(ITEM_4_ID, RestaurantMenuTestData.MENU_2_ID, "Item 2", 200);

    public static final String REST_URL_MENU_1_ITEMS = REST_URL_MENU_1 + "/items";
    public static final String REST_URL_ITEM_1 = REST_URL_MENU_1_ITEMS + "/" + ITEM_1_ID;


    public static void assertMatch(RestaurantMenuItemTo actual, RestaurantMenuItemTo expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<RestaurantMenuItemTo> actual, RestaurantMenuItemTo... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<RestaurantMenuItemTo> actual, Iterable<RestaurantMenuItemTo> expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }

    public static ResultMatcher contentJson(RestaurantMenuItemTo... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, RestaurantMenuItemTo.class), List.of(expected));
    }

    public static ResultMatcher contentJson(RestaurantMenuItemTo expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, RestaurantMenuItemTo.class), expected);
    }
}
