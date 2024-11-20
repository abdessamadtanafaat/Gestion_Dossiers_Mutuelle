package com.mutuelle.gestiondossiersmutuelle.batch.config;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import com.mutuelle.gestiondossiersmutuelle.processor.ValidationProcessor;
import com.mutuelle.gestiondossiersmutuelle.reader.DossierJsonReader;
import com.mutuelle.gestiondossiersmutuelle.writer.DossierConsoleWriter;
import com.mutuelle.gestiondossiersmutuelle.writer.DossierDatabaseWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration

@EnableBatchProcessing
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DossierJsonReader dossierJsonReader;
    private final DossierConsoleWriter dossierConsoleWriter;
    private final DossierDatabaseWriter dossierDatabaseWriter;
    private final ValidationProcessor validationProcessor;

    public BatchConfig (
                        JobRepository jobRepository,
                       PlatformTransactionManager transactionManager,
                       DossierJsonReader dossierJsonReader,
                       DossierConsoleWriter dossierConsoleWriter,
                        DossierDatabaseWriter dossierDatabaseWrite,
                        ValidationProcessor validationProcessor
    ){
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dossierJsonReader = dossierJsonReader;
        this.dossierConsoleWriter = dossierConsoleWriter;
        this.dossierDatabaseWriter = dossierDatabaseWrite;
        this.validationProcessor = validationProcessor;

    }

    @Bean
    public Job processDossierJob() {
        return new JobBuilder("processDossierJob", jobRepository)
                .start(step1())
                .build();
    }
    @Bean
    public Step step1() {
        return new StepBuilder("step1",jobRepository)
                .<Dossier, Dossier>chunk(10,transactionManager)
                .reader(dossierJsonReader)
                .processor(validationProcessor)
                .writer(dossierConsoleWriter)
                .writer(dossierDatabaseWriter)
                .build();
    }
}
