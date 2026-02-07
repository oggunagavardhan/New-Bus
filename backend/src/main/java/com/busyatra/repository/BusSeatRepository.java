package com.busyatra.repository;

import com.busyatra.entity.BusSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BusSeatRepository extends JpaRepository<BusSeat,Integer> {

    List<BusSeat> findByBusId(Integer busId);

    BusSeat findByBusIdAndSeatCode(Integer busId,String seatCode);

    List<BusSeat> findByBusIdAndDeck(Integer busId, String lower);
}
