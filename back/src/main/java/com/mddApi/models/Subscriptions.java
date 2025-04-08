package com.mddApi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Subscription")
public class Subscriptions {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "theme_id")
    private Integer themeId;
}
