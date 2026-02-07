package com.busyatra.controller;

import com.busyatra.entity.Booking;
import com.busyatra.repository.BookingRepository;
import com.busyatra.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    private final BookingRepository repo;
    private final EmailService emailService;

    public BookingController(BookingRepository repo, EmailService emailService) {
        this.repo = repo;
        this.emailService = emailService;
    }

    /* ================= USER BOOK ================= */
    @PostMapping("/book")
    public Booking book(@RequestBody Booking booking) {

        booking.setStatus("PENDING");

        return repo.save(booking);   // IMPORTANT → return saved booking
    }


    /* ================= ADMIN APPROVE ================= */
    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Integer id) {

        Booking b = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        b.setStatus("APPROVED");
        repo.save(b);

        emailService.sendTicketConfirmation(
                b.getEmail(),
                b.getBusName(),
                b.getSource() + " → " + b.getDestination(),
                b.getSeats(),
                b.getAmount(),
                b.getUpiId()
        );

        return "APPROVED_AND_EMAIL_SENT";
    }


    /* ================= GET BOOKING STATUS ================= */
    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable Integer id) {

        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
    @GetMapping("/user/{email}")
    public List<Booking> getUserBookings(@PathVariable String email){

        List<Booking> bookings = repo.findByEmail(email);

        return bookings == null ? new ArrayList<>() : bookings;
    }


}
