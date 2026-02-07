package com.busyatra.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /* ================= OTP MAIL ================= */
    public void sendOtp(String email, String otp) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(email);
            message.setSubject("BusYatra OTP Verification");
            message.setText("Your OTP is: " + otp);

            mailSender.send(message);

            System.out.println("OTP SENT SUCCESSFULLY");

        } catch (Exception e) {

            System.out.println("OTP MAIL ERROR: " + e.getMessage());

            // Do NOT crash entire API
            throw new RuntimeException("Unable to send OTP email");
        }
    }

    /* ================= TICKET CONFIRMATION ================= */

    public void sendTicketConfirmation(
            String email,
            String busName,
            String route,
            String seats,
            int amount,
            String upi
    ) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(email);
            message.setSubject("Busyatra Ticket Confirmed");

            message.setText(
                    "Your Bus Ticket has been APPROVED\n\n" +
                            "Bus: " + busName + "\n" +
                            "Route: " + route + "\n" +
                            "Seats: " + seats + "\n" +
                            "Amount Paid: ₹" + amount + "\n" +
                            "UPI ID: " + upi + "\n\n" +
                            "Thank you for booking with Busyatra"
            );

            mailSender.send(message);

            System.out.println("TICKET MAIL SENT");

        } catch (Exception e) {

            System.out.println("TICKET MAIL ERROR: " + e.getMessage());
        }
    }
}
