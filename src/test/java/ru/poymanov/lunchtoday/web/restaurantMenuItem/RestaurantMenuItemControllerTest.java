package ru.poymanov.lunchtoday.web.restaurantMenuItem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.poymanov.lunchtoday.repository.restaurantMenuItem.CrudRestaurantMenuItemRepository;
import ru.poymanov.lunchtoday.to.RestaurantMenuItemTo;
import ru.poymanov.lunchtoday.util.RestaurantMenuItemUtil;
import ru.poymanov.lunchtoday.web.AbstractControllerTest;
import ru.poymanov.lunchtoday.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.poymanov.lunchtoday.RestaurantMenuTestData.MENU_1_ID;
import static ru.poymanov.lunchtoday.TestUtil.readFromJson;
import static ru.poymanov.lunchtoday.TestUtil.userHttpBasic;
import static ru.poymanov.lunchtoday.UserTestData.USER;
import static ru.poymanov.lunchtoday.UserTestData.ADMIN;
import static ru.poymanov.lunchtoday.RestaurantMenuItemTestData.*;
import static ru.poymanov.lunchtoday.util.exception.ErrorType.VALIDATION_ERROR;

public class RestaurantMenuItemControllerTest extends AbstractControllerTest {
    @Autowired
    private CrudRestaurantMenuItemRepository repository;

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL_MENU_1_ITEMS))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL_MENU_1_ITEMS)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ITEM_1, ITEM_2));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL_ITEM_1)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ITEM_1));
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL_MENU_1_ITEMS + "/" + 1)
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL_ITEM_1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(RestaurantMenuItemUtil.asTo(repository.findAllByMenuId(MENU_1_ID)), ITEM_2);
    }

    @Test
    void testDeleteForbidden() throws Exception {
        mockMvc.perform(delete(REST_URL_ITEM_1)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate() throws Exception {
        RestaurantMenuItemTo expected = new RestaurantMenuItemTo(null, MENU_1_ID, "Item 3", 300);
        ResultActions action = mockMvc.perform(post(REST_URL_MENU_1_ITEMS)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        RestaurantMenuItemTo returned = readFromJson(action, RestaurantMenuItemTo.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(RestaurantMenuItemUtil.asTo(repository.findAllByMenuId(MENU_1_ID)), ITEM_1, ITEM_2, expected);
    }

    @Test
    void testCreateInvalid() throws Exception {
        RestaurantMenuItemTo created = new RestaurantMenuItemTo(null, null, null, null);
        mockMvc.perform(post(REST_URL_MENU_1_ITEMS)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testCreateForbidden() throws Exception {
        RestaurantMenuItemTo expected = new RestaurantMenuItemTo(null, MENU_1_ID, "Item 3", 300);
        mockMvc.perform(post(REST_URL_MENU_1_ITEMS)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdate() throws Exception {
        RestaurantMenuItemTo updated = new RestaurantMenuItemTo(ITEM_1);
        updated.setPrice(10);
        mockMvc.perform(put(REST_URL_ITEM_1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(RestaurantMenuItemUtil.asTo(repository.findByIdAndMenuId(ITEM_1_ID, MENU_1_ID).orElse(null)), updated);
    }

    @Test
    void testUpdateInvalid() throws Exception {
        RestaurantMenuItemTo updated = new RestaurantMenuItemTo(ITEM_1);
        updated.setMenuId(null);
        mockMvc.perform(put(REST_URL_ITEM_1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void testUpdateForbidden() throws Exception {
        RestaurantMenuItemTo updated = new RestaurantMenuItemTo(ITEM_1);
        updated.setPrice(10);
        mockMvc.perform(put(REST_URL_ITEM_1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isForbidden());
    }
}
