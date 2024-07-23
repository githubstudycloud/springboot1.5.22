//package com.study.testCron.repository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.Date;
//
//@Repository
//public class JobExecutionRepository {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public boolean hasBeenExecuted(String jobId) {
//        Integer count = jdbcTemplate.queryForObject(
//                "SELECT COUNT(*) FROM job_executions WHERE job_id = ?",
//                Integer.class,
//                jobId
//        );
//        return count != null && count > 0;
//    }
//
//    public void markAsExecuted(String jobId) {
//        jdbcTemplate.update(
//                "INSERT INTO job_executions (job_id, execution_time) VALUES (?, ?)",
//                jobId,
//                new Date()
//        );
//    }
//}