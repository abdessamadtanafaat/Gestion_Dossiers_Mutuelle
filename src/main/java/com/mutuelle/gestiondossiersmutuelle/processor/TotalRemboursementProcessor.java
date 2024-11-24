package com.mutuelle.gestiondossiersmutuelle.processor;


import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TotalRemboursementProcessor implements ItemProcessor<Dossier, Dossier> {

    private static final double REIMBURSEMENT_PERCENTAGE = 0.8;  // 80% reimbursement on consultation

    @Override
    public Dossier process(Dossier dossier) throws Exception {
        // Apply reimbursement percentage to the consultation price
        double reimbursementAmount = dossier.getPrixConsultation() * REIMBURSEMENT_PERCENTAGE;

        // Set the total reimbursement in the dossier (you can add a new field in the Dossier class for this)
        dossier.setMontantTotalFrais(reimbursementAmount);

        // Log or return the updated dossier with reimbursement applied
        return dossier;
    }
}
