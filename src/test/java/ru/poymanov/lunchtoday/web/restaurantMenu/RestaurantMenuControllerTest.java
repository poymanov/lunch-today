package ru.poymanov.lunchtoday.web.restaurantMenu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.poymanov.lunchtoday.model.RestaurantMenu;
import ru.poymanov.lunchtoday.repository.restaurantMenu.RestaurantMenuRepository;
import ru.poymanov.lunchtoday.web.AbstractControllerTest;
import ru.poymanov.lunchtoday.web.json.JsonUtil;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.poymanov.lunchtoday.RestaurantTestData.RESTAURANT_1;
import static ru.poymanov.lunchtoday.TestUtil.readFromJson;
import static ru.poymanov.lunchtoday.TestUtil.userHttpBasic;
import static ru.poymanov.lunchtoday.UserTestData.USER;
import static ru.poymanov.lunchtoday.UserTestData.ADMIN;
import static ru.poymanov.lunchtoday.RestaurantMenuTestData.*;
import static ru.poymanov.lunchtoday.util.exception.ErrorType.VALIDATION_ERROR;

public class RestaurantMenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantMenuController.REST_URL + '/';

    @Autowired
    private RestaurantMenuRepository repository;

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_MENU_1, RESTAURANT_MENU_2));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_MENU_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_MENU_1));
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT_MENU_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(repository.getAll(), RESTAURANT_MENU_2);
    }

    @Test
    void testDeleteForbidden() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT_MENU_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate() throws Exception {
        RestaurantMenu expected = new RestaurantMenu(null, RESTAURANT_1);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        RestaurantMenu returned = readFromJson(action, RestaurantMenu.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(repository.getAll(), RESTAURANT_MENU_1, RESTAURANT_MENU_2, expected);
    }

    @Test
    void testCreateInvalid() throws Exception {
        RestaurantMenu expected = new RestaurantMenu(null, null);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testCreateForbidden() throws Exception {
        RestaurantMenu expected = new RestaurantMenu(null, RESTAURANT_1);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdate() throws Exception {
        RestaurantMenu updated = new RestaurantMenu(RESTAURANT_MENU_1);
        updated.setDate(new Date());
        mockMvc.perform(put(REST_URL + RESTAURANT_MENU_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(repository.get(RESTAURANT_MENU_1_ID), updated);
    }

    @Test
    void testUpdateInvalid() throws Exception {
        RestaurantMenu updated = new RestaurantMenu(RESTAURANT_MENU_1);
        updated.setRestaurant(null);
        mockMvc.perform(put(REST_URL + RESTAURANT_MENU_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void testUpdateForbidden() throws Exception {
        RestaurantMenu updated = new RestaurantMenu(RESTAURANT_MENU_1);
        updated.setDate(new Date());
        mockMvc.perform(put(REST_URL + RESTAURANT_MENU_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isForbidden());
    }
}
