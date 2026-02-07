package com.busyatra.controller;

import com.busyatra.entity.User;
import com.busyatra.repository.UserRepository;
import com.busyatra.service.OtpService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepo;
    private final OtpService otpService;

    public AuthController(UserRepository userRepo,
                          OtpService otpService) {
        this.userRepo = userRepo;
        this.otpService = otpService;
    }

    /* ================= REGISTER ================= */
    @PostMapping(value = "/register", produces = "text/plain")
    public String register(@RequestBody User user) {

        if (userRepo.findByEmail(user.getEmail()) != null) {
            return "EMAIL_EXISTS";
        }

        user.setRole("USER");
        userRepo.save(user);

        return "REGISTERED_SUCCESSFULLY";
    }

    /* ================= SEND OTP ================= */
    @PostMapping(value = "/send-otp", produces = "text/plain")
    public String sendOtp(@RequestBody User user) {

        String email = user.getEmail();

        if (userRepo.findByEmail(email) == null) {
            return "EMAIL_NOT_REGISTERED";
        }

        otpService.generateOtp(email);

        return "OTP_SENT";
    }

    /* ================= VERIFY OTP ================= */
    @PostMapping(value = "/verify-otp", produces = "text/plain")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp) {

        boolean valid = otpService.verifyOtp(email, otp);

        return valid ? "SUCCESS" : "INVALID_OTP";
    }

    /* ================= PROFILE ================= */
    @GetMapping("/profile/{email}")
    public User getProfile(@PathVariable String email) {

        return userRepo.findByEmail(email);
    }
}
