package com.mutuelle.gestiondossiersmutuelle.processor;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ValidationProcessor implements ItemProcessor<Dossier, Dossier> {

    private static final Logger logger = LoggerFactory.getLogger(ValidationProcessor.class);

    @Override
    public Dossier process(Dossier dossier) throws Exception {
        logger.debug("Validating dossier: {}", dossier);

        if (dossier.getNomAssure() == null || dossier.getNomAssure().isEmpty()) {
            throw new IllegalArgumentException("Nom de l'assuré est manquant.");
        }
        if (dossier.getNumeroAffiliation() == null || dossier.getNumeroAffiliation().isEmpty()) {
            throw new IllegalArgumentException("Numéro d'affiliation est manquant.");
        }
        if (dossier.getMontantTotalFrais() <= 0 || dossier.getPrixConsultation() <= 0) {
            throw new IllegalArgumentException("Les frais et le prix de la consultation doivent être positifs.");
        }
        logger.info("Validation passed for dossier: {}", dossier);
        return dossier;
    }
}
