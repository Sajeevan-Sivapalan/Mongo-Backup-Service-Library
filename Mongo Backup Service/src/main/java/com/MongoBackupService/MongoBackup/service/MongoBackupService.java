package com.MongoBackupService.MongoBackup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class MongoBackupService {

    @Autowired
    private MongoDatabase mongoDatabase;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(MongoBackupService.class);

    public void backupDatabase(String backupDir, boolean compress) throws IOException {
        File directory = new File(backupDir);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Could not create backup directory: " + backupDir);
        }

        for (String collectionName : mongoDatabase.listCollectionNames()) {
            backupCollection(collectionName, backupDir);
        }

        if (compress) {
            compressDirectory(backupDir, backupDir + ".zip");
            deleteDirectory(directory);
        }

        logger.info("Backup completed successfully at {}", backupDir);
    }

    private void backupCollection(String collectionName, String backupDir) throws IOException {
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        List<Document> documents = collection.find().into(new ArrayList<>());

        File backupFile = new File(backupDir, collectionName + ".json");
        try (FileWriter writer = new FileWriter(backupFile)) {
            for (Document document : documents) {
                writer.write(objectMapper.writeValueAsString(document) + System.lineSeparator());
            }
        }
    }

    public void restoreDatabase(String backupDir) throws IOException {
        File directory = new File(backupDir);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IOException("Backup directory does not exist: " + backupDir);
        }

        for (File file : directory.listFiles((dir, name) -> name.endsWith(".json"))) {
            restoreCollection(file);
        }

        logger.info("Restore completed successfully from {}", backupDir);
    }

    private void restoreCollection(File backupFile) throws IOException {
        String collectionName = backupFile.getName().replace(".json", "");
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

        try (BufferedReader reader = new BufferedReader(new FileReader(backupFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Document document = Document.parse(line);
                collection.insertOne(document);
            }
        }
    }

    private void compressDirectory(String sourceDir, String zipFilePath) throws IOException {
        try (ZipArchiveOutputStream zipOutput = new ZipArchiveOutputStream(new FileOutputStream(zipFilePath))) {
            File sourceFile = new File(sourceDir);
            for (File file : sourceFile.listFiles()) {
                ZipArchiveEntry entry = new ZipArchiveEntry(file, file.getName());
                zipOutput.putArchiveEntry(entry);
                Files.copy(file.toPath(), zipOutput);
                zipOutput.closeArchiveEntry();
            }
        }
    }

    private void deleteDirectory(File directory) throws IOException {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                Files.delete(file.toPath());
            }
        }
        Files.delete(directory.toPath());
    }
}
