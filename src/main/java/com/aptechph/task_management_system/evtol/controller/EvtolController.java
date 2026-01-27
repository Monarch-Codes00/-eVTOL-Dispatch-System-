package com.aptechph.task_management_system.evtol.controller;

import com.aptechph.task_management_system.evtol.dto.*;
import com.aptechph.task_management_system.evtol.model.EvtolState;
import com.aptechph.task_management_system.evtol.service.EvtolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evtol")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EvtolController {

    private final EvtolService evtolService;

    @PostMapping("/register")
    public ResponseEntity<EvtolResponseDto> registerEvtol(@Valid @RequestBody EvtolRequestDto requestDto) {
        return new ResponseEntity<>(evtolService.registerEvtol(requestDto), HttpStatus.CREATED);
    }

    @PostMapping("/load")
    public ResponseEntity<EvtolResponseDto> loadMedications(@Valid @RequestBody LoadMedicationRequestDto requestDto) {
        return ResponseEntity.ok(evtolService.loadMedications(requestDto));
    }

    @GetMapping("/{serialNumber}/medications")
    public ResponseEntity<List<MedicationResponseDto>> getLoadedMedications(@PathVariable String serialNumber) {
        return ResponseEntity.ok(evtolService.getLoadedMedications(serialNumber));
    }

    @GetMapping("/available")
    public ResponseEntity<List<EvtolResponseDto>> getAvailableEvtols() {
        return ResponseEntity.ok(evtolService.getAvailableEvtols());
    }

    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<BatteryLevelResponseDto> getBatteryLevel(@PathVariable String serialNumber) {
        return ResponseEntity.ok(evtolService.getBatteryLevel(serialNumber));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EvtolResponseDto>> getAllEvtols() {
        return ResponseEntity.ok(evtolService.getAllEvtols());
    }
    
    @GetMapping("/{serialNumber}")
    public ResponseEntity<EvtolResponseDto> getEvtol(@PathVariable String serialNumber) {
        return ResponseEntity.ok(evtolService.getEvtolBySerialNumber(serialNumber));
    }

    @PatchMapping("/{serialNumber}/state")
    public ResponseEntity<EvtolResponseDto> updateState(@PathVariable String serialNumber, @RequestBody java.util.Map<String, String> payload) {
        EvtolState state = EvtolState.valueOf(payload.get("state"));
        return ResponseEntity.ok(evtolService.updateEvtolState(serialNumber, state));
    }
}
