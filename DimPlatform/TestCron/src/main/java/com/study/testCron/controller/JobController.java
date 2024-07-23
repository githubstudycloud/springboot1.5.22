package com.study.testCron.controller;


import com.study.testCron.job.SampleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @PostMapping
    public void createJob(@RequestParam String jobName, @RequestParam String cronExpression) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SampleJob.class)
                .withIdentity(jobName)
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "Trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    @DeleteMapping("/{jobName}")
    public void deleteJob(@PathVariable String jobName) throws SchedulerException {
        scheduler.deleteJob(new JobKey(jobName));
    }

//
//    @PostMapping("/refresh")
//    public void createRefreshJob(@RequestParam String jobName,
//                                 @RequestParam String cronExpression,
//                                 @RequestParam String dataType) throws SchedulerException {
//        JobDetail jobDetail = JobBuilder.newJob(RefreshJob.class)
//                .withIdentity(jobName)
//                .usingJobData("dataType", dataType)
//                .build();
//
//        CronTrigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity(jobName + "Trigger")
//                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
//                .build();
//
//        scheduler.scheduleJob(jobDetail, trigger);
//    }

//    @GetMapping
//    public ResponseEntity<?> listJobs() {
//        return ResponseEntity.ok(jobService.listJobs());
//    }
//
//    @GetMapping("/{jobName}/status")
//    public ResponseEntity<?> getJobStatus(@PathVariable String jobName) {
//        return ResponseEntity.ok(jobService.getJobStatus(jobName));
//    }

    @PostMapping("/{jobName}/trigger")
    public ResponseEntity<?> triggerJob(@PathVariable String jobName) {
//        jobService.triggerJob(jobName);
        return ResponseEntity.ok().build();
    }
}