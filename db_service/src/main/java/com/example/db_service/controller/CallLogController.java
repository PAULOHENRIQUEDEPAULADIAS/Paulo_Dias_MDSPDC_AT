package com.example.db_service.controller;

import com.example.db_service.model.CallLog;
import com.example.db_service.service.CallLogService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/logs")
public class CallLogController {

    private final CallLogService logService;

    public CallLogController(CallLogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public Mono<CallLog> saveLog(
            @RequestParam String service,
            @RequestParam String response) {

        return logService.saveLog(service, response);
    }

    @GetMapping
    public Flux<CallLog> listLogs() {
        return logService.listLogs();
    }
}
