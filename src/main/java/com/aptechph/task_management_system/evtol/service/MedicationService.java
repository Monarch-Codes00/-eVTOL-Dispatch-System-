package com.aptechph.task_management_system.evtol.service;

import com.aptechph.task_management_system.evtol.dto.MedicationRequestDto;
import com.aptechph.task_management_system.evtol.dto.MedicationResponseDto;
import com.aptechph.task_management_system.evtol.model.Medication;
import com.aptechph.task_management_system.evtol.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;

    @Transactional
    public MedicationResponseDto createMedication(MedicationRequestDto requestDto) {
        // Check if code already exists
        if (medicationRepository.existsByCode(requestDto.getCode())) {
            throw new IllegalArgumentException("Medication with code " + requestDto.getCode() + " already exists");
        }

        Medication medication = new Medication();
        medication.setName(requestDto.getName());
        medication.setWeight(requestDto.getWeight());
        medication.setCode(requestDto.getCode());
        medication.setImage(requestDto.getImage());

        Medication savedMedication = medicationRepository.save(medication);
        return mapToResponseDto(savedMedication);
    }

    public List<MedicationResponseDto> getAllMedications() {
        return medicationRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public MedicationResponseDto getMedicationByCode(String code) {
        Medication medication = medicationRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Medication not found with code: " + code));
        return mapToResponseDto(medication);
    }

    public List<MedicationResponseDto> getAvailableMedications() {
        return medicationRepository.findAll().stream()
                .filter(medication -> medication.getEvtol() == null)
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMedication(Long id) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medication not found with id: " + id));
        
        if (medication.getEvtol() != null) {
            throw new IllegalStateException("Cannot delete medication that is loaded on an eVTOL");
        }
        
        medicationRepository.delete(medication);
    }

    private MedicationResponseDto mapToResponseDto(Medication medication) {
        return new MedicationResponseDto(
                medication.getId(),
                medication.getName(),
                medication.getWeight(),
                medication.getCode(),
                medication.getImage()
        );
    }
}
