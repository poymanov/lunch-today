package ru.poymanov.lunchtoday.repository.restaurantMenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.poymanov.lunchtoday.model.RestaurantMenu;

import java.util.List;

@Repository
public class RestaurantMenuRepositoryImpl implements RestaurantMenuRepository {
    @Autowired
    private CrudRestaurantMenuRepository crudRepository;

    @Override
    public RestaurantMenu save(RestaurantMenu restaurantMenu) {
        return crudRepository.save(restaurantMenu);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public RestaurantMenu get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public List<RestaurantMenu> getAll() {
        return crudRepository.findAll();
    }
}
