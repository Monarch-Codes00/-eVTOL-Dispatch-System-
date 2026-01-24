package com.aptechph.task_management_system.evtol.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "battery_audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatteryAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String evtolSerialNumber;

    @Column(nullable = false)
    private Integer batteryLevel;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvtolState evtolState;

    public BatteryAuditLog(String evtolSerialNumber, Integer batteryLevel, EvtolState evtolState) {
        this.evtolSerialNumber = evtolSerialNumber;
        this.batteryLevel = batteryLevel;
        this.evtolState = evtolState;
        this.timestamp = LocalDateTime.now();
    }
}
