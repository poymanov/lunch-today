package ru.poymanov.lunchtoday.repository.userOrders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.poymanov.lunchtoday.model.UserOrder;

@Repository
public class UserOrderRepositoryImpl implements UserOrderRepository {
    @Autowired
    private CrudUserOrderRepository crudRepository;

    @Override
    public UserOrder save(UserOrder order) {
        return crudRepository.save(order);
    }
}
