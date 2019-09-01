package ru.poymanov.lunchtoday.web.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.poymanov.lunchtoday.service.menu.MenuService;
import ru.poymanov.lunchtoday.to.RestaurantMenuTo;
import ru.poymanov.lunchtoday.to.UserOrderTo;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {
    public static final String REST_URL = "/rest/menu";

    @Autowired
    public MenuService service;

    @GetMapping
    public List<RestaurantMenuTo> getToday() {
        return service.getTodayMenu();
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<UserOrderTo> createWithLocation(@PathVariable int id) {
        UserOrderTo order = service.orderMenu(id);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(order);
    }
}
