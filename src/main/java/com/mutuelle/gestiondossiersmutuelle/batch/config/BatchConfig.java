package com.mutuelle.gestiondossiersmutuelle.batch.config;

import com.mutuelle.gestiondossiersmutuelle.batch.listener.DossierRetryListener;
import com.mutuelle.gestiondossiersmutuelle.batch.listener.DossierSkipListener;
import com.mutuelle.gestiondossiersmutuelle.batch.listener.LoggingRetryListener;
import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import com.mutuelle.gestiondossiersmutuelle.processor.TotalRemboursementProcessor;
import com.mutuelle.gestiondossiersmutuelle.processor.ValidationProcessor;
import com.mutuelle.gestiondossiersmutuelle.reader.DossierJsonReader;
import com.mutuelle.gestiondossiersmutuelle.writer.DossierDatabaseWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DossierJsonReader dossierJsonReader;
    private final DossierDatabaseWriter dossierDatabaseWriter;
    private final ValidationProcessor validationProcessor;
    private final TotalRemboursementProcessor totalRemboursementProcessor;
    private final DossierSkipPolicy dossierSkipPolicy;
    private final DossierRetryListener dossierRetryListener;
    private final DossierSkipListener dossierSkipListener;
    private final LoggingRetryListener loggingRetryListener;

    public BatchConfig(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            DossierJsonReader dossierJsonReader,
            DossierDatabaseWriter dossierDatabaseWriter,
            ValidationProcessor validationProcessor,
            TotalRemboursementProcessor totalRemboursementProcessor,
            DossierSkipPolicy dossierSkipPolicy,
            DossierRetryListener dossierRetryListener,
            DossierSkipListener dossierSkipListener,
            LoggingRetryListener loggingRetryListener
    ) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dossierJsonReader = dossierJsonReader;
        this.dossierDatabaseWriter = dossierDatabaseWriter;
        this.validationProcessor = validationProcessor;
        this.totalRemboursementProcessor = totalRemboursementProcessor;
        this.dossierSkipPolicy = dossierSkipPolicy;
        this.dossierRetryListener = dossierRetryListener;
        this.dossierSkipListener = dossierSkipListener;
        this.loggingRetryListener = loggingRetryListener;
    }

    @Bean
    public CompositeItemProcessor<Dossier, Dossier> compositeProcessor() {
        CompositeItemProcessor<Dossier, Dossier> compositeProcessor = new CompositeItemProcessor<>();
        compositeProcessor.setDelegates(Arrays.asList(validationProcessor, totalRemboursementProcessor));
        return compositeProcessor;
    }

    @Bean
    public Step processStep() {
        return new StepBuilder("processStep", jobRepository)
                .<Dossier, Dossier>chunk(10, transactionManager)
                .reader(dossierJsonReader)
                .processor(compositeProcessor()) // Use composite processor
                .writer(dossierDatabaseWriter)
                .faultTolerant()
                .skipPolicy(dossierSkipPolicy)
                .listener(dossierRetryListener)
                .listener(dossierSkipListener)
                .listener(loggingRetryListener)
                .build();
    }

    @Bean
    public Job processDossierJob() {
        return new JobBuilder("processDossierJob", jobRepository)
                .start(processStep())
                .build();
    }
}
