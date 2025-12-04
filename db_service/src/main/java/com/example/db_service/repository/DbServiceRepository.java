package com.example.db_service.repository;

import com.example.db_service.model.CallLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface DbServiceRepository extends ReactiveCrudRepository<CallLog, Long> {

    Flux<CallLog> findAll();
}
