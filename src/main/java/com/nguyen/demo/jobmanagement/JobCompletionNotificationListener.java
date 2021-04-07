package com.nguyen.demo.jobmanagement;

import com.nguyen.demo.core.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final PersonRepository personRepository;

    @Autowired
    public JobCompletionNotificationListener(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            personRepository.findAll()
                    .forEach(person -> log.info("Found <" + person + "> in the database."));
        }
    }
}
