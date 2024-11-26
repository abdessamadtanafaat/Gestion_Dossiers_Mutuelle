package com.mutuelle.gestiondossiersmutuelle.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;
@Component
public class DossierSkipPolicy implements SkipPolicy {

    private static final Logger logger = LoggerFactory.getLogger(DossierSkipPolicy.class);

    // Si l'exception est une IllegalArgumentException et que le compteur de sauts
    // (skipCount) est inférieur à 5, le dossier est ignoré
    // sinon l'exception est considérée comme non gérable et l'exécution échoue.
    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        if (t instanceof IllegalArgumentException && skipCount < 5) {
            logger.warn("Skipping dossier due to error: {}", t.getMessage());
            return true;
        }
        logger.error("Unhandled exception: {}", t.getMessage());
        return false;
    }
}

