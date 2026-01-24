package com.aptechph.task_management_system.evtol.config;

import com.aptechph.task_management_system.evtol.model.Evtol;
import com.aptechph.task_management_system.evtol.model.EvtolModel;
import com.aptechph.task_management_system.evtol.model.EvtolState;
import com.aptechph.task_management_system.evtol.model.Medication;
import com.aptechph.task_management_system.evtol.repository.EvtolRepository;
import com.aptechph.task_management_system.evtol.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final EvtolRepository evtolRepository;
    private final MedicationRepository medicationRepository;

    @Override
    public void run(String... args) {
        if (evtolRepository.count() == 0) {
            log.info("Preloading eVTOL fleet...");
            
            evtolRepository.saveAll(Arrays.asList(
                new Evtol(null, "EVTOL-001", EvtolModel.LIGHTWEIGHT, 300, 100, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-002", EvtolModel.MIDDLEWEIGHT, 400, 85, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-003", EvtolModel.CRUISERWEIGHT, 500, 100, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-004", EvtolModel.HEAVYWEIGHT, 500, 20, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-005", EvtolModel.LIGHTWEIGHT, 300, 90, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-006", EvtolModel.MIDDLEWEIGHT, 400, 75, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-007", EvtolModel.CRUISERWEIGHT, 500, 100, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-008", EvtolModel.HEAVYWEIGHT, 500, 60, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-009", EvtolModel.LIGHTWEIGHT, 300, 45, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-010", EvtolModel.MIDDLEWEIGHT, 400, 100, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-011", EvtolModel.LIGHTWEIGHT, 300, 100, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-012", EvtolModel.MIDDLEWEIGHT, 400, 95, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-013", EvtolModel.CRUISERWEIGHT, 500, 100, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-014", EvtolModel.HEAVYWEIGHT, 500, 15, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-015", EvtolModel.LIGHTWEIGHT, 300, 80, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-016", EvtolModel.MIDDLEWEIGHT, 400, 70, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-017", EvtolModel.CRUISERWEIGHT, 500, 100, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-018", EvtolModel.HEAVYWEIGHT, 500, 55, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-019", EvtolModel.LIGHTWEIGHT, 300, 40, EvtolState.IDLE, new java.util.ArrayList<>(), 0),
                new Evtol(null, "EVTOL-020", EvtolModel.MIDDLEWEIGHT, 400, 90, EvtolState.IDLE, new java.util.ArrayList<>(), 0)
            ));
            
            log.info("Created 20 eVTOLs.");
        }

        if (medicationRepository.count() == 0) {
            log.info("Preloading medications...");
            
            medicationRepository.saveAll(Arrays.asList(
                new Medication(null, "Paracetamol", 50, "PARA_001", "https://img.freepik.com/free-vector/realistic-white-bottle-with-pills_1284-17799.jpg", null),
                new Medication(null, "Ibuprofen", 30, "IBU_001", "https://img.freepik.com/free-vector/package-design-medication_23-2148168233.jpg", null),
                new Medication(null, "Amoxicillin", 100, "AMOX_001", "https://img.freepik.com/free-photo/pills-spilled-from-bottle-yellow-background_23-2148818451.jpg", null),
                new Medication(null, "Insulin", 80, "INS_001", "https://img.freepik.com/free-vector/realistic-insulin-syringe_1284-17740.jpg", null),
                new Medication(null, "Adrenaline", 40, "ADR_001", "https://img.freepik.com/free-photo/medical-vials-white-background_23-2148818449.jpg", null)
            ));
            
            log.info("Created 5 medications.");
        }
    }
}
