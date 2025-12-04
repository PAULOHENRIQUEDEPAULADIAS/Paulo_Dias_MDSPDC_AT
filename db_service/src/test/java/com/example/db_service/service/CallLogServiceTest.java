package com.example.db_service.service;

import com.example.db_service.model.CallLog;
import com.example.db_service.repository.DbServiceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CallLogServiceTest {

    private final DbServiceRepository repository = Mockito.mock(DbServiceRepository.class);
    private final CallLogService service = new CallLogService(repository);

    @Test
    void saveLog_ShouldReturnSavedLog() {
        CallLog log = new CallLog("hello", "Hello");
        Mockito.when(repository.save(Mockito.any())).thenReturn(Mono.just(log));

        StepVerifier.create(service.saveLog("hello", "Hello"))
                .expectNext(log)
                .verifyComplete();
    }

    @Test
    void listLogs_ShouldReturnList() {
        CallLog log = new CallLog("hello", "Hello");

        Mockito.when(repository.findAll()).thenReturn(Flux.just(log));

        StepVerifier.create(service.listLogs())
                .expectNext(log)
                .verifyComplete();
    }
}
