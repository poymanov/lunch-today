package ru.poymanov.lunchtoday.repository.restaurantMenu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.poymanov.lunchtoday.model.RestaurantMenu;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantMenuRepository extends JpaRepository<RestaurantMenu, Integer> {
    @Override
    Optional<RestaurantMenu> findById(Integer id);

    Optional<RestaurantMenu> findByIdAndRestaurantId(Integer id, Integer restaurantId);

    @Override
    List<RestaurantMenu> findAll();

    List<RestaurantMenu> findAllByRestaurantId(Integer restaurantId);

    @Query("SELECT rm from RestaurantMenu rm WHERE rm.date BETWEEN :startDate AND :endDate ORDER BY rm.date DESC")
    List<RestaurantMenu> findAllBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM RestaurantMenu r WHERE r.id=:id AND r.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Override
    @Transactional
    RestaurantMenu save(RestaurantMenu menu);
}
