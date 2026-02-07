package com.busyatra.controller;

import com.busyatra.entity.*;
import com.busyatra.repository.*;
import com.busyatra.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private BusRepository busRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private BusSeatRepository seatRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AdminService adminService;


    /* ================= BUS ================= */

    @GetMapping("/buses")
    public List<Bus> getBuses(){
        return busRepo.findAll();
    }

    @PostMapping("/add-bus")
    public Bus addBus(@RequestBody Bus bus){
        return busRepo.save(bus);
    }

    @DeleteMapping("/delete-bus/{id}")
    public void deleteBus(@PathVariable Integer id){
        busRepo.deleteById(id);
    }


    /* ================= BOOKINGS ================= */

    @GetMapping("/bookings")
    public List<Booking> getBookings(){
        return bookingRepo.findAll();
    }


    @PutMapping("/approve-payment/{id}")
    public void approvePayment(@PathVariable Integer id){
        adminService.approveBooking(id);
    }



    /* ================= USERS ================= */

    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @PutMapping("/block-user/{id}")
    public void blockUser(@PathVariable Long id){
        adminService.blockUser(id);
    }


    /* ================= SEATS ================= */

    @GetMapping("/seats/{busId}")
    public List<BusSeat> getSeats(@PathVariable Integer busId){
        return seatRepo.findByBusId(busId);
    }

    @PutMapping("/update-seat/{seatId}")
    public BusSeat updateSeat(
            @PathVariable Integer seatId,
            @RequestBody BusSeat update){

        BusSeat seat = seatRepo.findById(seatId).orElseThrow();

        seat.setBooked(update.isBooked());
        seat.setPrice(update.getPrice());

        return seatRepo.save(seat);
    }


    /* ================= REVENUE ================= */

    @GetMapping("/revenue")
    public Double getRevenue(){
        return adminService.calculateRevenue();
    }
}
