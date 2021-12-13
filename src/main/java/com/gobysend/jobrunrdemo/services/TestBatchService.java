package com.gobysend.jobrunrdemo.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TestBatchService {

    private final Logger logger = LogManager.getLogger(TestBatchService.class);

    @PostConstruct
    public void runBatch() {
        BackgroundJob.startBatch(this::createChildJobs)
                .continueWith(this::chainedBatchJob);
    }

    public void createChildJobs() {
        logger.info("Start creating child jobs");
        for (int i = 0; i < 5; i++) {
            final String identifier = "Job #" + i;
            BackgroundJob.enqueue(() -> this.childJob(identifier));
        }
    }

    public void childJob(String identifier) {
        logger.info("This is the " + identifier);
    }

    public void chainedBatchJob() {
        logger.info("This job is expected to be run after all child jobs and the batch are finished. BUT actually it won't never be executed");
    }
}
