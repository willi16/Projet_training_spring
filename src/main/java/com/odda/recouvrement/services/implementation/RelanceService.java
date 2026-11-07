package com.odda.recouvrement.services.implementation;

import com.odda.recouvrement.entities.Echeancier;
import com.odda.recouvrement.entities.Notification;
import com.odda.recouvrement.enums.CanalNotification;
import com.odda.recouvrement.enums.StatutEcheance;
import com.odda.recouvrement.enums.StatutNotification;
import com.odda.recouvrement.repositories.EcheancierRepository;
import com.odda.recouvrement.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RelanceService {

    private final EcheancierRepository echeancierRepository;
    private final NotificationRepository notificationRepository;

    // S'exécute tous les jours à 08h00 du matin
    // Format Cron : "seconde minute heure jour_du_mois mois jour_de_la_semaine"
    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void verifierEtNotifierRetards() {
        LocalDate aujourdHui = LocalDate.now();
        log.info("Début du job de relance automatique des impayés...");

        // 1. Trouver toutes les échéances en retard (date passée) et non payées
        List<Echeancier> enRetard = echeancierRepository.findAll().stream()
                .filter(e -> e.getDateEcheance().isBefore(aujourdHui))
                .filter(e -> e.getStatut() == StatutEcheance.A_VENIR || e.getStatut() == StatutEcheance.PARTIELLEMENT_PAYE)
                .toList();

        if (enRetard.isEmpty()) {
            log.info("Aucun retard détecté aujourd'hui.");
            return;
        }

        // 2. Grouper les retards par Parent pour éviter d'envoyer 10 SMS si un parent a 10 enfants en retard
        Map<Long, List<Echeancier>> retardsParParent = enRetard.stream()
                .filter(e -> e.getApprenant().getParent() != null)
                .collect(Collectors.groupingBy(e -> e.getApprenant().getParent().getId()));

        // 3. Créer une notification unique par parent concerné
        for (Map.Entry<Long, List<Echeancier>> entry : retardsParParent.entrySet()) {
            Long parentId = entry.getKey();
            List<Echeancier> retards = entry.getValue();
            
            // Calcul du total en retard pour ce parent
            BigDecimal totalRetard = retards.stream()
                    .map(e -> e.getMontantPrevu().subtract(e.getMontantPaye()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            String nomParent = retards.get(0).getApprenant().getParent().getNom();
            String matricules = retards.stream()
                    .map(e -> e.getApprenant().getMatricule())
                    .distinct()
                    .collect(Collectors.joining(", "));

            // 4. Anti-Spam : Vérifier si on a déjà envoyé une notification aujourd'hui à ce parent
            boolean dejaNotifieAujourdhui = notificationRepository.existsByParent_TrackingIdAndStatutAndDateEnvoiAfter(
                    retards.get(0).getApprenant().getParent().getTrackingId(),
                    StatutNotification.EN_ATTENTE,
                    aujourdHui.atStartOfDay()
            );

            if (!dejaNotifieAujourdhui) {
                String message = String.format(
                        "Rappel : Vous avez un solde en retard de %s FCFA pour l(es) apprenant(s) : %s. Merci de régulariser.",
                        totalRetard, matricules
                );

                Notification notification = Notification.builder()
                        .parent(retards.get(0).getApprenant().getParent())
                        .apprenant(retards.get(0).getApprenant()) // On met le premier apprenant comme référence
                        .canal(CanalNotification.SMS) // Ou EMAIL selon votre logique
                        .contenu(message)
                        .statut(StatutNotification.EN_ATTENTE)
                        .dateEnvoi(LocalDateTime.now())
                        .build();

                notificationRepository.save(notification);
                log.info("Notification de relance créée pour le parent ID: {} (Total: {})", parentId, totalRetard);
            } else {
                log.info("Parent ID {} déjà notifié aujourd'hui. Notification ignorée.", parentId);
            }
        }
        
        log.info("Fin du job de relance automatique.");
    }
}