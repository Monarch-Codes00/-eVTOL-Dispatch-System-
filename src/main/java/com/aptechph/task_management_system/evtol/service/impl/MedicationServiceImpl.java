package com.aptechph.task_management_system.evtol.service.impl;

import com.aptechph.task_management_system.evtol.dto.MedicationRequestDto;
import com.aptechph.task_management_system.evtol.dto.MedicationResponseDto;
import com.aptechph.task_management_system.evtol.model.Medication;
import com.aptechph.task_management_system.evtol.repository.MedicationRepository;
import com.aptechph.task_management_system.evtol.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;

    @Override
    @Transactional
    public MedicationResponseDto createMedication(MedicationRequestDto requestDto) {
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

    @Override
    public List<MedicationResponseDto> getAllMedications() {
        return medicationRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public MedicationResponseDto getMedicationByCode(String code) {
        Medication medication = medicationRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Medication not found with code: " + code));
        return mapToResponseDto(medication);
    }

    @Override
    public List<MedicationResponseDto> getAvailableMedications() {
        return medicationRepository.findAll().stream()
                .filter(medication -> medication.getEvtol() == null)
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteMedication(Long id) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medication not found with id: " + id));
        
        if (medication.getEvtol() != null) {
            throw new IllegalStateException("Cannot delete medication that is loaded on an eVTOL");
        }
        
        medicationRepository.delete(medication);
    }

    @Override
    @Transactional
    public MedicationResponseDto updateMedication(Long id, MedicationRequestDto requestDto) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medication not found with id: " + id));

        if (requestDto.getName() != null) medication.setName(requestDto.getName());
        if (requestDto.getWeight() != null) medication.setWeight(requestDto.getWeight());
        if (requestDto.getCode() != null) medication.setCode(requestDto.getCode());
        if (requestDto.getImage() != null) medication.setImage(requestDto.getImage());

        Medication savedMedication = medicationRepository.save(medication);
        return mapToResponseDto(savedMedication);
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
