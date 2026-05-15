package com.captcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaptchaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaptchaApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("🎤 CAPTCHA Server Started!");
        System.out.println("📍 http://localhost:8888/api/captcha/generate");
        System.out.println("📍 http://localhost:8888/index.html");
        System.out.println("========================================\n");
    }
}
