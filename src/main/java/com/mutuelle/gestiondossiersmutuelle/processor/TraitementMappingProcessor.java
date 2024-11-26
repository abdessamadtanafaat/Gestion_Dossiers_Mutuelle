package com.mutuelle.gestiondossiersmutuelle.processor;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import com.mutuelle.gestiondossiersmutuelle.model.Traitement;
import com.mutuelle.gestiondossiersmutuelle.model.MedicamentReferentiel;
import com.mutuelle.gestiondossiersmutuelle.repository.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TraitementMappingProcessor implements ItemProcessor<Dossier, Dossier> {

    @Autowired
    private MedicamentRepository medicamentRepository;

    // Calcule le remboursement de chaque médicament référencé et
    // met à jour le montant total des frais du dossier, ainsi que le montant total remboursé.
    @Override
    public Dossier process(Dossier dossier) throws Exception {
        // Parcourir tous les traitements pour les mapper aux médicaments référencés
        for (Traitement traitement : dossier.getTraitements()) {
            // Si le médicament existe dans le référentiel
            if (traitement.isExiste()) {
                MedicamentReferentiel medicament = medicamentRepository.findByCodeBarre(traitement.getCodeBarre());
                if (medicament != null) {
                    // Mettre à jour les informations du traitement avec les données du médicament
                    traitement.setNomMedicament(medicament.getNom());
                    traitement.setTypeMedicament(medicament.getDci1());
                    traitement.setPrixMedicament(medicament.getPrixReference());
                    traitement.setRemboursementPourcentage(medicament.getPourcentageRemboursement());
                } else {
                    // Si le médicament n'est pas référencé, on le marque comme non disponible
                    throw new IllegalArgumentException("Le médicament référencé n'existe pas : " + traitement.getCodeBarre());
                }
            }
        }
        return dossier;
    }
}
