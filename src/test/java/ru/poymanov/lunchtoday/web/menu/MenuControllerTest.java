package ru.poymanov.lunchtoday.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.poymanov.lunchtoday.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.poymanov.lunchtoday.RestaurantMenuTestData.MENU_1_ID;
import static ru.poymanov.lunchtoday.RestaurantMenuTestData.MENU_2_ID;
import static ru.poymanov.lunchtoday.RestaurantMenuTestData.MENU_1;
import static ru.poymanov.lunchtoday.RestaurantMenuTestData.MENU_2;
import static ru.poymanov.lunchtoday.UserOrderTestData.*;
import static ru.poymanov.lunchtoday.TestUtil.userHttpBasic;
import static ru.poymanov.lunchtoday.UserTestData.USER;

public class MenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuController.REST_URL;

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetTodayMenu() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ru.poymanov.lunchtoday.RestaurantMenuTestData.contentJson(MENU_1, MENU_2));
    }

    @Test
    void testOrderMenu() throws Exception {
        mockTime("09");
        mockMvc.perform(post(REST_URL + "/" + MENU_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(contentJson(ORDER));
    }

    @Test
    void testOrderNotExistedMenu() throws Exception {
        mockTime("09");
        mockMvc.perform(post(REST_URL + "/" + 999)
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void testOrderMenuAgainBefore11() throws Exception {
        mockTime("09");

        mockMvc.perform(post(REST_URL + "/" + MENU_1_ID)
                .with(userHttpBasic(USER)));

        mockTime("10");

        mockMvc.perform(post(REST_URL + "/" + MENU_2_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(contentJson(ORDER_2));
    }

    @Test
    void testOrderMenuAgainAfter11() throws Exception {
        mockTime("09");

        mockMvc.perform(post(REST_URL + "/" + MENU_1_ID)
                .with(userHttpBasic(USER)));

        mockTime("12");

        mockMvc.perform(post(REST_URL + "/" + MENU_2_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity());
    }
}
