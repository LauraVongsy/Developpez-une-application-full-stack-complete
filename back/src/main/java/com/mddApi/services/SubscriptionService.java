package com.mddApi.services;

import com.mddApi.dtos.SubscriptionResponseDTO;
import com.mddApi.exceptions.BadRequestException;
import com.mddApi.exceptions.NotFoundException;
import com.mddApi.models.SubscriptionId;
import com.mddApi.models.Subscriptions;
import com.mddApi.models.Themes;
import com.mddApi.repositories.SubscriptionRepository;
import com.mddApi.repositories.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
@Autowired
private SubscriptionRepository subscriptionRepository;
@Autowired
private ThemeRepository themeRepository;

    /**
     *
     * @param userId
     * @param themeId
     * Subscription to a theme
     */
    public void subscribeToTheme(Integer userId, Integer themeId) {
        SubscriptionId subscriptionId = new SubscriptionId(userId, themeId);

        if (!subscriptionRepository.existsById(subscriptionId)) {
            Subscriptions subscription = new Subscriptions();
            subscription.setUserId(userId);
            subscription.setThemeId(themeId);
            subscription.setSubscribedAt(Timestamp.valueOf(LocalDateTime.now()));

            subscriptionRepository.save(subscription);
        } else {
           throw new BadRequestException("User already subscribed to this theme");
        }
    }

    /**
     * Unsubscription to a theme
     * @param userId
     * @param themeId
     */
    public void unsubscribeFromTheme(Integer userId, Integer themeId) {
        SubscriptionId subscriptionId = new SubscriptionId(userId, themeId);

        if (subscriptionRepository.existsById(subscriptionId)) {
            subscriptionRepository.deleteById(subscriptionId);
        }
    }

    public List<SubscriptionResponseDTO> getUserSubscriptions(Integer userId) {
        List<Subscriptions> subscriptions = subscriptionRepository.findByUserId(userId);

        return subscriptions.stream()
                .map(sub -> {
                    Themes theme = themeRepository.findById(sub.getThemeId())
                            .orElseThrow(() -> new NotFoundException("Cannot find theme for this subscription"));

                    return new SubscriptionResponseDTO(
                            theme.getId(),
                            theme.getName(),
                            theme.getDescription()
                    );
                })
                .collect(Collectors.toList());
    }

}
