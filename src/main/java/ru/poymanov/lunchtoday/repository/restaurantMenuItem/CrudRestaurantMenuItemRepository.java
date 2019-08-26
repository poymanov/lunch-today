package ru.poymanov.lunchtoday.repository.restaurantMenuItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.poymanov.lunchtoday.model.RestaurantMenuItem;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Integer> {
    @Override
    Optional<RestaurantMenuItem> findById(Integer id);

    @Override
    List<RestaurantMenuItem> findAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM RestaurantMenuItem r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    RestaurantMenuItem save(RestaurantMenuItem user);
}
