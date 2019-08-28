package ru.poymanov.lunchtoday.util;

import ru.poymanov.lunchtoday.model.Restaurant;
import ru.poymanov.lunchtoday.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {
    public static RestaurantTo asTo(Restaurant item) {
        return new RestaurantTo(item.getId(), item.getName(), item.getCreatedAt());
    }

    public static List<RestaurantTo> asTo(List<Restaurant> item) {
        return item.stream().map(el -> new RestaurantTo(el.getId(), el.getName(), el.getCreatedAt())).collect(Collectors.toList());
    }

    public static Restaurant createNewFromTo(RestaurantTo newItem) {
        return new Restaurant(newItem.getId(), newItem.getName());
    }

    public static Restaurant updateFromTo(Restaurant existedItem, RestaurantTo item) {
        existedItem.setName(item.getName());
        existedItem.setCreatedAt(item.getCreatedAt());

        return existedItem;
    }
}
