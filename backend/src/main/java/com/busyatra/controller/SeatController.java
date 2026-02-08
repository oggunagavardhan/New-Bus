package com.busyatra.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import com.busyatra.repository.BusSeatRepository;
import com.busyatra.entity.BusSeat;

@RestController
@RequestMapping("/api/seats")
@CrossOrigin(origins = "*")
public class SeatController {

    private final BusSeatRepository seatRepo;

    public SeatController(BusSeatRepository seatRepo) {
        this.seatRepo = seatRepo;
    }

    @GetMapping("/{busId}")
    public Map<String, Object> getSeats(@PathVariable Integer busId) {

        List<BusSeat> lower = seatRepo.findByBusIdAndDeck(busId, "LOWER");
        List<BusSeat> upper = seatRepo.findByBusIdAndDeck(busId, "UPPER");

        Map<String, Object> res = new HashMap<>();
        res.put("lowerDeck", lower);
        res.put("upperDeck", upper);

        return res;
    }
}
