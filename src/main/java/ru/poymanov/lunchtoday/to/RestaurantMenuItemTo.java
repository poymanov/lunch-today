package ru.poymanov.lunchtoday.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class RestaurantMenuItemTo extends BaseTo {
    @NotNull
    private Integer menuId;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    private Integer price;

    public RestaurantMenuItemTo() {
    }

    public RestaurantMenuItemTo(Integer id, Integer menuId, String name, Integer price) {
        super(id);
        this.price = price;
        this.menuId = menuId;
        this.name = name;
    }

    public RestaurantMenuItemTo(RestaurantMenuItemTo item) {
        this(item.getId(), item.getMenuId(), item.getName(), item.getPrice());
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantMenuItemTo that = (RestaurantMenuItemTo) o;
        return menuId.equals(that.menuId) &&
                name.equals(that.name) &&
                price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, name, price);
    }

    @Override
    public String toString() {
        return "RestaurantMenuItemTo{" +
                "menuId=" + menuId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}
