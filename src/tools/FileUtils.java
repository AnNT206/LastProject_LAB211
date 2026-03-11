package tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            return lines;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }

    public static boolean writeFile(String filename, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            return false;
        }
    }

    public static boolean appendFile(String filename, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(line);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
            return false;
        }
    }

    public static boolean fileExists(String filename) {
        return new File(filename).exists();
    }

    public static boolean createFile(String filename) {
        try {
            File file = new File(filename);
            return file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteFile(String filename) {
        File file = new File(filename);
        return file.delete();
    }
}