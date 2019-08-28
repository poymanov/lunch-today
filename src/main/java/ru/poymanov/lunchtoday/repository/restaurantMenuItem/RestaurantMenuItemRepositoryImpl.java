package ru.poymanov.lunchtoday.repository.restaurantMenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.poymanov.lunchtoday.model.RestaurantMenuItem;

import java.util.List;

@Repository
public class RestaurantMenuItemRepositoryImpl implements RestaurantMenuItemRepository {
    @Autowired
    private CrudRestaurantMenuItemRepository crudRepository;

    @Override
    public RestaurantMenuItem save(RestaurantMenuItem item) {
        return crudRepository.save(item);
    }

    @Override
    public boolean delete(int id, int menuId) {
        return crudRepository.delete(id, menuId) != 0;
    }

    @Override
    public RestaurantMenuItem get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public RestaurantMenuItem getByMenu(int id, int menuId) {
        return crudRepository.findByIdAndMenuId(id, menuId).orElse(null);
    }

    @Override
    public List<RestaurantMenuItem> getAllByMenu(int menuId) {
        return crudRepository.findAllByMenuId(menuId);
    }
}
