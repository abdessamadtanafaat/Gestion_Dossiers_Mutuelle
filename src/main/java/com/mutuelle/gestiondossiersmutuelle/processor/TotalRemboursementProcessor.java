package com.mutuelle.gestiondossiersmutuelle.processor;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TotalRemboursementProcessor implements ItemProcessor<Dossier, Dossier> {

    // Met à jour le montant total des frais avec
    // le remboursement total (consultation + traitements).
    @Override
    public Dossier process(Dossier dossier) throws Exception {
        double remboursementTotal = dossier.getMontantTotalFrais();  // Récupérer le montant des frais après les processeurs précédents
        System.out.println("Total des frais avant total remboursement : " + remboursementTotal);

        // Calcul du total remboursement (si nécessaire, ici, il est déjà inclus dans les autres processeurs)
        // Si vous avez besoin de calculer à nouveau le remboursement total, vous pouvez ajouter cette logique ici.

        System.out.println("Montant total des frais après total remboursement : " + remboursementTotal);

        // Mise à jour du montant total des frais
        dossier.setMontantTotalFrais(remboursementTotal);

        return dossier;
    }
}
