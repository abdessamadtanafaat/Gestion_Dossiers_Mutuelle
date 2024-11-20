package com.mutuelle.gestiondossiersmutuelle.batch.listener;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Component
public class DossierSkipListener implements SkipListener<Dossier, Dossier> {
    @Override
    public void onSkipInRead(Throwable t) {
        System.err.println("Skipped during reading: " + t.getMessage());

    }

    @Override
    public void onSkipInWrite(Dossier dossier, Throwable t) {
        System.err.println("Skipped during writing: " + dossier + ", Error: " + t.getMessage());
    }

    @Override
    public void onSkipInProcess(Dossier dossier, Throwable t) {
        System.err.println("Skipped during processing: " + dossier + ", Error: " + t.getMessage());
    }

}
