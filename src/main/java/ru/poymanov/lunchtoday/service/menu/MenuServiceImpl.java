package ru.poymanov.lunchtoday.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.poymanov.lunchtoday.model.RestaurantMenu;
import ru.poymanov.lunchtoday.model.User;
import ru.poymanov.lunchtoday.model.UserOrder;
import ru.poymanov.lunchtoday.repository.restaurantMenu.RestaurantMenuRepository;
import ru.poymanov.lunchtoday.repository.user.UserRepository;
import ru.poymanov.lunchtoday.repository.userOrders.UserOrderRepository;
import ru.poymanov.lunchtoday.to.RestaurantMenuTo;
import ru.poymanov.lunchtoday.to.UserOrderTo;
import ru.poymanov.lunchtoday.util.RestaurantMenuUtil;
import ru.poymanov.lunchtoday.util.exception.IllegalRequestDataException;
import ru.poymanov.lunchtoday.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final RestaurantMenuRepository menuRepository;
    private final UserOrderRepository userOrderRepository;
    private final UserRepository userRepository;

    @Autowired
    public MenuServiceImpl(RestaurantMenuRepository repository, UserOrderRepository userOrderRepository, UserRepository userRepository) {
        this.menuRepository = repository;
        this.userOrderRepository = userOrderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RestaurantMenuTo> getTodayMenu() {
        LocalDate now = LocalDate.now();
        return RestaurantMenuUtil.asTo(menuRepository.getAllBetween(LocalDateTime.of(now, LocalTime.MIN), LocalDateTime.of(now, LocalTime.MAX)));
    }

    @Override
    public UserOrderTo orderMenu(int id) {
        RestaurantMenu menu = menuRepository.get(id);

        if (menu == null) {
            throw new IllegalRequestDataException("Menu not found");
        }

        LocalDateTime orderDeadline = LocalDateTime.parse(LocalDate.now().toString() + "T11:00:00");

        if (LocalDateTime.now().isAfter(orderDeadline)) {
            throw new IllegalRequestDataException("Trying to order after 11:00");
        }

        User user = userRepository.get(SecurityUtil.authUserId());
        UserOrder created = userOrderRepository.save(new UserOrder(menu, user));
        return new UserOrderTo(created.getId(), menu.getId(), user.getId());
    }
}
