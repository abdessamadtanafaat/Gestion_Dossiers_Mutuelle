package com.mutuelle.gestiondossiersmutuelle.processor;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import com.mutuelle.gestiondossiersmutuelle.model.Traitement;
import com.mutuelle.gestiondossiersmutuelle.model.MedicamentReferentiel;
import com.mutuelle.gestiondossiersmutuelle.repository.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TraitementRemboursementProcessor implements ItemProcessor<Dossier, Dossier> {

    @Autowired
    private MedicamentRepository medicamentRepository;  // Repository pour accéder aux médicaments référencés

    @Override
    public Dossier process(Dossier dossier) throws Exception {
        double remboursementTotalTraitements = 0.0;
        double montantInitialFrais = dossier.getMontantTotalFrais();  // Sauvegarde du montant initial


        System.out.println("Montant initial des frais avant traitements : " + dossier.getMontantTotalFrais());

        // Calculer le remboursement de chaque traitement
        for (Traitement traitement : dossier.getTraitements()) {
            if (traitement.isExiste()) {
                // Rechercher le médicament dans le référentiel en utilisant le code barre du traitement
                MedicamentReferentiel medicament = medicamentRepository.findByCodeBarre(traitement.getCodeBarre());
                if (medicament != null) {
                    // Utiliser le pourcentage de remboursement spécifique du médicament référentiel
                    double remboursementTraitement = medicament.getPrixReference() * medicament.getPourcentageRemboursement();
                    System.out.println("Remboursement pour " + traitement.getNomMedicament() + " : " + remboursementTraitement);
                    remboursementTotalTraitements += remboursementTraitement;
                } else {
                    System.out.println("Le médicament référencé avec le code barre " + traitement.getCodeBarre() + " n'existe pas.");
                }
            } else {
                System.out.println("Le traitement " + traitement.getNomMedicament() + " n'existe pas dans le référentiel.");
            }
        }

        // Ajouter le remboursement total des traitements au montant total des frais
        dossier.setMontantTotalFrais(dossier.getMontantTotalFrais() + remboursementTotalTraitements);  // On additionne ici
        System.out.println("Montant total des frais après ajout des remboursements des traitements : " + dossier.getMontantTotalFrais());

        // Calculer le montant total remboursé
        double montantTotalRembourse = dossier.getMontantTotalFrais() - montantInitialFrais;  // Différence entre final et initial
        dossier.setMontantTotalRembourse(montantTotalRembourse);  // Stockage du montant total remboursé
        System.out.println("Montant total remboursé : " + montantTotalRembourse);

        return dossier;
    }
}
