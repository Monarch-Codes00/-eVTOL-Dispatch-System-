package com.aptechph.task_management_system.evtol.repository;

import com.aptechph.task_management_system.evtol.model.BatteryAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BatteryAuditLogRepository extends JpaRepository<BatteryAuditLog, Long> {
    
    List<BatteryAuditLog> findByEvtolSerialNumber(String serialNumber);
    
    List<BatteryAuditLog> findByEvtolSerialNumberOrderByTimestampDesc(String serialNumber);
    
    List<BatteryAuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
