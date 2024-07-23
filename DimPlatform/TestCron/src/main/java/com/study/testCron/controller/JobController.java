package com.study.testCron.controller;


import com.study.testCron.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<?> createJob(@RequestParam String dataType, @RequestParam String cronExpression) {
        jobService.createJob(dataType, cronExpression);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> listJobs() {
        return ResponseEntity.ok(jobService.listJobs());
    }

    @GetMapping("/{jobName}/status")
    public ResponseEntity<?> getJobStatus(@PathVariable String jobName) {
        return ResponseEntity.ok(jobService.getJobStatus(jobName));
    }

    @PostMapping("/{jobName}/trigger")
    public ResponseEntity<?> triggerJob(@PathVariable String jobName) {
        jobService.triggerJob(jobName);
        return ResponseEntity.ok().build();
    }
}