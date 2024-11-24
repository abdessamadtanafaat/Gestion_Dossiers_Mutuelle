package com.mutuelle.gestiondossiersmutuelle.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

@Component
public class DossierSkipPolicy implements SkipPolicy {

    private static final Logger logger = LoggerFactory.getLogger(DossierSkipPolicy.class);

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        if (t instanceof IllegalArgumentException && skipCount < 5) {
            logger.warn("Skipping dossier due to error: {}. Skip count: {}", t.getMessage(), skipCount);
            return true;
        }
        logger.error("Exceeded skip limit or unhandled exception: {}", t.getMessage());
        return false;
    }
}
