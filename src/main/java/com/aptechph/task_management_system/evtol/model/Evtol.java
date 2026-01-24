package com.aptechph.task_management_system.evtol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evtols")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evtol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Serial number is required")
    @Size(max = 100, message = "Serial number must not exceed 100 characters")
    @Column(unique = true, nullable = false, length = 100)
    private String serialNumber;

    @NotNull(message = "Model is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvtolModel model;

    @NotNull(message = "Weight limit is required")
    @Max(value = 500, message = "Weight limit must not exceed 500 grams")
    @Column(nullable = false)
    private Integer weightLimit; // in grams

    @NotNull(message = "Battery capacity is required")
    @Min(value = 0, message = "Battery capacity must be at least 0%")
    @Max(value = 100, message = "Battery capacity must not exceed 100%")
    @Column(nullable = false)
    private Integer batteryCapacity; // percentage

    @NotNull(message = "State is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvtolState state;

    @OneToMany(mappedBy = "evtol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medication> medications = new ArrayList<>();

    @Column(nullable = false)
    private Integer currentLoad = 0; // current weight in grams

    // Helper method to check if eVTOL can be loaded
    public boolean canBeLoaded() {
        return this.batteryCapacity >= 25 && 
               (this.state == EvtolState.IDLE || this.state == EvtolState.LOADING);
    }

    // Helper method to check available capacity
    public int getAvailableCapacity() {
        return this.weightLimit - this.currentLoad;
    }

    // Helper method to add medication
    public void addMedication(Medication medication) {
        medications.add(medication);
        medication.setEvtol(this);
        this.currentLoad += medication.getWeight();
    }

    // Helper method to remove medication
    public void removeMedication(Medication medication) {
        medications.remove(medication);
        medication.setEvtol(null);
        this.currentLoad -= medication.getWeight();
    }
}
