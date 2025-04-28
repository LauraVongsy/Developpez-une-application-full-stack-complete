package com.mddApi.services;

import com.mddApi.dtos.SubscriptionResponseDTO;
import com.mddApi.exceptions.BadRequestException;
import com.mddApi.exceptions.NotFoundException;
import com.mddApi.models.SubscriptionId;
import com.mddApi.models.Subscriptions;
import com.mddApi.models.Themes;
import com.mddApi.repositories.SubscriptionRepository;
import com.mddApi.repositories.ThemeRepository;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class SubscriptionService {
@Autowired
    private SubscriptionRepository subscriptionRepository;
@Autowired
private ThemeRepository themeRepository;

    public void subscribeToTheme(Integer userId, Integer themeId) {
        SubscriptionId subscriptionId = new SubscriptionId(userId, themeId);

        // Vérifier si l'abonnement existe déjà
        if (!subscriptionRepository.existsById(subscriptionId)) {
            Subscriptions subscription = new Subscriptions();
            subscription.setUserId(userId);
            subscription.setThemeId(themeId);
            subscription.setSubscribedAt(Timestamp.valueOf(LocalDateTime.now()));

            // Enregistrer l'abonnement dans la base de données
            subscriptionRepository.save(subscription);
        } else {
           throw new BadRequestException("L'utilisateur est déjà abonné à ce thème");
        }
    }

    public void unsubscribeFromTheme(Integer userId, Integer themeId) {
        SubscriptionId subscriptionId = new SubscriptionId(userId, themeId);

        if (subscriptionRepository.existsById(subscriptionId)) {
            subscriptionRepository.deleteById(subscriptionId);
        }
    }

    public List<SubscriptionResponseDTO> getUserSubscriptions(Integer userId) {
        // Récupérer les abonnements de l'utilisateur
        List<Subscriptions> subscriptions = subscriptionRepository.findByUserId(userId);

        // Mapper les abonnements en SubscriptionResponseDTO
        return subscriptions.stream()
                .map(sub -> {
                    // Trouver le thème correspondant à l'abonnement
                    Themes theme = themeRepository.findById(sub.getThemeId())
                            .orElseThrow(() -> new NotFoundException("Thème introuvable pour l'abonnement."));

                    return new SubscriptionResponseDTO(
                            theme.getId(),
                            theme.getName(),
                            theme.getDescription()
                    );
                })
                .collect(Collectors.toList());
    }

}
