package com.mutuelle.gestiondossiersmutuelle.processor;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ValidationProcessor implements ItemProcessor<Dossier, Dossier> {
    @Override

    public Dossier process (Dossier dossier) throws Exception {
        if(dossier.getNomAssure() == null || dossier.getNomAssure().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'assuré est manquant");
        }

        if (dossier.getNumeroAffiliation() == null || dossier.getNumeroAffiliation().isEmpty()) {
            throw new IllegalArgumentException("Le numéro d'affiliation est manquant");
        }

        if (dossier.getPrixConsultation() <= 0) {
            throw new IllegalArgumentException("Le prix de la consultation doit être positif");
        }

        if (dossier.getMontantTotalFrais() <= 0) {
            throw new IllegalArgumentException("Le montant total des frais doit être positif");
        }

        if (dossier.getBeneficiaire() == null) {
            throw new IllegalArgumentException("Les informations sur le bénéficiaire sont manquantes");
        }
        return dossier;
    }

}
