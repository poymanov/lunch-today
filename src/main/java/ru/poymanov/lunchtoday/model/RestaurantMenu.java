package ru.poymanov.lunchtoday.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "restaurant_menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"}, name = "restaurant_menu_idx")})
public class RestaurantMenu extends AbstractBaseEntity {
    @OneToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date date = new Date();

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    private List<RestaurantMenuItem> items;

    public RestaurantMenu() {
    }

    public RestaurantMenu(Integer id, Restaurant restaurant) {
        super(id);
        this.restaurant = restaurant;
    }

    public RestaurantMenu(Integer id, Restaurant restaurant, Date date) {
        this(id, restaurant);
        this.date = date;
    }

    public RestaurantMenu(RestaurantMenu restaurantMenu) {
        this(restaurantMenu.getId(), restaurantMenu.getRestaurant(), restaurantMenu.getDate());
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<RestaurantMenuItem> getItems() {
        return items;
    }

    public void setItems(List<RestaurantMenuItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "RestaurantMenu{" +
                "restaurant=" + restaurant +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
