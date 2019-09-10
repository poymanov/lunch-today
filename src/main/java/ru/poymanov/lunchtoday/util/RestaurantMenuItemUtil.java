package ru.poymanov.lunchtoday.util;

import ru.poymanov.lunchtoday.model.RestaurantMenu;
import ru.poymanov.lunchtoday.model.RestaurantMenuItem;
import ru.poymanov.lunchtoday.to.RestaurantMenuItemTo;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantMenuItemUtil {
    public static RestaurantMenuItemTo asTo(RestaurantMenuItem item) {
        return new RestaurantMenuItemTo(item.getId(), item.getMenu().getId(), item.getName(), item.getPrice());
    }

    public static List<RestaurantMenuItemTo> asTo(List<RestaurantMenuItem> item) {
        return item.stream().map(el -> new RestaurantMenuItemTo(el.getId(), el.getMenu().getId(), el.getName(), el.getPrice())).collect(Collectors.toList());
    }

    public static RestaurantMenuItem createNewFromTo(RestaurantMenuItemTo newItem, RestaurantMenu menu) {
        return new RestaurantMenuItem(null, newItem.getName(), menu, newItem.getPrice());
    }
}
