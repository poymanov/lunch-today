package ru.poymanov.lunchtoday.service.menu;

import ru.poymanov.lunchtoday.to.RestaurantMenuTo;
import ru.poymanov.lunchtoday.to.UserVoteTo;

import java.util.List;

public interface MenuService {
    List<RestaurantMenuTo> getTodayMenu();

    UserVoteTo voteMenu(int id);
}
