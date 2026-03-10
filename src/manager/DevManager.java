package manager;

import java.util.List;
import model.Developer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import tools.FileUtils;

public class DevManager {

    private List<Developer> devList;
    private String devFilePath;
    private boolean saved;

    //constructor
    public DevManager() {
        devList = new ArrayList<>();
        this.saved = true;
        this.devFilePath = "developers.txt";
        this.readFromFile();
    }

    //Getter and setter
    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public List<Developer> getDevList() {
        return this.devList;
    }

    //Đọc dữ liệu
    public void readFromFile() {
        devList.clear(); 

        List<String> devLines = FileUtils.readFile(devFilePath);
        for (String line : devLines) {
            try {
                String[] parts = line.split("\\[|\\]");

                String[] info = parts[0].split(",");
                String id = info[0].trim();
                String name = info[1].trim();

                List<String> langs = new ArrayList<>();
                if (!parts[1].trim().isEmpty()) {
                    for (String lang : parts[1].split(",")) {
                        langs.add(lang.trim());
                    }
                }

                int salary = Integer.parseInt(parts[2].replace(",", "").trim());

                // 4. Thêm vào danh sách
                devList.add(new Developer(id, name, langs, salary));

            } catch (Exception e) {
                System.out.println("Error parsing developer line: " + line);
            }
        }

        this.saved = true;
        System.out.println("Data loaded successfully from files!");
        System.out.println("Total Developers loaded: " + devList.size());
    }

    //In ra màn hình toàn bộ developer (listAllDevelopers)
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

    //Kiểm tra devId có trùng không (findById)
    public Developer findById(String devId) {
        for (Developer dev : devList) {
            if (dev.getDevId().equalsIgnoreCase(devId)) {
                return dev;
            }
        }
        return null;
    }

    //Thêm 1 developer mới (addNewDeveloper)
    public boolean addNewDeveloper(Developer dev) {
        if (findById(dev.getDevId()) != null) {
            System.out.println("Developer ID already exists");
            return false;
        }

        if (dev.getSalary() < 1000) {
            System.out.println("Salary must be greater than 1000 USD");
            return false;
        }

        System.out.println("Add new developer successfully");
        saved = false;
        devList.add(dev);
        return true;
    }

    //Tìm developer theo devId (searchDeveloper)
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
            System.out.println("Developer ID does not exist");
        }
    }

    //Cập nhật Salary của developer theo devId (updateSalary)
    public boolean updateSalary(String devId, int newSalary) {
        Developer dev = findById(devId);
        if (dev == null) {
            System.out.println("Developer ID does not exist");
            return false;
        }

        if (newSalary < 1000) {
            System.out.println("Salary must be greater than 1000 USD");
            return false;
        }

        dev.setSalary(newSalary);
        saved = false;
        System.out.println("Update developer's salary successfully");
        return true;
    }

    //Tìm developer theo ngôn ngữ lập trình (listByLanguage)
    public void searchByLanguage(String language) {
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

    //Xóa developer theo ID
    public boolean removeDeveloper(String removeId, ProjectManager pm) {
        Developer removeDev = findById(removeId);
        if (removeDev == null) {
            System.out.println("Developer ID does not exist");
            return false;
        }

        if (pm.hasProjectByDevId(removeId)) {
            System.out.println("Cannot delete: Developer is assigned to projects");
            return false;
        }

        devList.remove(removeDev);
        saved = false;
        System.out.println("Remove developer successfully");
        return true;
    }

    //Sắp xếp developer theo Salary tăng dần
    public void sortDeveloperBySalary() {
        System.out.println("--- SORT DEVELOPERS BY SALARY ---");

        if (devList.isEmpty()) {
            System.out.println("No developers found");
            return;
        }

        Collections.sort(devList, (Developer o1, Developer o2) -> {
            int salaryComparison = Integer.compare(o1.getSalary(), o2.getSalary());

            if (salaryComparison == 0) {
                return o1.getFullName().compareToIgnoreCase(o2.getFullName());
            }

            return salaryComparison;
        });

        saved = false;
        System.out.println("Developer sorted successfully");
        listAllDevelopers();
    }

    //Lưu dữ liệu
    public void saveToFile() {
        List<String> devLines = new ArrayList<>();
        for (Developer dev : devList) {
            String line = String.format("%s, %s, %s, %d",
                    dev.getDevId(),
                    dev.getFullName(),
                    dev.getProgramLanguages().toString(),
                    dev.getSalary());
            devLines.add(line);
        }

        boolean isDevSaved = FileUtils.writeFile(devFilePath, devLines);

        if (isDevSaved) {
            this.saved = true;
            System.out.println("========================================");
            System.out.println("Data saved to files successfully!");
            System.out.println("========================================");

            System.out.println("-> Total Developers saved: " + devLines.size());
            System.out.println("========================================");
        } else {
            System.out.println("Error: Failed to save data to files. Please check file permissions.");
        }
    }
}
