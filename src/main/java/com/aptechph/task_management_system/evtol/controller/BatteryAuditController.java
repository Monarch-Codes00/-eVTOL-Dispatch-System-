package com.aptechph.task_management_system.evtol.controller;

import com.aptechph.task_management_system.evtol.model.BatteryAuditLog;
import com.aptechph.task_management_system.evtol.repository.BatteryAuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BatteryAuditController {

    private final BatteryAuditLogRepository auditLogRepository;

    @GetMapping("/logs")
    public ResponseEntity<List<BatteryAuditLog>> getRecentLogs(@RequestParam(defaultValue = "50") int limit) {
        return ResponseEntity.ok(auditLogRepository.findAll(
                PageRequest.of(0, limit, Sort.by("timestamp").descending())).getContent());
    }

    @GetMapping("/logs/{serialNumber}")
    public ResponseEntity<List<BatteryAuditLog>> getLogsByDrone(@PathVariable String serialNumber) {
        return ResponseEntity.ok(auditLogRepository.findByEvtolSerialNumberOrderByTimestampDesc(serialNumber));
    }
}
