package ru.poymanov.lunchtoday.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_menu_id", "user_id"}, name = "user_votes_idx")})
public class UserVote extends AbstractBaseEntity {
    @OneToOne
    @JoinColumn(name = "restaurant_menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private RestaurantMenu menu;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    public UserVote() {

    }

    public UserVote(RestaurantMenu menu, User user) {
        this.menu = menu;
        this.user = user;
    }

    public RestaurantMenu getMenu() {
        return menu;
    }

    public void setMenu(RestaurantMenu menu) {
        this.menu = menu;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
