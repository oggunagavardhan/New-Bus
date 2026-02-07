package com.busyatra.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

import com.busyatra.entity.OtpVerification;
import com.busyatra.repository.OtpRepository;

@Service
public class OtpService {

    private final OtpRepository otpRepository;
    private final EmailService emailService;

    public OtpService(OtpRepository otpRepository, EmailService emailService) {
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }

    public void generateOtp(String email) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        OtpVerification entity =
                otpRepository.findByEmail(email).orElse(new OtpVerification());

        entity.setEmail(email);
        entity.setOtp(otp);
        entity.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(entity);
        emailService.sendOtp(email, otp);
    }

    public boolean verifyOtp(String email, String otp) {
        return otpRepository.findByEmail(email)
                .filter(o -> o.getOtp().equals(otp))
                .filter(o -> o.getExpiryTime().isAfter(LocalDateTime.now()))
                .isPresent();
    }
}
