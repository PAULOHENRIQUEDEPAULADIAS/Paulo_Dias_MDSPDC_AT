package com.example.db_service.service;

import com.example.db_service.model.CallLog;
import com.example.db_service.repository.DbServiceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CallLogService {

    private final DbServiceRepository repository;

    public CallLogService(DbServiceRepository repository) {
        this.repository = repository;
    }

    public Mono<CallLog> saveLog(String service, String response) {
        return repository.save(new CallLog(service, response));
    }

    public Flux<CallLog> listLogs() {
        return repository.findAll();
    }
}
