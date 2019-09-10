package ru.poymanov.lunchtoday.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.poymanov.lunchtoday.repository.restaurant.CrudRestaurantRepository;
import ru.poymanov.lunchtoday.to.RestaurantTo;
import ru.poymanov.lunchtoday.util.RestaurantUtil;
import ru.poymanov.lunchtoday.web.AbstractControllerTest;
import ru.poymanov.lunchtoday.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.poymanov.lunchtoday.TestUtil.readFromJson;
import static ru.poymanov.lunchtoday.TestUtil.userHttpBasic;
import static ru.poymanov.lunchtoday.RestaurantTestData.*;
import static ru.poymanov.lunchtoday.UserTestData.USER;
import static ru.poymanov.lunchtoday.UserTestData.ADMIN;
import static ru.poymanov.lunchtoday.util.exception.ErrorType.VALIDATION_ERROR;

public class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @Autowired
    private CrudRestaurantRepository repository;

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
                .andExpect(contentJson(RESTAURANT_1, RESTAURANT_2));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_1));
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
        mockMvc.perform(delete(REST_URL + RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(RestaurantUtil.asTo(repository.findAll()), RESTAURANT_2);
    }

    @Test
    void testDeleteForbidden() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate() throws Exception {
        RestaurantTo expected = new RestaurantTo(null, "Restaurant #3");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        RestaurantTo returned = readFromJson(action, RestaurantTo.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(RestaurantUtil.asTo(repository.findAll()), RESTAURANT_1, RESTAURANT_2, expected);
    }

    @Test
    void testCreateInvalid() throws Exception {
        RestaurantTo expected = new RestaurantTo(null, null);
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
        RestaurantTo expected = new RestaurantTo(null, "Restaurant #3");
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdate() throws Exception {
        RestaurantTo updated = new RestaurantTo(RESTAURANT_1);
        updated.setName("Restaurant #4");
        mockMvc.perform(put(REST_URL + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(RestaurantUtil.asTo(repository.findById(RESTAURANT_1_ID).orElse(null)), updated);
    }

    @Test
    void testUpdateInvalid() throws Exception {
        RestaurantTo updated = new RestaurantTo(RESTAURANT_1);
        updated.setName("");
        mockMvc.perform(put(REST_URL + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void testUpdateForbidden() throws Exception {
        RestaurantTo updated = new RestaurantTo(RESTAURANT_1);
        updated.setName("Restaurant #4");
        mockMvc.perform(put(REST_URL + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isForbidden());
    }
}
