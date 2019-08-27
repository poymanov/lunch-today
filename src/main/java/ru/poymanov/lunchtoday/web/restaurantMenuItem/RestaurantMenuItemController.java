package ru.poymanov.lunchtoday.web.restaurantMenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.poymanov.lunchtoday.View;
import ru.poymanov.lunchtoday.model.RestaurantMenu;
import ru.poymanov.lunchtoday.model.RestaurantMenuItem;
import ru.poymanov.lunchtoday.repository.restaurantMenu.RestaurantMenuRepository;
import ru.poymanov.lunchtoday.repository.restaurantMenuItem.RestaurantMenuItemRepository;
import ru.poymanov.lunchtoday.to.RestaurantMenuItemTo;
import ru.poymanov.lunchtoday.util.RestaurantMenuItemUtil;

import java.net.URI;
import java.util.List;

import static ru.poymanov.lunchtoday.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantMenuItemController {
    public static final String REST_URL = "/rest/items";

    private final RestaurantMenuItemRepository repository;

    private final RestaurantMenuRepository repositoryMenu;

    @Autowired
    public RestaurantMenuItemController(RestaurantMenuItemRepository repository, RestaurantMenuRepository repositoryMenu) {
        this.repository = repository;
        this.repositoryMenu = repositoryMenu;
    }

    @GetMapping
    public List<RestaurantMenuItemTo> getAll() {
        return RestaurantMenuItemUtil.asTo(repository.getAll());
    }

    @GetMapping("/{id}")
    public RestaurantMenuItemTo get(@PathVariable int id) {
        RestaurantMenuItem item = checkNotFoundWithId(repository.get(id), id);
        return RestaurantMenuItemUtil.asTo(item);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantMenuItemTo> createWithLocation(@Validated(View.Web.class) @RequestBody RestaurantMenuItemTo item) {
        checkNew(item);

        RestaurantMenu menu = checkNotFoundWithId(repositoryMenu.get(item.getMenuId()), item.getMenuId());
        repository.save(RestaurantMenuItemUtil.createNewFromTo(item, menu));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(item.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(item);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody RestaurantMenuItemTo item, @PathVariable int id) {
        assureIdConsistent(item, id);

        RestaurantMenuItem existedItem = checkNotFoundWithId(repository.get(id), id);
        RestaurantMenu menu = checkNotFoundWithId(repositoryMenu.get(item.getMenuId()), item.getMenuId());

        checkNotFoundWithId(repository.save(RestaurantMenuItemUtil.updateFromTo(existedItem, item, menu)), item.getId());
    }
}
