package com.berndgoetz.memcalc.controller;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
@Slf4j
public class HomeController {

    TelemetryClient telemetryClient;

    public HomeController(TelemetryClient telemetryClient) {
        this.telemetryClient = telemetryClient;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        telemetryClient.trackEvent("Sending a custom event...");
        telemetryClient.trackTrace("Sending a custom trace....");
        telemetryClient.trackMetric("custom metric", 1.0);
        telemetryClient.trackDependency("SQL", "Insert", new Duration(0, 0, 1, 1, 1), true);
        return "hello";
    }

    @GetMapping("/exception")
    @ResponseBody
    public String exception() {
        log.info("Throwing a runtime exception now");
        try {
            throw new RuntimeException("Explicit exception thrown");
        } catch (RuntimeException e) {
            log.error("Exception caught", e);
        }
        return "exception";
    }

    @GetMapping("/oom")
    @ResponseBody
    public String oom() throws InterruptedException {
        log.info("Going for a OOM");
        int iteratorValue = 20;
        for (int outerIterator = 1; outerIterator <= 20; outerIterator++) {
            log.info("Iteration {}, free mem {}", outerIterator, Runtime.getRuntime().freeMemory());
            int loop1 = 2;
            int[] memoryFillIntVar = new int[iteratorValue];
            do {
                memoryFillIntVar[loop1] = 0;
                loop1--;
            } while (loop1 > 0);
            iteratorValue = iteratorValue * 5;
            log.info("Required memory for next loop: {}", iteratorValue);
            Thread.sleep(1000);
        }
        return "oom done";
    }

}