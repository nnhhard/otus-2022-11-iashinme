package ru.iashinme.homework14.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final JobLauncher jobLauncher;
    private final Job importCatalogueJob;

    @ShellMethod(value = "Start migration", key = {"sm", "start_migration"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public void start() throws Exception {
        JobExecution execution = jobLauncher.run(importCatalogueJob, new JobParametersBuilder().toJobParameters());
        System.out.println(execution);
    }
}