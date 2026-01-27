package com.aptechph.task_management_system.evtol.scheduler;

import com.aptechph.task_management_system.evtol.model.BatteryAuditLog;
import com.aptechph.task_management_system.evtol.model.Evtol;
import com.aptechph.task_management_system.evtol.repository.BatteryAuditLogRepository;
import com.aptechph.task_management_system.evtol.repository.EvtolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatteryCheckScheduler {

    private final EvtolRepository evtolRepository;
    private final BatteryAuditLogRepository auditLogRepository;
    
    @Scheduled(fixedRate = 120000)
    public void checkBatteryLevels() {
        log.info("Starting periodic battery level check for all eVTOLs");
        
        List<Evtol> evtols = evtolRepository.findAll();
        
        for (Evtol evtol : evtols) {
            log.info("eVTOL [{}]: Battery level: {}%, State: {}", 
                    evtol.getSerialNumber(), evtol.getBatteryCapacity(), evtol.getState());
            
            BatteryAuditLog auditLog = new BatteryAuditLog(
                    evtol.getSerialNumber(),
                    evtol.getBatteryCapacity(),
                    evtol.getState()
            );
            
            auditLogRepository.save(auditLog);
        }
        
        log.info("Battery level check completed. Logs created for {} eVTOLs", evtols.size());
    }
}
