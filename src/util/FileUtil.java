package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
    private FileUtil() {
    }

    public static void savePortfolio(String path, Map<String, Integer> portfolio) {
        Path filePath = Paths.get(path);
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
                    writer.write(entry.getKey() + "," + entry.getValue());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save portfolio to file: " + path, e);
        }
    }

    public static Map<String, Integer> loadPortfolio(String path) {
        Map<String, Integer> portfolio = new HashMap<>();
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            return portfolio;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                String[] parts = trimmed.split(",");
                if (parts.length == 2) {
                    portfolio.put(parts[0].toUpperCase(), Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load portfolio from file: " + path, e);
        }

        return portfolio;
    }
}
