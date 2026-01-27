package com.aptechph.task_management_system.evtol.service;

import com.aptechph.task_management_system.evtol.dto.*;
import com.aptechph.task_management_system.evtol.model.EvtolState;
import java.util.List;

public interface EvtolService {
    EvtolResponseDto registerEvtol(EvtolRequestDto requestDto);
    EvtolResponseDto loadMedications(LoadMedicationRequestDto requestDto);
    List<MedicationResponseDto> getLoadedMedications(String serialNumber);
    List<EvtolResponseDto> getAvailableEvtols();
    BatteryLevelResponseDto getBatteryLevel(String serialNumber);
    List<EvtolResponseDto> getAllEvtols();
    EvtolResponseDto getEvtolBySerialNumber(String serialNumber);
    EvtolResponseDto updateEvtolState(String serialNumber, EvtolState state);
}
