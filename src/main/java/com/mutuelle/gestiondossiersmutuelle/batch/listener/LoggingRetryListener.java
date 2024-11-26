package com.mutuelle.gestiondossiersmutuelle.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

@Component
public class LoggingRetryListener implements RetryListener {

    private static final Logger logger = LoggerFactory.getLogger(LoggingRetryListener.class);

    //se concentre sur l'enregistrement des tentatives de reprise.
    //enregistre un message indiquant une tentative de reprise échouée,
    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        logger.info("Retry attempt #{} for operation failed. Error: {}", context.getRetryCount(), throwable.getMessage());
    }
}
