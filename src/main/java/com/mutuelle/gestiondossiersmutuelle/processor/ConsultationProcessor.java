package com.mutuelle.gestiondossiersmutuelle.processor;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ConsultationProcessor implements ItemProcessor<Dossier, Dossier> {

    private static final double CONSULTATION_REIMBURSEMENT_PERCENTAGE = 0.80;  // 80% de remboursement

    // Calcule le remboursement de la consultation
    // et met à jour le montant total des frais du dossier.
    @Override
    public Dossier process(Dossier dossier) throws Exception {
        // Affichage des valeurs initiales
        System.out.println("Initial montantTotalFrais : " + dossier.getMontantTotalFrais());
        System.out.println("Prix consultation : " + dossier.getPrixConsultation());

        // Calcul du remboursement de la consultation
        double remboursementConsultation = dossier.getPrixConsultation() * CONSULTATION_REIMBURSEMENT_PERCENTAGE;
        System.out.println("Remboursement consultation (80%): " + remboursementConsultation);

        // Ajouter le remboursement de la consultation au montant total des frais
        dossier.setMontantTotalFrais(dossier.getMontantTotalFrais() + remboursementConsultation);  // On additionne ici

        // Affichage après modification
        System.out.println("Montant total des frais après ajout remboursement consultation : " + dossier.getMontantTotalFrais());

        return dossier;
    }
}
