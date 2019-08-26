package ru.poymanov.lunchtoday.web.restaurantMenuItem;

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
import ru.poymanov.lunchtoday.model.RestaurantMenuItem;
import ru.poymanov.lunchtoday.repository.restaurantMenuItem.RestaurantMenuItemRepository;

import java.net.URI;
import java.util.List;

import static ru.poymanov.lunchtoday.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantMenuItemController {
    public static final String REST_URL = "/rest/items";

    private final RestaurantMenuItemRepository repository;

    @Autowired
    public RestaurantMenuItemController(RestaurantMenuItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<RestaurantMenuItem> getAll() {
        return repository.getAll();
    }

    @GetMapping("/{id}")
    public RestaurantMenuItem get(@PathVariable int id) {
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
    public ResponseEntity<RestaurantMenuItem> createWithLocation(@Validated(View.Web.class) @RequestBody RestaurantMenuItem item) {
        checkNew(item);

        Assert.notNull(item, "restaurant menu item must not be null");
        RestaurantMenuItem created = repository.save(item);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody RestaurantMenuItem item, @PathVariable int id) {
        assureIdConsistent(item, id);

        Assert.notNull(item, "restaurant menu must not be null");
        checkNotFoundWithId(repository.save(item), item.getId());
    }
}
