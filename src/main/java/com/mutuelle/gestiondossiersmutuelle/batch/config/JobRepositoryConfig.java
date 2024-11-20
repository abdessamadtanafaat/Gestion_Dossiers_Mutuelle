//package com.mutuelle.gestiondossiersmutuelle.batch.config;
//
//
//
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableBatchProcessing
//// repo personalise
//public class JobRepositoryConfig {
//
//    @Bean
//    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
//        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setTransactionManager(transactionManager);
//        factory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
//        factory.setTablePrefix("BATCH_"); // Préfixe pour éviter les conflits
//        factory.afterPropertiesSet();
//        return factory.getObject();
//    }
//}
