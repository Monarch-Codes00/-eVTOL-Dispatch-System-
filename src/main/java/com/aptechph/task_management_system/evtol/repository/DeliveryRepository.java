package com.aptechph.task_management_system.evtol.repository;

import com.aptechph.task_management_system.evtol.model.Delivery;
import com.aptechph.task_management_system.evtol.model.Evtol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByEvtol(Evtol evtol);
    List<Delivery> findByEvtolSerialNumber(String serialNumber);
}
