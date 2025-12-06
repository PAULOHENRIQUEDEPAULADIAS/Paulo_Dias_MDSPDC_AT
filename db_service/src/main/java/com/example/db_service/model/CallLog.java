package com.example.db_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;


import java.time.LocalDateTime;

@Table("call_log")
public class CallLog {

    @Id
    private Long id;

    @Column("service_name")
    private String serviceName;

    @Column("response")
    private String response;

    @Column("timestamp")
    private LocalDateTime timestamp;

    public CallLog(String serviceName, String response) {
        this.serviceName = serviceName;
        this.response = response;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
