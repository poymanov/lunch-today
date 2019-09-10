package ru.poymanov.lunchtoday.web.restaurantMenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.poymanov.lunchtoday.View;
import ru.poymanov.lunchtoday.model.Restaurant;
import ru.poymanov.lunchtoday.model.RestaurantMenu;
import ru.poymanov.lunchtoday.repository.restaurant.CrudRestaurantRepository;
import ru.poymanov.lunchtoday.repository.restaurantMenu.CrudRestaurantMenuRepository;
import ru.poymanov.lunchtoday.to.RestaurantMenuTo;
import ru.poymanov.lunchtoday.util.RestaurantMenuUtil;
import ru.poymanov.lunchtoday.web.restaurant.RestaurantController;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

import static ru.poymanov.lunchtoday.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantMenuController {
    public static final String REST_URL = RestaurantController.REST_URL + "/{restaurantId}/menu";

    private final CrudRestaurantMenuRepository repository;
    private final CrudRestaurantRepository repositoryRestaurant;

    @Autowired
    public RestaurantMenuController(CrudRestaurantMenuRepository repository, CrudRestaurantRepository repositoryRestaurant) {
        this.repository = repository;
        this.repositoryRestaurant = repositoryRestaurant;
    }

    @GetMapping
    public List<RestaurantMenuTo> getAll(@PathVariable int restaurantId) {
        return RestaurantMenuUtil.asTo(repository.findAllByRestaurantId(restaurantId));
    }

    @GetMapping("/{id}")
    public RestaurantMenuTo get(@PathVariable int restaurantId, @PathVariable int id) {
        RestaurantMenu menu = checkNotFoundWithId(repository.findByIdAndRestaurantId(id, restaurantId).orElse(null), id);
        return RestaurantMenuUtil.asTo(menu);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantMenuTo> createWithLocation(@Validated(View.Web.class) @RequestBody RestaurantMenuTo menu, @PathVariable int restaurantId) {
        checkNew(menu);

        Restaurant restaurant = checkNotFoundWithId(repositoryRestaurant.findById(restaurantId).orElse(null), restaurantId);
        RestaurantMenu created = repository.save(new RestaurantMenu(null, restaurant));
        menu.setId(created.getId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, menu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(menu);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Validated(View.Web.class) @RequestBody RestaurantMenuTo menu, @PathVariable int restaurantId, @PathVariable int id) {
        assureIdConsistent(menu, id);

        RestaurantMenu existedMenu = checkNotFoundWithId(repository.findById(id).orElse(null), id);
        Restaurant restaurant = new Restaurant(restaurantId, null);

        existedMenu.setRestaurant(restaurant);
        existedMenu.setDate(menu.getDate());

        checkNotFoundWithId(repository.save(existedMenu), menu.getId());
    }
}
