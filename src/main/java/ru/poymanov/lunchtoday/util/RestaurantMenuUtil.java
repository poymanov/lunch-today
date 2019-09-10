package ru.poymanov.lunchtoday.util;

import ru.poymanov.lunchtoday.model.RestaurantMenu;
import ru.poymanov.lunchtoday.to.RestaurantMenuItemTo;
import ru.poymanov.lunchtoday.to.RestaurantMenuTo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantMenuUtil {
    public static RestaurantMenuTo asTo(RestaurantMenu menu) {
        return new RestaurantMenuTo(menu.getId(), menu.getRestaurant().getId(), menu.getDate(), RestaurantMenuItemUtil.asTo(menu.getItems()));
    }

    public static List<RestaurantMenuTo> asTo(List<RestaurantMenu> menu) {
        return menu.stream().map((el) -> {
            List<RestaurantMenuItemTo> items = el.getItems() != null ? RestaurantMenuItemUtil.asTo(el.getItems()) : new ArrayList<>();
            return new RestaurantMenuTo(el.getId(), el.getRestaurant().getId(), el.getDate(), items);
        }).collect(Collectors.toList());
    }
}
