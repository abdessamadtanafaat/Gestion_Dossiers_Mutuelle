package com.mutuelle.gestiondossiersmutuelle.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

@Component
public class DossierRetryListener implements RetryListener {

    // La classe DossierRetryListener écoute les événements de tentative de reprise après
    // une erreur.
    private static final Logger logger = LoggerFactory.getLogger(DossierRetryListener.class);

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        logger.error("Retry attempt #{} failed. Error: {}", context.getRetryCount(), throwable.getMessage());
    }
}
