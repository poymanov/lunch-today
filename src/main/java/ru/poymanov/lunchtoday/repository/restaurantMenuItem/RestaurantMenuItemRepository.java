package ru.poymanov.lunchtoday.repository.restaurantMenuItem;

import ru.poymanov.lunchtoday.model.RestaurantMenuItem;

import java.util.List;

public interface RestaurantMenuItemRepository {
    RestaurantMenuItem save(RestaurantMenuItem restaurant);

    // false if not found
    boolean delete(int id, int menuId);

    // null if not found
    RestaurantMenuItem get(int id);

    RestaurantMenuItem getByMenu(int id, int menuId);

    List<RestaurantMenuItem> getAllByMenu(int menuId);
}
