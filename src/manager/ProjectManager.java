package manager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Project;
import java.util.ArrayList;
import model.Developer;
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
        
        List<String> projLines = FileUtils.readFile(projFilePath);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (String line : projLines) {
            try {
                String [] tokens = line.split(",");
                if (tokens.length >= 5) {
                    String pId = tokens[0].trim();
                    String dId = tokens[1].trim();
                    String pName = tokens[2].trim();
                    int duration = Integer.parseInt(tokens[3].trim());
                    LocalDate startDate = LocalDate.parse(tokens[4].trim(), dtf);
                    
                    projList.add(new Project(pId, dId, pName, duration, startDate));
                }
            } catch (Exception e) {
                System.out.println("Error parsing projects line: " + line);
            }
        }
        
        this.saved = true;
        System.out.println("Data loaded successfully from files!");
        System.out.println("Total projects loaded: " + projList.size());
    }

    //findById
    public Project findById(String projId) {
        for (Project proj : projList) {
            if (proj.getProjectId().equalsIgnoreCase(projId)) {
                return proj;
            }
        }
        return null;
    }

    //Add new project
    public boolean addNewProject(DevManager dm, Project proj) {
        if (findById(proj.getProjectId()) != null) {
            System.out.println("Project ID already exists");
            return false;
        }

        Developer dev = dm.findById(proj.getDevId());
        if (dev == null) {
            System.out.println("Developer ID does not exists");
            return false;
        }

        projList.add(proj);
        System.out.println("Add new project successfully");
        saved = false;
        return true;
    }

    //Liệt kê danh sách developer có project (listProjectByDeveloper)
    public void listProjectByDeveloper(DevManager dm) {
        System.out.println("--- LIST PROJECTS GROUPED BY DEVELOPER ---");

        List<Developer> devList = dm.getDevList();

        if (devList.isEmpty()) {
            System.out.println("No developers found");
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Developer dev : devList) {
            System.out.println("Developer: [" + dev.getDevId() + "] - " + dev.getFullName());

            boolean hasProject = false;
            boolean isHeaderPrinted = false;

            for (Project proj : projList) {
                if (proj.getDevId().equalsIgnoreCase(dev.getDevId())) {
                    hasProject = true;

                    if (!isHeaderPrinted) {
                        System.out.println("+------------+--------------------------------+------------+------------+");
                        System.out.println("| Project ID |          Project Name          |  Duration  | Start Date |");
                        System.out.println("+------------+--------------------------------+------------+------------+");
                        isHeaderPrinted = true;
                    }
                    
                    System.out.printf("| %-10s | %-30s | %-10s | %-10s |%n",
                            proj.getProjectId(),
                            proj.getProjectName(),
                            proj.getDurationMonths() + " months",
                            proj.getStartDate().format(dtf));
                }
            }
            
            if (hasProject) {
                System.out.println("+------------+--------------------------------+------------+------------+");
            } 
            else {
                System.out.println("No project assigned");
            }
        }
    }

    //Tính tổng kinh nghiệm của 
    public void calculateTotalExperience(String devId, DevManager dm) {
        System.out.println("--- CALCULATE TOTAL EXPERIENCE ---");
        Developer dev = dm.findById(devId);
        if (dev == null) {
            System.out.println("Developer ID does not exist");
            return;
        }
        
        System.out.println("Developer: [" + dev.getDevId() + "] - " + dev.getFullName());
        
        int totalMonths = 0;
        boolean hasProject = false;
        
        for (Project proj : projList) {
            if (proj.getDevId().equalsIgnoreCase(devId)) {
                totalMonths += proj.getDurationMonths();
                hasProject = true;
            }
        }
        
        if (hasProject) {
            System.out.println("Total experience: " + totalMonths + " months");
            
            int years = totalMonths / 12;
            int remainingMonths = totalMonths % 12;
            if (years > 0) {
                System.out.println("-> Equivalent to " + years + " year(s) and " + remainingMonths + " month(s)");
            }
        }
        else {
            System.out.println("Developer had not participate in any project. Total experience 0 months");
        }
    }

    public boolean hasProjectByDevId(String devId) {
        for (Project proj : projList) {
            if (proj.getDevId().equalsIgnoreCase(devId)) {
                return true;
            }
        }
        return false;
    }

    //saveToFile of projects.txt
    public void saveToFile() {
        List<String> projLines = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Project proj : projList) {
            String line = String.format("%s, %s, %s, %d, %s",
                    proj.getProjectId(),
                    proj.getDevId(),
                    proj.getProjectName(),
                    proj.getDurationMonths(),
                    proj.getStartDate().format(dtf));
            projLines.add(line);
        }
        
        boolean isProjectSaved = FileUtils.writeFile(projFilePath, projLines);
        
        if (isProjectSaved) {
            this.saved = true;
            System.out.println("Data saved to files successfully!");
            System.out.println("Total projects saved: " + projLines.size());
        }
        else {
            System.out.println("Error: Failed to save date to files.");
        }
    }
}
