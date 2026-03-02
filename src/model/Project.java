package model;

import java.time.LocalDate;

public class Project {

    private String projectId;
    private String devId;
    private String projectName;
    private int durationMonths;
    private LocalDate startDate;

    //constructor
    public Project() {
    }

    public Project(String projectId, String devId, String projectName, int durationMonths, LocalDate startDate) {
        this.projectId = projectId;
        this.devId = devId;
        this.projectName = projectName;
        this.durationMonths = durationMonths;
        this.startDate = startDate;
    }

    //Getter and setter
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    //toString
    @Override
    public String toString(){
        return String.format("%s, %s, %s, %d, %s", projectId, devId, projectName, durationMonths, startDate);
    }
}
