package com.busyatra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.busyatra.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);   // ✅ THIS LINE FIXES ERROR
}
