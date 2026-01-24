package com.aptechph.task_management_system.evtol.repository;

import com.aptechph.task_management_system.evtol.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    
    Optional<Medication> findByCode(String code);
    
    boolean existsByCode(String code);
    
    List<Medication> findByEvtolId(Long evtolId);
    
    List<Medication> findByEvtolSerialNumber(String serialNumber);
}
