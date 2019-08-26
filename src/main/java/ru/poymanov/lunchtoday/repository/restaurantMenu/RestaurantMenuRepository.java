package ru.poymanov.lunchtoday.repository.restaurantMenu;

import ru.poymanov.lunchtoday.model.RestaurantMenu;

import java.util.List;

public interface RestaurantMenuRepository {
    RestaurantMenu save(RestaurantMenu restaurant);

    // false if not found
    boolean delete(int id);

    // null if not found
    RestaurantMenu get(int id);

    List<RestaurantMenu> getAll();
}
