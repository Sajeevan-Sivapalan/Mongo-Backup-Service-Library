package com.MongoBackupService.MongoBackup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BackupScheduler {

    @Autowired
    private MongoBackupService mongoBackupService;

    @Value("${backup.directory}")
    private String backupDirectory;

    @Value("${backup.schedule}")
    private String backupSchedule;

    private String getCronExpression() {
        switch (backupSchedule.toLowerCase()) {
            case "weekly":
                return "0 0 0 * * SUN";
            case "monthly":
                return "0 0 0 1 * ?";
            case "daily":
            default:
                return "0 0 0 * * ?";
        }
    }

    // Schedule the backup based on the chosen frequency (daily, weekly, monthly)
    @Scheduled(cron = "#{T(com.MongoBackupService.MongoBackup.service.BackupScheduler).getCronExpression()}")
    public void scheduledBackup() {
        try {
            mongoBackupService.backupDatabase(backupDirectory, true);
        } catch (IOException e) {
            System.err.println("Failed to perform MongoDB backup: " + e.getMessage());
        }
    }
}
