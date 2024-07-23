//package com.study.testCron.service;
//package com.example.cronservice.service;
//
//import com.example.cronservice.entity.RefreshConfig;
//import com.example.cronservice.repository.RefreshConfigRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//@Service
//public class RefreshService {
//
//    @Autowired
//    private RefreshConfigRepository refreshConfigRepository;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public void refreshDataType(String dataType) {
//        List<RefreshConfig> configs = refreshConfigRepository.findByDataType(dataType);
//        for (RefreshConfig config : configs) {
//            refreshData(config);
//        }
//    }
//
//    private void refreshData(RefreshConfig config) {
//        // Call refresh API
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(config.getPostParams(), headers);
//        restTemplate.postForObject(config.getRefreshUrl(), entity, String.class);
//
//        // Check status until complete or timeout
//        LocalDateTime startTime = LocalDateTime.now();
//        while (true) {
//            String status = restTemplate.getForObject(config.getStatusUrl(), String.class);
//            if ("COMPLETED".equals(status)) {
//                // Refresh completed successfully
//                break;
//            }
//            if (ChronoUnit.SECONDS.between(startTime, LocalDateTime.now()) > config.getMaxRefreshTime()) {
//                // Refresh failed due to timeout
//                throw new RuntimeException("Refresh timed out for data type: " + config.getDataType());
//            }
//            try {
//                Thread.sleep(5000); // Wait for 5 seconds before checking again
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//    }
//}
