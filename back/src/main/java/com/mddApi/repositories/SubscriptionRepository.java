package com.mddApi.repositories;

import com.mddApi.models.SubscriptionId;
import com.mddApi.models.Subscriptions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscriptions, SubscriptionId> {
    List<Subscriptions> findByUserId(int userId);
    boolean existsByUserIdAndThemeId(int userId, int themeId);
    void deleteByUserIdAndThemeId(int userId, int themeId);
    boolean existsById(SubscriptionId subscriptionId);

}
