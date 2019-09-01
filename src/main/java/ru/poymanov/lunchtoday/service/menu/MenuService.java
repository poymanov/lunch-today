package ru.poymanov.lunchtoday.service.menu;

import ru.poymanov.lunchtoday.to.RestaurantMenuTo;
import ru.poymanov.lunchtoday.to.UserOrderTo;

import java.util.List;

public interface MenuService {
    List<RestaurantMenuTo> getTodayMenu();

    UserOrderTo orderMenu(int id);
}
