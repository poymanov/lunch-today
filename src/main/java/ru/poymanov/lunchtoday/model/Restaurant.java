package ru.poymanov.lunchtoday.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_idx")})
public class Restaurant extends AbstractNamedEntity {
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date createdAt = new Date();

    public Restaurant() {
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getCreatedAt());
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, Date createdAt) {
        this(id, name);
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
