package ru.poymanov.lunchtoday.repository.restaurantMenu;

import ru.poymanov.lunchtoday.model.RestaurantMenu;

import java.util.List;

public interface RestaurantMenuRepository {
    RestaurantMenu save(RestaurantMenu restaurant);

    // false if not found
    boolean delete(int id, int restaurantId);

    // null if not found
    RestaurantMenu get(int id);

    RestaurantMenu getByRestaurant(int id, int restaurantId);

    List<RestaurantMenu> getAll();

    List<RestaurantMenu> getAllByRestaurant(int restaurantId);
}
