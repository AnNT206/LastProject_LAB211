package dispatcher;

import manager.DevManager;
import manager.ProjectManager;
import model.Developer;
import model.Project;
import tools.Inputter;

public class Main {

    public static void main(String[] args) {
        // Khởi tạo đối tượng quản lý và công cụ nhập liệu
        DevManager dm = new DevManager();
        ProjectManager pm = new ProjectManager();
        Inputter input = new Inputter();

        int choice;
        do {
            // Hiển thị Menu 
            System.out.println("\n=======================================================");
            System.out.println("          SOFTWARE DEVELOPER MANAGEMENT SYSTEM         ");
            System.out.println("=======================================================");
            System.out.println("1. List all Developers");
            System.out.println("2. Add a new Developer");
            System.out.println("3. Search for a Developer by ID");
            System.out.println("4. Update a Developer's salary by ID");
            System.out.println("5. List all Developers by Language");
            System.out.println("6. Add a new Project");
            System.out.println("7. List all Projects by Developer (Grouped)");
            System.out.println("8. Calculate Total Experience by Dev ID");
            System.out.println("9. Remove a Developer by ID");
            System.out.println("10. Sort Developers by Salary");
            System.out.println("11. Save data to files");
            System.out.println("12. Quit program");
            System.out.println("=======================================================");

            // Lấy lựa chọn của người dùng (từ 1 đến 12)
            choice = input.getInt("Enter your choice (1-12): ");

            switch (choice) {
                case 1:
                    dm.listAllDevelopers();
                    break;
                case 2:
                    Developer dev = input.addNewDevloper();
                    dm.addNewDeveloper(dev);
                    break;
                case 3:
                    String searchId = input.getString("Enter developer ID to search: ");
                    dm.searchDeveloper(searchId);
                    break;
                case 4:
                    String updateId = input.getString("Enter develpoer ID to update salary: ");
                    int newSalary = input.getInt("Enter new salary: ");
                    dm.updateSalary(updateId, newSalary);
                    break;
                case 5:
                    String searchLang = input.getString("Enter a programming language to search: ");
                    dm.listByLanguage(searchLang);
                    break;
                case 6:
                    Project proj = input.addNewProject();
                    pm.addNewProject(dm, proj);
                    break;
                case 7:
                    pm.listProjectByDeveloper(dm);
                    break;
                case 8: {
                    String devId = input.getString("Enter developer ID to calculate: ");
                    pm.calculateTotalExperience(devId, dm);
                    break;
                }
                case 9:
                    String removeId = input.getString("Enter developer ID to remove: ");
                    dm.removeDeveloper(removeId, pm);
                    break;
                case 10:
                    dm.sortDeveloperBySalary();
                    break;
                case 11:
                    dm.saveToFile();
                    pm.saveToFile();
                    break;
                case 12:
                    if (!dm.isSaved()) {
                        String ans = input.getString("Do you want to save the changes before exiting? (Y/N): ");
                        if (ans.equalsIgnoreCase("Y")) {
                            dm.saveToFile();
                            pm.saveToFile();
                        }
                    }
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please select a number from 1 to 12.");
            }
        } while (choice != 12);
    }
}
