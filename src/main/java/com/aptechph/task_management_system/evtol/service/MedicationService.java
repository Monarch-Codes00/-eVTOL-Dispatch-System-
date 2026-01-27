package com.aptechph.task_management_system.evtol.service;

import com.aptechph.task_management_system.evtol.dto.MedicationRequestDto;
import com.aptechph.task_management_system.evtol.dto.MedicationResponseDto;
import java.util.List;

public interface MedicationService {
    MedicationResponseDto createMedication(MedicationRequestDto requestDto);
    List<MedicationResponseDto> getAllMedications();
    MedicationResponseDto getMedicationByCode(String code);
    List<MedicationResponseDto> getAvailableMedications();
    void deleteMedication(Long id);
    MedicationResponseDto updateMedication(Long id, MedicationRequestDto requestDto);
}
