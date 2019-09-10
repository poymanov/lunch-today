package ru.poymanov.lunchtoday.web.restaurant;

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
import ru.poymanov.lunchtoday.repository.restaurant.CrudRestaurantRepository;
import ru.poymanov.lunchtoday.to.RestaurantTo;
import ru.poymanov.lunchtoday.util.RestaurantUtil;

import java.net.URI;
import java.util.List;

import static ru.poymanov.lunchtoday.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    public static final String REST_URL = "/rest/restaurants";

    private final CrudRestaurantRepository repository;

    @Autowired
    public RestaurantController(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        return RestaurantUtil.asTo(repository.findAll());
    }

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        Restaurant restaurant = checkNotFoundWithId(repository.findById(id).orElse(null), id);
        return RestaurantUtil.asTo(restaurant);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> createWithLocation(@Validated(View.Web.class) @RequestBody RestaurantTo restaurant) {
        checkNew(restaurant);

        Restaurant created = repository.save(RestaurantUtil.createNewFromTo(restaurant));
        restaurant.setId(created.getId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurant);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody RestaurantTo restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);

        Restaurant existedItem = checkNotFoundWithId(repository.findById(id).orElse(null), id);

        checkNotFoundWithId(repository.save(RestaurantUtil.updateFromTo(existedItem, restaurant)), restaurant.getId());
    }
}
