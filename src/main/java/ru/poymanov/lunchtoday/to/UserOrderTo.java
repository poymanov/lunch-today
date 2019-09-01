package ru.poymanov.lunchtoday.to;

import java.util.Objects;

public class UserOrderTo extends BaseTo {
    private Integer restaurantId;

    private Integer userId;

    public UserOrderTo() {

    }

    public UserOrderTo(Integer id, Integer restaurantId, Integer userId) {
        super(id);
        this.restaurantId = restaurantId;
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOrderTo that = (UserOrderTo) o;
        return restaurantId.equals(that.restaurantId) &&
                userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, userId);
    }

    @Override
    public String toString() {
        return "UserOrderTo{" +
                "restaurantId=" + restaurantId +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }
}
