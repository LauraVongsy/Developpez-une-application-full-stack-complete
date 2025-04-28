package com.mddApi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Subscription")
@IdClass(SubscriptionId.class)  // composite key for userId and themeId
public class Subscriptions {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "theme_id")
    private Integer themeId;

    @Column(name = "subscribed_at", nullable = false)
    private java.sql.Timestamp subscribedAt;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_subscription"))
    private Users user;

    @ManyToOne
    @JoinColumn(name = "theme_id", referencedColumnName = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_theme_subscription"))
    private Themes theme;
}
