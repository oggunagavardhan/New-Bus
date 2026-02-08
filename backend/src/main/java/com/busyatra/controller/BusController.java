package com.busyatra.controller;

import org.springframework.web.bind.annotation.*;
import com.busyatra.entity.Bus;
import com.busyatra.repository.BusRepository;

import java.util.List;

@RestController
@RequestMapping("/api/bus")
@CrossOrigin(origins = "*")
public class BusController {

    private final BusRepository busRepo;

    public BusController(BusRepository busRepo) {
        this.busRepo = busRepo;
    }

    // 🔍 SEARCH BUSES
    @GetMapping("/search")
    public List<Bus> searchBuses(
            @RequestParam String from,
            @RequestParam String to
    ) {
        return busRepo.findBySourceIgnoreCaseAndDestinationIgnoreCase(from, to);
    }
}
