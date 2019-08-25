package ru.poymanov.lunchtoday.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "alias", name = "restaurants_idx")})
public class Restaurant extends AbstractNamedEntity {
    @Column(name = "alias", nullable = false, unique = true)
    @NotBlank
    @Size(max = 255)
    private String alias;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt = new Date();

    public Restaurant() {
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getAlias(), restaurant.getName(), restaurant.getCreatedAt());
    }

    public Restaurant(Integer id, String alias, String name) {
        super(id, name);
        this.alias = alias;
    }

    public Restaurant(Integer id, String alias, String name, Date createdAt) {
        this(id, alias, name);
        this.createdAt = createdAt;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
                "alias='" + alias + '\'' +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
