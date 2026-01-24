package com.aptechph.task_management_system.evtol.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadMedicationRequestDto {

    @NotNull(message = "eVTOL serial number is required")
    private String evtolSerialNumber;

    @NotEmpty(message = "At least one medication code is required")
    private List<String> medicationCodes;
}
