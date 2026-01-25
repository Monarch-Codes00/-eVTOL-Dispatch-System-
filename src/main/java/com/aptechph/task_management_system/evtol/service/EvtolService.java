package com.aptechph.task_management_system.evtol.service;

import com.aptechph.task_management_system.evtol.dto.*;
import com.aptechph.task_management_system.evtol.model.Evtol;
import com.aptechph.task_management_system.evtol.model.EvtolState;
import com.aptechph.task_management_system.evtol.model.Medication;
import com.aptechph.task_management_system.evtol.repository.EvtolRepository;
import com.aptechph.task_management_system.evtol.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvtolService {

    private final EvtolRepository evtolRepository;
    private final MedicationRepository medicationRepository;

    @Transactional
    public EvtolResponseDto registerEvtol(EvtolRequestDto requestDto) {
      
        if (evtolRepository.existsBySerialNumber(requestDto.getSerialNumber())) {
            throw new IllegalArgumentException("eVTOL with serial number " + requestDto.getSerialNumber() + " already exists");
        }

        Evtol evtol = new Evtol();
        evtol.setSerialNumber(requestDto.getSerialNumber());
        evtol.setModel(requestDto.getModel());
        evtol.setWeightLimit(requestDto.getWeightLimit());
        evtol.setBatteryCapacity(requestDto.getBatteryCapacity());
        evtol.setState(requestDto.getState() != null ? requestDto.getState() : EvtolState.IDLE);
        evtol.setCurrentLoad(0);

        Evtol savedEvtol = evtolRepository.save(evtol);
        return mapToResponseDto(savedEvtol);
    }

    @Transactional
    public EvtolResponseDto loadMedications(LoadMedicationRequestDto requestDto) {
        
        Evtol evtol = evtolRepository.findBySerialNumber(requestDto.getEvtolSerialNumber())
                .orElseThrow(() -> new IllegalArgumentException("eVTOL not found with serial number: " + requestDto.getEvtolSerialNumber()));

      
        if (evtol.getBatteryCapacity() < 25) {
            throw new IllegalStateException("Cannot load eVTOL with battery level below 25%. Current level: " + evtol.getBatteryCapacity() + "%");
        }

        if (evtol.getState() != EvtolState.IDLE && evtol.getState() != EvtolState.LOADING) {
            throw new IllegalStateException("eVTOL is not available for loading. Current state: " + evtol.getState());
        }

       
        evtol.setState(EvtolState.LOADING);

       
        int totalWeight = 0;
        for (String medicationCode : requestDto.getMedicationCodes()) {
            Medication medication = medicationRepository.findByCode(medicationCode)
                    .orElseThrow(() -> new IllegalArgumentException("Medication not found with code: " + medicationCode));
            
            if (medication.getEvtol() != null) {
                throw new IllegalStateException("Medication " + medicationCode + " is already loaded on another eVTOL");
            }
            
            totalWeight += medication.getWeight();
        }

       
        if (evtol.getCurrentLoad() + totalWeight > evtol.getWeightLimit()) {
            throw new IllegalStateException("Cannot load medications. Total weight (" + (evtol.getCurrentLoad() + totalWeight) + 
                    "g) exceeds weight limit (" + evtol.getWeightLimit() + "g)");
        }

    
        for (String medicationCode : requestDto.getMedicationCodes()) {
            Medication medication = medicationRepository.findByCode(medicationCode).get();
            evtol.addMedication(medication);
        }

 
        evtol.setState(EvtolState.LOADED);

        Evtol savedEvtol = evtolRepository.save(evtol);
        return mapToResponseDto(savedEvtol);
    }

    public List<MedicationResponseDto> getLoadedMedications(String serialNumber) {
        Evtol evtol = evtolRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new IllegalArgumentException("eVTOL not found with serial number: " + serialNumber));

        return evtol.getMedications().stream()
                .map(this::mapMedicationToResponseDto)
                .collect(Collectors.toList());
    }

    public List<EvtolResponseDto> getAvailableEvtols() {
        return evtolRepository.findAvailableForLoading().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public BatteryLevelResponseDto getBatteryLevel(String serialNumber) {
        Evtol evtol = evtolRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new IllegalArgumentException("eVTOL not found with serial number: " + serialNumber));

        return new BatteryLevelResponseDto(
                evtol.getSerialNumber(),
                evtol.getBatteryCapacity(),
                evtol.getState().toString()
        );
    }

    public List<EvtolResponseDto> getAllEvtols() {
        return evtolRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public EvtolResponseDto getEvtolBySerialNumber(String serialNumber) {
        Evtol evtol = evtolRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new IllegalArgumentException("eVTOL not found with serial number: " + serialNumber));
        return mapToResponseDto(evtol);
    }

    private EvtolResponseDto mapToResponseDto(Evtol evtol) {
        List<MedicationResponseDto> medications = evtol.getMedications().stream()
                .map(this::mapMedicationToResponseDto)
                .collect(Collectors.toList());

        return new EvtolResponseDto(
                evtol.getId(),
                evtol.getSerialNumber(),
                evtol.getModel(),
                evtol.getWeightLimit(),
                evtol.getBatteryCapacity(),
                evtol.getState(),
                evtol.getCurrentLoad(),
                evtol.getAvailableCapacity(),
                medications
        );
    }

    private MedicationResponseDto mapMedicationToResponseDto(Medication medication) {
        return new MedicationResponseDto(
                medication.getId(),
                medication.getName(),
                medication.getWeight(),
                medication.getCode(),
                medication.getImage()
        );
    }
}
