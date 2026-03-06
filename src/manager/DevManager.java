package manager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Developer;
import model.Project;
import java.util.ArrayList;
import tools.FileUtils;

public class DevManager {

    private List<Developer> devList;
    private List<Project> projList;
    private String devFilePath;
    private String projFilePath;
    private boolean saved;

    //constructor
    public DevManager() {
        devList = new ArrayList<>();
        projList = new ArrayList<>();
        this.saved = true;
        this.devFilePath = "developers.txt";
        this.projFilePath = "projects.txt";
    }

    //Getter and setter
    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void readFromFile() {
        // Xóa dữ liệu cũ trước khi đọc để tránh bị nhân đôi
        devList.clear();
        projList.clear();

        // 1. ĐỌC FILE DEVELOPERS.TXT
        List<String> devLines = FileUtils.readFile(devFilePath);
        for (String line : devLines) {
            try {
                int startBracket = line.indexOf('[');
                int endBracket = line.indexOf(']');

                String part1 = line.substring(0, startBracket);
                String[] tokens1 = part1.split(",");
                String id = tokens1[0].trim();
                String name = tokens1[1].trim();

                String langString = line.substring(startBracket + 1, endBracket);
                List<String> langs = new ArrayList<>();
                if (!langString.trim().isEmpty()) {
                    String[] langArray = langString.split(",");
                    for (String lang : langArray) {
                        langs.add(lang.trim());
                    }
                }

                String part3 = line.substring(endBracket + 1);
                int salary = Integer.parseInt(part3.replace(",", "").trim());

                devList.add(new Developer(id, name, langs, salary));
            } catch (Exception e) {
                System.out.println("Error parsing developer line: " + line);
            }
        }

        // 2. ĐỌC FILE PROJECTS.TXT
        List<String> projLines = FileUtils.readFile(projFilePath);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (String line : projLines) {
            try {
                String[] tokens = line.split(",");
                if (tokens.length >= 5) {
                    String pId = tokens[0].trim();
                    String dId = tokens[1].trim();
                    String pName = tokens[2].trim();
                    int duration = Integer.parseInt(tokens[3].trim());
                    LocalDate startDate = LocalDate.parse(tokens[4].trim(), formatter);

                    projList.add(new Project(pId, dId, pName, duration, startDate));
                }
            } catch (Exception e) {
                System.out.println("Error parsing project line: " + line);
            }
        }

        // 3. CẬP NHẬT TRẠNG THÁI VÀ HIỂN THỊ THÔNG BÁO CHI TIẾT
        this.saved = true;
        System.out.println("Data loaded successfully from files!");
        // In ra số lượng đối tượng thực tế đã đưa vào danh sách thành công
        System.out.println("-> Total Developers loaded: " + devList.size());
        System.out.println("-> Total Projects loaded: " + projList.size());
    }

    public void listAllDevelopers() {
        if (devList.isEmpty()) {
            System.out.println("No developers found");
            return;
        }

        System.out.println("+----------+----------------------+----------------------+------------+");
        System.out.println("|    ID    |       Full Name      |      Languages       |   Salary   |");
        System.out.println("+----------+----------------------+----------------------+------------+");

        for (Developer dev : devList) {
            String langs = String.join(", ", dev.getProgramLanguages());
            System.out.printf("| %-8s | %-20s | %-20s | %-10d |%n",
                    dev.getDevId(),
                    dev.getFullName(),
                    langs,
                    dev.getSalary());
        }
        System.out.println("+----------+----------------------+----------------------+------------+");
        System.out.println("Total: " + devList.size() + " developer(s)");
    }

    public Developer findById(String id) {
        for (Developer dev : devList) {
            if (dev.getDevId().equals(id)) {
                return dev;
            }
        }
        return null;
    }

    public boolean addNewDeveloper(Developer dev) {
        if (findById(dev.getDevId()) != null) {
            System.out.println("Developer already exists");
            return false;
        }

        if (dev.getSalary() < 1000) {
            System.out.println("Salary must be greater than 1000");
            return false;
        }

        System.out.println("Developer added successfully");
        saved = false;
        devList.add(dev);
        return true;
    }

