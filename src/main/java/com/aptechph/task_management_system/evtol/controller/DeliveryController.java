package com.aptechph.task_management_system.evtol.controller;

import com.aptechph.task_management_system.evtol.dto.DeliveryResponseDto;
import com.aptechph.task_management_system.evtol.model.DeliveryStatus;
import com.aptechph.task_management_system.evtol.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<DeliveryResponseDto>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDto> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

    @PostMapping
    public ResponseEntity<DeliveryResponseDto> createDelivery(@RequestBody Map<String, Object> payload) {
        String serialNumber = (String) payload.get("evtolSerialNumber");
        String destination = (String) payload.get("destination");
        String recipientName = (String) payload.get("recipientName");
        String recipientPhone = (String) payload.get("recipientPhone");
        String priority = (String) payload.get("priority");
        String notes = (String) payload.get("notes");

        return ResponseEntity.ok(deliveryService.createDelivery(
                serialNumber, destination, recipientName, recipientPhone, priority, notes));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveryResponseDto> updateDeliveryStatus(
            @PathVariable Long id, @RequestBody Map<String, String> payload) {
        DeliveryStatus status = DeliveryStatus.valueOf(payload.get("status"));
        return ResponseEntity.ok(deliveryService.updateDeliveryStatus(id, status));
    }

    @GetMapping("/evtol/{serialNumber}")
    public ResponseEntity<List<DeliveryResponseDto>> getDeliveriesByEvtol(@PathVariable String serialNumber) {
        return ResponseEntity.ok(deliveryService.getDeliveriesByEvtol(serialNumber));
    }
}
