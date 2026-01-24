package com.aptechph.task_management_system.evtol.dto;

import com.aptechph.task_management_system.evtol.model.EvtolModel;
import com.aptechph.task_management_system.evtol.model.EvtolState;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvtolRequestDto {

    @NotBlank(message = "Serial number is required")
    @Size(max = 100, message = "Serial number must not exceed 100 characters")
    private String serialNumber;

    @NotNull(message = "Model is required")
    private EvtolModel model;

    @NotNull(message = "Weight limit is required")
    @Max(value = 500, message = "Weight limit must not exceed 500 grams")
    @Min(value = 1, message = "Weight limit must be at least 1 gram")
    private Integer weightLimit;

    @NotNull(message = "Battery capacity is required")
    @Min(value = 0, message = "Battery capacity must be at least 0%")
    @Max(value = 100, message = "Battery capacity must not exceed 100%")
    private Integer batteryCapacity;

    private EvtolState state = EvtolState.IDLE;
}