    public void searchDeveloper(String devId) {
        System.out.println("--- SEARCH DEVELOPER BY ID ---");
        System.out.println("Search developer with id '" + devId + "':");

        boolean found = false;
        for (Developer dev : devList) {
            if (dev.getDevId().equalsIgnoreCase(devId)) {
                found = true;

                System.out.println("+----------+----------------------+----------------------+------------+");
                System.out.println("|    ID    |       Full Name      |      Languages       |   Salary   |");
                System.out.println("+----------+----------------------+----------------------+------------+");

                String langs = String.join(", ", dev.getProgramLanguages());
                System.out.printf("| %-8s | %-20s | %-20s | %-10d |%n",
                        dev.getDevId(),
                        dev.getFullName(),
                        langs,
                        dev.getSalary());
                System.out.println("+----------+----------------------+----------------------+------------+");
                break;
            }
        }
        if (!found) {
            System.out.println("Develop ID does not exist!");
        }
    }

    public boolean updateSalary(String devId, int newSalary) {
        Developer dev = findById(devId);
        if (dev == null) {
            System.out.println("Developer ID does not exist");
            return false;
        }

        if (newSalary < 1000) {
            System.out.println("Salary must be at least 1000 USD");
            return false;
        }

        dev.setSalary(newSalary);
        saved = false;
        System.out.println("Update developer's salary successfully");
        return true;
    }

    public void listByLanguage(String language) {
        System.out.println("--- SEARCH DEVELOPERS BY LANGUAGE ---");
        System.out.println("Searching for language '" + language + "':");

        boolean found = false;
        boolean isHeaderPrinted = false;

        for (Developer dev : devList) {
            boolean hasLanguage = false;

            for (String lang : dev.getProgramLanguages()) {
                if (lang.trim().equalsIgnoreCase(language.trim())) {
                    hasLanguage = true;
                    found = true;
                    break;
                }
            }

            if (hasLanguage) {
                if (!isHeaderPrinted) {
                    System.out.println("+----------+----------------------+----------------------+------------+");
                    System.out.println("|    ID    |       Full Name      |      Languages       |   Salary   |");
                    System.out.println("+----------+----------------------+----------------------+------------+");
                    isHeaderPrinted = true;
                }

                String langs = String.join(", ", dev.getProgramLanguages());
                System.out.printf("| %-8s | %-20s | %-20s | %-10d |%n",
                        dev.getDevId(),
                        dev.getFullName(),
                        langs,
                        dev.getSalary());
            }
        }
        if (found) {
            System.out.println("+----------+----------------------+----------------------+------------+");
        } else {
            System.out.println("No developers found with language: " + language);
        }
    }

    public void saveToFile() {
        // 1. CHUẨN BỊ DỮ LIỆU CHO DEVELOPERS
        List<String> devLines = new ArrayList<>();
        for (Developer dev : devList) {
            // List.toString() sẽ tự tạo chuỗi dạng [Java, C++]
            String line = String.format("%s, %s, %s, %d",
                    dev.getDevId(),
                    dev.getFullName(),
                    dev.getProgramLanguages().toString(),
                    dev.getSalary());
            devLines.add(line);
        }

        // 2. CHUẨN BỊ DỮ LIỆU CHO PROJECTS
        List<String> projLines = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Project proj : projList) {
            // Chuyển đổi LocalDate về dạng chuỗi dd/MM/yyyy
            String line = String.format("%s, %s, %s, %d, %s",
                    proj.getProjectId(),
                    proj.getDevId(),
                    proj.getProjectName(),
                    proj.getDurationMonths(),
                    proj.getStartDate().format(formatter));
            projLines.add(line);
        }

        // 3. GHI XUỐNG FILE THÔNG QUA FILEUTILS
        boolean isDevSaved = FileUtils.writeFile(devFilePath, devLines);
        boolean isProjSaved = FileUtils.writeFile(projFilePath, projLines);

        // 4. KIỂM TRA KẾT QUẢ VÀ HIỂN THỊ THÔNG BÁO CHI TIẾT
        if (isDevSaved && isProjSaved) {
            this.saved = true; // Cập nhật cờ hiệu thành true (Đã lưu)
            System.out.println("========================================");
            System.out.println("Data saved to files successfully!");
            // In ra số lượng record thực tế đã ghi xuống file
            System.out.println("-> Total Developers saved: " + devLines.size());
            System.out.println("-> Total Projects saved: " + projLines.size());
            System.out.println("========================================");
        } else {
            System.out.println("Error: Failed to save data to files. Please check file permissions.");
        }
    }
}
