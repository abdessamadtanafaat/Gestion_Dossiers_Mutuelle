package com.mutuelle.gestiondossiersmutuelle.processor;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import com.mutuelle.gestiondossiersmutuelle.model.MedicamentReferentiel;
import com.mutuelle.gestiondossiersmutuelle.model.Traitement;
import com.mutuelle.gestiondossiersmutuelle.repository.MedicamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ValidationProcessor implements ItemProcessor<Dossier, Dossier> {

    private static final Logger logger = LoggerFactory.getLogger(ValidationProcessor.class);

    @Autowired
    private MedicamentRepository medicamentRepository;  // Référence au repository des médicaments

    @Override
    public Dossier process(Dossier dossier) throws Exception {
        logger.debug("Validating dossier: {}", dossier);

        // Validate that all necessary fields are filled
        if (dossier.getNomAssure() == null || dossier.getNomAssure().isEmpty()) {
            throw new IllegalArgumentException("Nom de l'assuré est manquant.");
        }
        if (dossier.getNumeroAffiliation() == null || dossier.getNumeroAffiliation().isEmpty()) {
            throw new IllegalArgumentException("Numéro d'affiliation est manquant.");
        }
        if (dossier.getPrixConsultation() <= 0) {
            throw new IllegalArgumentException("Le prix de la consultation doit être positif.");
        }

        if (dossier.getMontantTotalFrais() <= 0) {
            throw new IllegalArgumentException("Le montant total des frais doit être positif.");
        }

        // Check that the list of treatments is present and not empty
        if (dossier.getTraitements() == null || dossier.getTraitements().isEmpty()) {
            throw new IllegalArgumentException("La liste des traitements doit être présente et non vide.");
        }

        // Validate that all treatments are referenced in the database
        for (Traitement traitement : dossier.getTraitements()) {
            MedicamentReferentiel medicament = medicamentRepository.findByCodeBarre(traitement.getCodeBarre());
            if (medicament == null) {
                dossier.setRejet(true);  // Mark the dossier as rejected if a treatment is not referenced
                logger.warn("Traitement non référencé trouvé dans le dossier: {}", traitement);
                throw new IllegalArgumentException("Un des traitements n'est pas référencé.");
            }
        }

        if (dossier.getNombrePiecesJointes() < 0) {
            throw new IllegalArgumentException("Le nombre de pièces jointes ne peut pas être négatif.");
        }

        logger.info("Validation passed for dossier: {}", dossier);
        return dossier;
    }
}
