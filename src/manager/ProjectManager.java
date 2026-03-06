package manager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Project;
import java.util.ArrayList;
import tools.FileUtils;

public class ProjectManager {

    private List<Project> projList;
    private String projFilePath;
    private boolean saved;

    //constructor
    public ProjectManager() {
        projList = new ArrayList<>();
        this.saved = true;
        this.projFilePath = "projects.txt";
        this.readFromFile();
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public List<Project> getProjList() {
        return this.projList;
    }

    //ReadFromFile of projects.txt
    public void readFromFile() {
        projList.clear();

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

        this.saved = true;
        System.out.println("Data loaded successfully from files!");
        System.out.println("Total Projects loaded: " + projList.size());
    }

    //saveToFile of projects.txt
    public void saveToFile() {
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
        
        boolean isProjSaved = FileUtils.writeFile(projFilePath, projLines);
        
        if (isProjSaved) {
            this.saved = true; // Cập nhật cờ hiệu thành true (Đã lưu)
            System.out.println("========================================");
            System.out.println("Data saved to files successfully!");
            // In ra số lượng record thực tế đã ghi xuống file
            System.out.println("-> Total Projects saved: " + projLines.size());
            System.out.println("========================================");
        } else {
            System.out.println("Error: Failed to save data to files. Please check file permissions.");
        }
    }
}
