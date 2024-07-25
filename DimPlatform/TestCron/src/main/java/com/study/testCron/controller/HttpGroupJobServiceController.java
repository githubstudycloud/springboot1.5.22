package com.study.testCron.controller;

import com.study.testCron.service.HttpGroupJobService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/httpGroupJob")
public class HttpGroupJobServiceController {
    @Resource
    private HttpGroupJobService httpGroupJobService;
}
