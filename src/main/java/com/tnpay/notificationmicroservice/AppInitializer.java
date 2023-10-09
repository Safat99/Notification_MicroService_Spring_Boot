package com .tnpay.notificationmicroservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class AppInitializer implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String uploads = "notification-microservice/src/main/resources/static/uploads";

        // Create the directory
        File directory = new File(uploads);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Directory created successfully.");
            } else {
                System.err.println("Failed to create the directory.");
            }
        } else {
            System.out.println("Directory already exists.");
        }
    }
}
