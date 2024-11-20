package com.mutuelle.gestiondossiersmutuelle.batch.listener;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

@Configuration
public class LoggingRetryListener implements RetryListener {

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        System.err.println("Retry #" + context.getRetryCount() + " failed for: " + context.getAttribute("context.name"));
        System.err.println("Error: " + throwable.getMessage());
    }

}
