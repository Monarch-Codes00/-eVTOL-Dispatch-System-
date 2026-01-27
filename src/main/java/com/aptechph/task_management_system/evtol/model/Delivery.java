package com.aptechph.task_management_system.evtol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evtol_id", nullable = false)
    private Evtol evtol;

    @NotBlank(message = "Destination is required")
    @Column(nullable = false)
    private String destination;

    @NotBlank(message = "Recipient name is required")
    @Column(nullable = false)
    private String recipientName;

    @NotBlank(message = "Recipient phone is required")
    @Column(nullable = false)
    private String recipientPhone;

    @NotBlank(message = "Priority is required")
    @Column(nullable = false)
    private String priority; // LOW, NORMAL, HIGH, CRITICAL

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    private LocalDateTime estimatedArrival;
    private LocalDateTime dispatchedAt;
    private LocalDateTime completedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Double distanceKm;
    
    @Column(length = 1000)
    private String notes;
}
