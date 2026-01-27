package com.aptechph.task_management_system.evtol.dto;

import com.aptechph.task_management_system.evtol.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponseDto {
    private Long id;
    private String evtolSerialNumber;
    private String destination;
    private String recipientName;
    private String recipientPhone;
    private String priority;
    private DeliveryStatus status;
    private LocalDateTime estimatedArrival;
    private LocalDateTime dispatchedAt;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private Double distanceKm;
    private String notes;
}
