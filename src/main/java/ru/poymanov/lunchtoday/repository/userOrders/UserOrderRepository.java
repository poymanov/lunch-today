package ru.poymanov.lunchtoday.repository.userOrders;

import ru.poymanov.lunchtoday.model.UserOrder;

public interface UserOrderRepository {
    UserOrder save(UserOrder restaurant);
}
