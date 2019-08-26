package ru.poymanov.lunchtoday.repository.restaurantMenu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.poymanov.lunchtoday.model.RestaurantMenu;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantMenuRepository extends JpaRepository<RestaurantMenu, Integer> {
    @Override
    Optional<RestaurantMenu> findById(Integer id);

    @Override
    List<RestaurantMenu> findAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM RestaurantMenu r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    RestaurantMenu save(RestaurantMenu user);
}
