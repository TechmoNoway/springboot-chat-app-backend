package com.trickynguci.civicmessagerbackend;

import com.trickynguci.civicmessagerbackend.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CivicMessagerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CivicMessagerBackendApplication.class, args);
    }
}


    