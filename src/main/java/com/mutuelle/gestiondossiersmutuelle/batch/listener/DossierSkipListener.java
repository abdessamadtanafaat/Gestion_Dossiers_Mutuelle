package com.mutuelle.gestiondossiersmutuelle.batch.listener;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Component
public class DossierSkipListener implements SkipListener<Dossier, Dossier> {

    private static final Logger logger = LoggerFactory.getLogger(DossierSkipListener.class);

    // écouteur qui est invoqué lorsqu'un dossier est ignoré (skip).
    @Override
    public void onSkipInRead(Throwable t) {
        logger.warn("Skipped during reading. Error: {}", t.getMessage());
    }

    @Override
    public void onSkipInWrite(Dossier dossier, Throwable t) {
        logger.warn("Skipped during writing: {}. Error: {}", dossier, t.getMessage());
    }

    @Override
    public void onSkipInProcess(Dossier dossier, Throwable t) {
        logger.warn("Skipped during processing: {}. Error: {}", dossier, t.getMessage());
    }
}
