package com.mddApi.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * this class is for the composite key userId + themeId, to get a user subscriptions
 **/

public class SubscriptionId implements Serializable {
    private Integer userId;
    private Integer themeId;


    public SubscriptionId() {
    }

    public SubscriptionId(Integer userId, Integer themeId) {
        this.userId = userId;
        this.themeId = themeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionId that = (SubscriptionId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(themeId, that.themeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, themeId);
    }
}
