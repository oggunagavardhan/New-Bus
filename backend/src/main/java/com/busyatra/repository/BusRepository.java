package com.busyatra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.busyatra.entity.Bus;
import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Integer> {

    List<Bus> findBySourceIgnoreCaseAndDestinationIgnoreCase(
            String source,
            String destination
    );
}
