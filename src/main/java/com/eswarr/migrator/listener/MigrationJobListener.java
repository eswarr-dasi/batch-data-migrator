package com.eswarr.migrator.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class MigrationJobListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(MigrationJobListener.class);
        private Instant startTime;

            @Override
                public void beforeJob(JobExecution jobExecution) {
                        startTime = Instant.now();
                                log.info("Starting migration job: {}", jobExecution.getJobInstance().getJobName());
                                        log.info("Job parameters: {}", jobExecution.getJobParameters());
                                            }

                                                @Override
                                                    public void afterJob(JobExecution jobExecution) {
                                                            Duration duration = Duration.between(startTime, Instant.now());
                                                                    BatchStatus status = jobExecution.getStatus();

                                                                            if (status == BatchStatus.COMPLETED) {
                                                                                        long readCount = jobExecution.getStepExecutions().stream()
                                                                                                        .mapToLong(se -> se.getReadCount()).sum();
                                                                                                                    long writeCount = jobExecution.getStepExecutions().stream()
                                                                                                                                    .mapToLong(se -> se.getWriteCount()).sum();
                                                                                                                                                long skipCount = jobExecution.getStepExecutions().stream()
                                                                                                                                                                .mapToLong(se -> se.getSkipCount()).sum();
                                                                                                                                                                
                                                                                                                                                                            log.info("Migration completed in {}s. Read={}, Written={}, Skipped={}",
                                                                                                                                                                                            duration.getSeconds(), readCount, writeCount, skipCount);
                                                                                                                                                                                                    } else {
                                                                                                                                                                                                                log.error("Migration FAILED with status: {}. Duration: {}s",
                                                                                                                                                                                                                                status, duration.getSeconds());
                                                                                                                                                                                                                                            jobExecution.getAllFailureExceptions()
                                                                                                                                                                                                                                                            .forEach(ex -> log.error("Failure: ", ex));
                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                        }
