package tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.Developer;
import model.Project;

public class Inputter {

    private Scanner ndl;

    //constructor
    public Inputter() {
        this.ndl = new Scanner(System.in);
    }

    //Input String
    public String getString(String mess) {
        System.out.print(mess);
        return ndl.nextLine().trim();
    }

    //Input Integer
    public int getInt(String mess) {
        int kq = 0;
        String temp = getString(mess);
        if (Validation.isValid(temp, Validation.INTEGER_VALID)) {
            kq = Integer.parseInt(temp);
        }
        return kq;
    }

    public int getIntOptional(String mess) {
        String temp = getString(mess).trim();
        if (temp.isEmpty()) {
            return Integer.MIN_VALUE;
        }
        if (Validation.isValid(temp, Validation.INTEGER_VALID)) {
            return Integer.parseInt(temp);
        }
        System.out.println("Invalid number!");
        return getIntOptional(mess);
    }

    //Input Double
    public double getDouble(String mess) {
        double kq = 0.0;
        String temp = getString(mess);
        if (Validation.isValid(temp, Validation.DOUBLE_VALID)) {
            kq = Double.parseDouble(temp);
        }
        return kq;
    }

    public double getDoubleOptional(String mess) {
        String temp = getString(mess).trim();
        if (temp.isEmpty()) {
            return Double.NEGATIVE_INFINITY;
        }
        if (Validation.isValid(temp, Validation.DOUBLE_VALID)) {
            return Double.parseDouble(temp);
        }
        System.out.println("Invalid number!");
        return getDoubleOptional(mess);
    }

    //Check data and loop if data is invalid
    public String inputAndLoop(String mess, String pattern, boolean isLoop) {
        String result;
        boolean isInvalid;

        do {
            result = getString(mess).trim();
            if (!isLoop && result.isEmpty()) {
                return result;
            }

            isInvalid = !Validation.isValid(result, pattern);

            if (isInvalid && isLoop) {
                System.out.println("Data is invalid! Re-enter...");
            }
        } while (isLoop && isInvalid);
        return result;
    }

    //Add new Developer
    public Developer addNewDevloper() {
        Developer dev = new Developer();
        dev.setDevId(inputAndLoop("Enter dev ID: ", Validation.DEV_ID_VALID, true));
        dev.setFullName(inputAndLoop("Enter full name: ", Validation.FULL_NAME_VALID, true));

        java.util.List<String> languages = new java.util.ArrayList<>();
        System.out.println("--- Enter Programming Languages ---");
        String lang = inputAndLoop("Enter programming language: ", Validation.PROGRAM_LANGUAGE_VALID, true);
        languages.add(lang);

        String continueInput;
        do {
            continueInput = getString("Do you want to add more language? (Y/N): ");
            if (continueInput.equalsIgnoreCase("Y")) {
                lang = inputAndLoop("Enter programming language: ", Validation.PROGRAM_LANGUAGE_VALID, true);
                languages.add(lang);
            }
        } while (continueInput.equalsIgnoreCase("Y"));

        dev.setProgramLanguages(languages);
        dev.setSalary(getInt("Enter salary (>= 1000 USD): "));
        return dev;
    }
    
    //Add new Project
    public Project addNewProject() {
        Project proj = new Project();
        proj.setProjectId(inputAndLoop("Enter project ID: ", Validation.PROJECT_ID_VALID, true));
        proj.setDevId(inputAndLoop("Enter developer ID: ", Validation.DEV_ID_VALID, true));
        proj.setDurationMonths(getInt("Enter duration (in monts, > 0): "));
        proj.setStartDate(getDate("Enter start date (dd/MM/yyyy): "));
        proj.setProjectName(inputAndLoop("Enter project name: ", Validation.PROJECT_NAME_VALID, true));
        return proj;
    }

    //Format days/months/years
    public LocalDate getDate(String temp) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate resultDate = null;
        boolean isValid = false;

        do {
            try {
                String dateStr = getString(temp);
                resultDate = LocalDate.parse(dateStr, dtf);
                isValid = true;
            } catch (Exception e) {
                System.out.println("Error: Invalid date format! Please enter date as dd/MM/yyyy (e.g., 01/12/2026).");
            }
        } while (!isValid);
        return resultDate;
    }
}
