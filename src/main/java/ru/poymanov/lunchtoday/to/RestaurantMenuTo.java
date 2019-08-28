package ru.poymanov.lunchtoday.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RestaurantMenuTo extends BaseTo {
    @NotNull
    private Integer restaurantId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    private List<RestaurantMenuItemTo> items = new ArrayList<>();

    public RestaurantMenuTo() {
    }

    public RestaurantMenuTo(Integer id, Integer restaurantId) {
        super(id);
        this.restaurantId = restaurantId;
    }

    public RestaurantMenuTo(Integer id, Integer restaurantId, RestaurantMenuItemTo... items) {
        super(id);
        this.restaurantId = restaurantId;
        this.items = List.of(items);
    }

    public RestaurantMenuTo(Integer id, Integer restaurantId, Date date) {
        super(id);
        this.restaurantId = restaurantId;
        this.date = date;
    }

    public RestaurantMenuTo(Integer id, Integer restaurantId, Date date, RestaurantMenuItemTo... items) {
        this(id, restaurantId, date);
        this.items = List.of(items);
    }

    public RestaurantMenuTo(Integer id, Integer restaurantId, Date date, List<RestaurantMenuItemTo> items) {
        this(id, restaurantId, date);
        this.items = items;
    }

    public RestaurantMenuTo(RestaurantMenuTo menu) {
        this(menu.getId(), menu.getRestaurantId(), menu.getDate(), menu.getItems());
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<RestaurantMenuItemTo> getItems() {
        return items;
    }

    public void setItems(List<RestaurantMenuItemTo> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantMenuTo that = (RestaurantMenuTo) o;
        return restaurantId.equals(that.restaurantId) &&
                date.equals(that.date) &&
                items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, date, items);
    }

    @Override
    public String toString() {
        return "RestaurantMenuTo{" +
                "restaurantId=" + restaurantId +
                ", date=" + date +
                ", items=" + items +
                ", id=" + id +
                '}';
    }
}
