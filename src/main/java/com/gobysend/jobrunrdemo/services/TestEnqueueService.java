package com.gobysend.jobrunrdemo.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TestEnqueueService {

    private final Logger logger = LogManager.getLogger(TestEnqueueService.class);

    @PostConstruct
    public void runJobWithChain() {
        BackgroundJob.enqueue(this::aJob).continueWith(this::chainedJob);
    }

    public void aJob() {
        logger.info("This is a testing job");
    }

    public void chainedJob() {
        logger.info("This job will be executed after aJob is done.");
    }
}
