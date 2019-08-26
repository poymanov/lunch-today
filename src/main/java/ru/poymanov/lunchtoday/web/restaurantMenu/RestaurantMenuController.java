package ru.poymanov.lunchtoday.web.restaurantMenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.poymanov.lunchtoday.View;
import ru.poymanov.lunchtoday.model.RestaurantMenu;
import ru.poymanov.lunchtoday.repository.restaurantMenu.RestaurantMenuRepository;

import java.net.URI;
import java.util.List;

import static ru.poymanov.lunchtoday.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantMenuController {
    public static final String REST_URL = "/rest/menu";

    private final RestaurantMenuRepository repository;

    @Autowired
    public RestaurantMenuController(RestaurantMenuRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<RestaurantMenu> getAll() {
        return repository.getAll();
    }

    @GetMapping("/{id}")
    public RestaurantMenu get(@PathVariable int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantMenu> createWithLocation(@Validated(View.Web.class) @RequestBody RestaurantMenu menu) {
        checkNew(menu);

        Assert.notNull(menu, "restaurant menu must not be null");
        RestaurantMenu created = repository.save(menu);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody RestaurantMenu restaurantMenu, @PathVariable int id) {
        assureIdConsistent(restaurantMenu, id);

        Assert.notNull(restaurantMenu, "restaurant menu must not be null");
        checkNotFoundWithId(repository.save(restaurantMenu), restaurantMenu.getId());
    }
}
