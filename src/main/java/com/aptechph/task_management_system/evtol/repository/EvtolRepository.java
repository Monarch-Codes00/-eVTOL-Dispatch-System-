package com.aptechph.task_management_system.evtol.repository;

import com.aptechph.task_management_system.evtol.model.Evtol;
import com.aptechph.task_management_system.evtol.model.EvtolState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvtolRepository extends JpaRepository<Evtol, Long> {
    
    Optional<Evtol> findBySerialNumber(String serialNumber);
    
    boolean existsBySerialNumber(String serialNumber);
    
    // Find available eVTOLs for loading (battery >= 25% and state is IDLE or LOADING)
    @Query("SELECT e FROM Evtol e WHERE e.batteryCapacity >= 25 AND (e.state = 'IDLE' OR e.state = 'LOADING')")
    List<Evtol> findAvailableForLoading();
    
    List<Evtol> findByState(EvtolState state);
}
