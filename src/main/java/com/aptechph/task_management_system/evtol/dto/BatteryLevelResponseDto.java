package com.aptechph.task_management_system.evtol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatteryLevelResponseDto {

    private String serialNumber;
    private Integer batteryCapacity;
    private String state;
}
