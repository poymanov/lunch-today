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
import ru.poymanov.lunchtoday.repository.restaurant.RestaurantRepository;
import ru.poymanov.lunchtoday.repository.restaurantMenu.RestaurantMenuRepository;
import ru.poymanov.lunchtoday.to.RestaurantMenuTo;
import ru.poymanov.lunchtoday.util.RestaurantMenuUtil;

import java.net.URI;
import java.util.List;

import static ru.poymanov.lunchtoday.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantMenuController {
    public static final String REST_URL = "/rest/menu";

    private final RestaurantMenuRepository repository;
    private final RestaurantRepository repositoryRestaurant;

    @Autowired
    public RestaurantMenuController(RestaurantMenuRepository repository, RestaurantRepository repositoryRestaurant) {
        this.repository = repository;
        this.repositoryRestaurant = repositoryRestaurant;
    }

    @GetMapping
    public List<RestaurantMenuTo> getAll() {
        return RestaurantMenuUtil.asTo(repository.getAll());
    }

    @GetMapping("/{id}")
    public RestaurantMenuTo get(@PathVariable int id) {
        RestaurantMenu menu = checkNotFoundWithId(repository.get(id), id);
        return RestaurantMenuUtil.asTo(menu);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantMenuTo> createWithLocation(@Validated(View.Web.class) @RequestBody RestaurantMenuTo menu) {
        checkNew(menu);

        Restaurant restaurant = checkNotFoundWithId(repositoryRestaurant.get(menu.getRestaurantId()), menu.getRestaurantId());
        RestaurantMenu created = repository.save(new RestaurantMenu(null, restaurant));
        menu.setId(created.getId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(menu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(menu);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody RestaurantMenuTo menu, @PathVariable int id) {
        assureIdConsistent(menu, id);

        RestaurantMenu existedMenu = checkNotFoundWithId(repository.get(id), id);
        Restaurant restaurant = checkNotFoundWithId(repositoryRestaurant.get(menu.getRestaurantId()), id);

        checkNotFoundWithId(repository.save(RestaurantMenuUtil.updateFromTo(existedMenu, menu, restaurant)), menu.getId());
    }
}
