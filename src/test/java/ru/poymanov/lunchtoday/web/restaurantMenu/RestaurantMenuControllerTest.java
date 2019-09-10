package ru.poymanov.lunchtoday.web.restaurantMenu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.poymanov.lunchtoday.repository.restaurantMenu.CrudRestaurantMenuRepository;
import ru.poymanov.lunchtoday.to.RestaurantMenuTo;
import ru.poymanov.lunchtoday.util.RestaurantMenuUtil;
import ru.poymanov.lunchtoday.web.AbstractControllerTest;
import ru.poymanov.lunchtoday.web.json.JsonUtil;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.poymanov.lunchtoday.RestaurantTestData.RESTAURANT_1_ID;
import static ru.poymanov.lunchtoday.TestUtil.readFromJson;
import static ru.poymanov.lunchtoday.TestUtil.userHttpBasic;
import static ru.poymanov.lunchtoday.UserTestData.USER;
import static ru.poymanov.lunchtoday.UserTestData.ADMIN;
import static ru.poymanov.lunchtoday.RestaurantMenuTestData.*;
import static ru.poymanov.lunchtoday.util.exception.ErrorType.VALIDATION_ERROR;

public class RestaurantMenuControllerTest extends AbstractControllerTest {
    @Autowired
    private CrudRestaurantMenuRepository repository;

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL_RESTAURANT_1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL_RESTAURANT_1)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MENU_3, MENU_4, MENU_1));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL_MENU_1)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MENU_1));
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL_RESTAURANT_1 + "/" + 1)
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL_MENU_1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(RestaurantMenuUtil.asTo(repository.findAllByRestaurantId(RESTAURANT_1_ID)), MENU_3, MENU_4);
    }

    @Test
    void testDeleteForbidden() throws Exception {
        mockMvc.perform(delete(REST_URL_MENU_1)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate() throws Exception {
        RestaurantMenuTo expected = new RestaurantMenuTo(null, RESTAURANT_1_ID);
        ResultActions action = mockMvc.perform(post(REST_URL_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        RestaurantMenuTo returned = readFromJson(action, RestaurantMenuTo.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(RestaurantMenuUtil.asTo(repository.findAllByRestaurantId(RESTAURANT_1_ID)), MENU_3, MENU_4, MENU_1, expected);
    }

    @Test
    void testCreateInvalid() throws Exception {
        RestaurantMenuTo expected = new RestaurantMenuTo(null, null);
        mockMvc.perform(post(REST_URL_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testCreateForbidden() throws Exception {
        RestaurantMenuTo expected = new RestaurantMenuTo(null, RESTAURANT_1_ID);
        mockMvc.perform(post(REST_URL_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdate() throws Exception {
        RestaurantMenuTo updated = new RestaurantMenuTo(MENU_1);
        updated.setDate(LocalDateTime.now());
        mockMvc.perform(put(REST_URL_MENU_1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(RestaurantMenuUtil.asTo(repository.findByIdAndRestaurantId(MENU_1_ID, RESTAURANT_1_ID).orElse(null)), updated);
    }

    @Test
    void testUpdateInvalid() throws Exception {
        RestaurantMenuTo updated = new RestaurantMenuTo(MENU_1);
        updated.setRestaurantId(null);
        mockMvc.perform(put(REST_URL_MENU_1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void testUpdateForbidden() throws Exception {
        RestaurantMenuTo updated = new RestaurantMenuTo(MENU_1);
        updated.setDate(LocalDateTime.now());
        mockMvc.perform(put(REST_URL_MENU_1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isForbidden());
    }
}
