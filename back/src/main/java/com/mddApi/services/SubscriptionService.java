package com.mddApi.services;

import com.mddApi.dtos.SubscriptionResponseDTO;
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
            // Gérer si l'abonnement existe déjà (par exemple, afficher un message)
            System.out.println("L'utilisateur est déjà abonné à ce thème.");
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
                    Optional<Themes> theme = themeRepository.findById(sub.getThemeId());

                    // Si un thème est trouvé, mapper en SubscriptionResponseDTO
                    return theme.map(t -> new SubscriptionResponseDTO(
                            t.getId(),
                            t.getName(),
                            t.getDescription()
                    )).orElse(null);  // Si le thème n'existe pas, retourner null
                })
                .filter(dto -> dto != null)  // Filtrer les valeurs null
                .collect(Collectors.toList());
    }

}
