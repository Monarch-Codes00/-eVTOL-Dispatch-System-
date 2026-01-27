package com.aptechph.task_management_system.evtol.service;

import com.aptechph.task_management_system.evtol.dto.DeliveryResponseDto;
import com.aptechph.task_management_system.evtol.model.Delivery;
import com.aptechph.task_management_system.evtol.model.DeliveryStatus;
import com.aptechph.task_management_system.evtol.model.Evtol;
import com.aptechph.task_management_system.evtol.model.EvtolState;
import com.aptechph.task_management_system.evtol.repository.DeliveryRepository;
import com.aptechph.task_management_system.evtol.repository.EvtolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final EvtolRepository evtolRepository;

    public List<DeliveryResponseDto> getAllDeliveries() {
        return deliveryRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public DeliveryResponseDto getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
        return mapToResponseDto(delivery);
    }

    @Transactional
    public DeliveryResponseDto createDelivery(String serialNumber, String destination, String recipientName, String recipientPhone, String priority, String notes) {
        Evtol evtol = evtolRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new RuntimeException("eVTOL not found with serial number: " + serialNumber));

        Delivery delivery = new Delivery();
        delivery.setEvtol(evtol);
        delivery.setDestination(destination);
        delivery.setRecipientName(recipientName);
        delivery.setRecipientPhone(recipientPhone);
        delivery.setPriority(priority);
        delivery.setStatus(DeliveryStatus.PENDING);
        delivery.setCreatedAt(LocalDateTime.now());
        delivery.setNotes(notes);
        // Default distance for demo
        delivery.setDistanceKm(Math.random() * 20 + 5); 
        delivery.setEstimatedArrival(LocalDateTime.now().plusMinutes(20 + (long)(Math.random() * 30)));

        // Update evtol state
        evtol.setState(EvtolState.DELIVERING);
        evtolRepository.save(evtol);

        Delivery savedDelivery = deliveryRepository.save(delivery);
        return mapToResponseDto(savedDelivery);
    }

    @Transactional
    public DeliveryResponseDto updateDeliveryStatus(Long id, DeliveryStatus status) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));

        delivery.setStatus(status);
        
        if (status == DeliveryStatus.IN_TRANSIT) {
            delivery.setDispatchedAt(LocalDateTime.now());
            delivery.getEvtol().setState(EvtolState.DELIVERING);
        } else if (status == DeliveryStatus.ARRIVED) {
            delivery.getEvtol().setState(EvtolState.DELIVERED);
        } else if (status == DeliveryStatus.COMPLETED) {
            delivery.setCompletedAt(LocalDateTime.now());
            delivery.getEvtol().setState(EvtolState.IDLE);
            // Optionally clear medications from drone upon completion
            delivery.getEvtol().getMedications().clear();
            delivery.getEvtol().setCurrentLoad(0);
        } else if (status == DeliveryStatus.CANCELLED) {
            delivery.getEvtol().setState(EvtolState.IDLE);
        }

        evtolRepository.save(delivery.getEvtol());
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        return mapToResponseDto(updatedDelivery);
    }

    public List<DeliveryResponseDto> getDeliveriesByEvtol(String serialNumber) {
        return deliveryRepository.findByEvtolSerialNumber(serialNumber).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private DeliveryResponseDto mapToResponseDto(Delivery delivery) {
        DeliveryResponseDto dto = new DeliveryResponseDto();
        dto.setId(delivery.getId());
        dto.setEvtolSerialNumber(delivery.getEvtol().getSerialNumber());
        dto.setDestination(delivery.getDestination());
        dto.setRecipientName(delivery.getRecipientName());
        dto.setRecipientPhone(delivery.getRecipientPhone());
        dto.setPriority(delivery.getPriority());
        dto.setStatus(delivery.getStatus());
        dto.setEstimatedArrival(delivery.getEstimatedArrival());
        dto.setDispatchedAt(delivery.getDispatchedAt());
        dto.setCompletedAt(delivery.getCompletedAt());
        dto.setCreatedAt(delivery.getCreatedAt());
        dto.setDistanceKm(delivery.getDistanceKm());
        dto.setNotes(delivery.getNotes());
        return dto;
    }
}
