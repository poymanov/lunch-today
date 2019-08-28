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
    public boolean delete(int id, int restaurantId) {
        return crudRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public RestaurantMenu get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public RestaurantMenu getByRestaurant(int id, int restaurantId) {
        return crudRepository.findByIdAndRestaurantId(id, restaurantId).orElse(null);
    }

    @Override
    public List<RestaurantMenu> getAll() {
        return crudRepository.findAll();
    }

    @Override
    public List<RestaurantMenu> getAllByRestaurant(int restaurantId) {
        return crudRepository.findAllByRestaurantId(restaurantId);
    }
}
