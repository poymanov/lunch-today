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
import ru.poymanov.lunchtoday.repository.restaurantMenu.CrudRestaurantMenuRepository;
import ru.poymanov.lunchtoday.repository.restaurantMenuItem.CrudRestaurantMenuItemRepository;
import ru.poymanov.lunchtoday.to.RestaurantMenuItemTo;
import ru.poymanov.lunchtoday.util.RestaurantMenuItemUtil;
import ru.poymanov.lunchtoday.web.restaurantMenu.RestaurantMenuController;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

import static ru.poymanov.lunchtoday.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantMenuItemController {
    public static final String REST_URL = RestaurantMenuController.REST_URL + "/{menuId}/items";

    private final CrudRestaurantMenuItemRepository repository;

    private final CrudRestaurantMenuRepository repositoryMenu;

    @Autowired
    public RestaurantMenuItemController(CrudRestaurantMenuItemRepository repository, CrudRestaurantMenuRepository repositoryMenu) {
        this.repository = repository;
        this.repositoryMenu = repositoryMenu;
    }

    @GetMapping
    public List<RestaurantMenuItemTo> getAll(@PathVariable int menuId) {
        return RestaurantMenuItemUtil.asTo(repository.findAllByMenuId(menuId));
    }

    @GetMapping("/{id}")
    public RestaurantMenuItemTo get(@PathVariable int id, @PathVariable int menuId) {
        RestaurantMenuItem item = checkNotFoundWithId(repository.findByIdAndMenuId(id, menuId).orElse(null), id);
        return RestaurantMenuItemUtil.asTo(item);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int menuId) {
        checkNotFoundWithId(repository.delete(id, menuId), id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantMenuItemTo> createWithLocation(@Validated(View.Web.class) @RequestBody RestaurantMenuItemTo item, @PathVariable int menuId) {
        checkNew(item);

        RestaurantMenu menu = checkNotFoundWithId(repositoryMenu.findById(menuId).orElse(null), menuId);
        repository.save(RestaurantMenuItemUtil.createNewFromTo(item, menu));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(menu.getRestaurant().getId(), menuId, item.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(item);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody RestaurantMenuItemTo item, @PathVariable int id, @PathVariable int menuId) {
        assureIdConsistent(item, id);

        RestaurantMenu menu = new RestaurantMenu(menuId, null);
        RestaurantMenuItem updateItem = new RestaurantMenuItem(id, item.getName(), menu, item.getPrice());

        checkNotFoundWithId(repository.save(updateItem), item.getId());
    }
}
