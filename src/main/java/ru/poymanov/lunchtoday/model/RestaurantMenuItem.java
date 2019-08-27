package ru.poymanov.lunchtoday.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "restaurant_menu_items", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_menu_id", "name"}, name = "restaurant_menu_items_idx")})
public class RestaurantMenuItem extends AbstractNamedEntity {
    @ManyToOne
    @JoinColumn(name = "restaurant_menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private RestaurantMenu menu;

    @Column(name = "price", nullable = false)
    @NotNull
    private Integer price;

    public RestaurantMenuItem() {
    }

    public RestaurantMenuItem(Integer id, String name, RestaurantMenu menu, Integer price) {
        super(id, name);
        this.menu = menu;
        this.price = price;
    }

    public RestaurantMenuItem(RestaurantMenuItem item) {
        this(item.getId(), item.getName(), item.getMenu(), item.getPrice());
    }

    public RestaurantMenu getMenu() {
        return menu;
    }

    public void setMenu(RestaurantMenu menu) {
        this.menu = menu;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RestaurantMenuItem{" +
                "menu=" + menu +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
