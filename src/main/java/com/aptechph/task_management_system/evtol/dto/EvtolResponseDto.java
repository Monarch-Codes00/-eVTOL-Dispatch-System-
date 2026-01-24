package com.aptechph.task_management_system.evtol.dto;

import com.aptechph.task_management_system.evtol.model.EvtolModel;
import com.aptechph.task_management_system.evtol.model.EvtolState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvtolResponseDto {

    private Long id;
    private String serialNumber;
    private EvtolModel model;
    private Integer weightLimit;
    private Integer batteryCapacity;
    private EvtolState state;
    private Integer currentLoad;
    private Integer availableCapacity;
    private List<MedicationResponseDto> medications;
}
