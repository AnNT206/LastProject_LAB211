
package model;

import java.util.List;

public class Developer {
    private String devId;
    private String fullName;
    private List<String> programLanguages;
    private int salary;

    //constructor
    public Developer() {
    }

    public Developer(String devId, String fullName, List<String> programLanguages, int salary) {
        this.devId = devId;
        this.fullName = fullName;
        this.programLanguages = programLanguages;
        this.salary = salary;
    }

    //Getter and setter
    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getProgramLanguages() {
        return programLanguages;
    }

    public void setProgramLanguages(List<String> programLanguages) {
        this.programLanguages = programLanguages;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    
    //toString
    @Override
    public String toString(){
        return String.format("%s, %s, %s, %d", devId, fullName, programLanguages, salary);
    }
            
}
