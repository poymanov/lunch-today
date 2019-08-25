package ru.poymanov.lunchtoday.util;

import ru.poymanov.lunchtoday.model.Restaurant;

public class RestaurantUtil {
    public static Restaurant prepareToSave(Restaurant restaurant) {
        restaurant.setAlias(restaurant.getAlias().toLowerCase());
        return restaurant;
    }
}
