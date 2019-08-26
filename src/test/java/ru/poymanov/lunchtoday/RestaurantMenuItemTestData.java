package ru.poymanov.lunchtoday;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.poymanov.lunchtoday.model.RestaurantMenuItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.poymanov.lunchtoday.TestUtil.readFromJsonMvcResult;
import static ru.poymanov.lunchtoday.TestUtil.readListFromJsonMvcResult;
import static ru.poymanov.lunchtoday.model.AbstractBaseEntity.START_SEQ;

public class RestaurantMenuItemTestData {
    public static final int ITEM_1_ID = START_SEQ + 6;
    public static final int ITEM_2_ID = ITEM_1_ID + 1;
    public static final int ITEM_3_ID = ITEM_2_ID + 1;
    public static final int ITEM_4_ID = ITEM_3_ID + 1;

    public static final RestaurantMenuItem ITEM_1 = new RestaurantMenuItem(ITEM_1_ID, "Item 1", RestaurantMenuTestData.MENU_1, 100);
    public static final RestaurantMenuItem ITEM_2 = new RestaurantMenuItem(ITEM_2_ID, "Item 2", RestaurantMenuTestData.MENU_1, 200);
    public static final RestaurantMenuItem ITEM_3 = new RestaurantMenuItem(ITEM_3_ID, "Item 1", RestaurantMenuTestData.MENU_2, 100);
    public static final RestaurantMenuItem ITEM_4 = new RestaurantMenuItem(ITEM_4_ID, "Item 2", RestaurantMenuTestData.MENU_2, 200);

    public static void assertMatch(RestaurantMenuItem actual, RestaurantMenuItem expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<RestaurantMenuItem> actual, RestaurantMenuItem... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<RestaurantMenuItem> actual, Iterable<RestaurantMenuItem> expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }

    public static ResultMatcher contentJson(RestaurantMenuItem... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, RestaurantMenuItem.class), List.of(expected));
    }

    public static ResultMatcher contentJson(RestaurantMenuItem expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, RestaurantMenuItem.class), expected);
    }
}
