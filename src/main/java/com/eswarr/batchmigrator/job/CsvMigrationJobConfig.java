package com.eswarr.batchmigrator.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CsvMigrationJobConfig {

    @Value("${job.input.file:classpath:data/expenses.csv}")
        private Resource inputFile;

            @Bean
                public FlatFileItemReader<String[]> csvItemReader() {
                        return new FlatFileItemReaderBuilder<String[]>()
                                        .name("expenseCsvReader")
                                                        .resource(inputFile)
                                                                        .delimited().names("amount", "description", "date", "category")
                                                                                        .fieldSetMapper(fs -> fs.getValues())
                                                                                                        .linesToSkip(1)
                                                                                                                        .build();
                                                                                                                            }
                                                                                                                            
                                                                                                                                @Bean
                                                                                                                                    public Job csvMigrationJob(JobRepository jobRepository, Step csvMigrationStep) {
                                                                                                                                            return new JobBuilder("csvMigrationJob", jobRepository)
                                                                                                                                                            .start(csvMigrationStep)
                                                                                                                                                                            .build();
                                                                                                                                                                                }
                                                                                                                                                                                }
