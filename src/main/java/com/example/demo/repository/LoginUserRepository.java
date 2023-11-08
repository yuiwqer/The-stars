package com.example.demo.repository;

import com.example.demo.entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser,Long> {
    LoginUser findByUsername(String username);
}
