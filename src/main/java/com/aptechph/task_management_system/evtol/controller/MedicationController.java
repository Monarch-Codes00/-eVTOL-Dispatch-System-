package com.aptechph.task_management_system.evtol.controller;

import com.aptechph.task_management_system.evtol.dto.MedicationRequestDto;
import com.aptechph.task_management_system.evtol.dto.MedicationResponseDto;
import com.aptechph.task_management_system.evtol.service.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping
    public ResponseEntity<MedicationResponseDto> createMedication(@Valid @RequestBody MedicationRequestDto requestDto) {
        return new ResponseEntity<>(medicationService.createMedication(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MedicationResponseDto>> getAllMedications() {
        return ResponseEntity.ok(medicationService.getAllMedications());
    }

    @GetMapping("/available")
    public ResponseEntity<List<MedicationResponseDto>> getAvailableMedications() {
        return ResponseEntity.ok(medicationService.getAvailableMedications());
    }

    @GetMapping("/{code}")
    public ResponseEntity<MedicationResponseDto> getMedication(@PathVariable String code) {
        return ResponseEntity.ok(medicationService.getMedicationByCode(code));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationResponseDto> updateMedication(@PathVariable Long id, @Valid @RequestBody MedicationRequestDto requestDto) {
        return ResponseEntity.ok(medicationService.updateMedication(id, requestDto));
    }
}
