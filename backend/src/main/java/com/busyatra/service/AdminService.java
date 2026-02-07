package com.busyatra.service;

import com.busyatra.entity.*;
import com.busyatra.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private BusSeatRepository seatRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService emailService;

    /* APPROVE BOOKING */

    public String approveBooking(Integer bookingId){

        Booking booking = bookingRepo.findById(bookingId).orElse(null);

        if(booking == null){
            return "BOOKING_NOT_FOUND";
        }

        booking.setStatus("APPROVED");
        bookingRepo.save(booking);

        /* LOCK SEATS */

        String[] seats = booking.getSeats().split(",");

        for(String code : seats){

            BusSeat seat = seatRepo.findByBusIdAndSeatCode(
                    booking.getBusId(),
                    code.trim()
            );

            if(seat != null){
                seat.setBooked(true);
                seatRepo.save(seat);
            }
        }

        /* SEND EMAIL */

        try{
            emailService.sendTicketConfirmation(
                    booking.getEmail(),
                    booking.getBusName(),
                    booking.getSource() + " → " + booking.getDestination(),
                    booking.getSeats(),
                    booking.getAmount(),
                    booking.getUpiId()
            );
        }
        catch(Exception e){
            System.out.println("MAIL ERROR: " + e.getMessage());
        }

        return "BOOKING_APPROVED";
    }


    /* BLOCK USER */

    public String blockUser(Long userId){

        User user = userRepo.findById(userId).orElse(null);

        if(user == null){
            return "USER_NOT_FOUND";
        }

        user.setStatus("BLOCKED");
        userRepo.save(user);

        return "USER_BLOCKED";
    }


    /* REVENUE */

    public Double calculateRevenue(){

        return bookingRepo.findAll()
                .stream()
                .filter(b -> "APPROVED".equals(b.getStatus()))
                .mapToDouble(Booking::getAmount)
                .sum();
    }
}
