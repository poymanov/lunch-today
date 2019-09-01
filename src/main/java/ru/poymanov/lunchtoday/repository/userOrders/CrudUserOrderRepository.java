package ru.poymanov.lunchtoday.repository.userOrders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.poymanov.lunchtoday.model.UserOrder;

@Transactional(readOnly = true)
public interface CrudUserOrderRepository extends JpaRepository<UserOrder, Integer> {
    @Override
    @Transactional
    UserOrder save(UserOrder user);
}
