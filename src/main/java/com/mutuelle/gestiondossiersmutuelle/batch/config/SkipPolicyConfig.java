package com.mutuelle.gestiondossiersmutuelle.batch.config;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

@Component
public class SkipPolicyConfig implements SkipPolicy {

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        if (skipCount >= 5) {
            return false;
        }
        // Ignorer uniquement les exceptions spécifiques
        return t instanceof IllegalArgumentException;     }
}
