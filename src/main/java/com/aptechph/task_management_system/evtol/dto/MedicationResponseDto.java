package com.aptechph.task_management_system.evtol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationResponseDto {

    private Long id;
    private String name;
    private Integer weight;
    private String code;
    private String image;
}
