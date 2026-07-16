package org.tasktracker;

import org.tasktracker.model.Status;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.tasktracker.exceptions.DuplicateTaskIdException;
import org.tasktracker.exceptions.EmployeeNotFoundException;
import org.tasktracker.exceptions.InvalidStatusException;
import org.tasktracker.exceptions.TaskNotFoundException;
import org.tasktracker.manager.EmployeeManager;
import org.tasktracker.manager.LoginManager;
import org.tasktracker.manager.TaskManager;
import org.tasktracker.model.Employee;
import org.tasktracker.model.Priority;
import org.tasktracker.model.Task;

public class TaskTracker {

    static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static Status parseStatus(String input) {
        try {
            return Status.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException(
                    "Invalid status. Valid statuses are: OPEN, IN_PROGRESS, COMPLETED.");
        }
    }

    public static void main(String[] args) {

        LoginManager loginManager = new LoginManager();
        EmployeeManager employeeManager = new EmployeeManager();
        TaskManager taskManager = new TaskManager(employeeManager);

        boolean isLoggedIn = false;
        String username = "";

        while (!isLoggedIn) {

            System.out.println("\n====================================");
            System.out.println("         PROJECT TASK TRACKER");
            System.out.println("====================================");
            System.out.println("Please log in to continue.\n");

            System.out.print("Username (or type 'exit'): ");
            username = scanner.nextLine();

            if (username.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                scanner.close();
                return;
            }

            System.out.print("Password: ");
            String password = scanner.nextLine();

            isLoggedIn = loginManager.login(username, password);

            if (!isLoggedIn) {
                printError("Invalid username or password. Please try again.");
            }
        }

        printLoginSuccess(username);

        while (isLoggedIn) {

            printMenu();

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:

                    addEmployee(employeeManager);
                    break;

                case 2:

                    searchAndListEmployee(employeeManager);
                    break;

                case 3:

                    createTask(taskManager);
                    break;

                case 4:

                    searchAndAssignTask(employeeManager, taskManager);
                    break;

                case 5:

                    updateTaskStatus(taskManager);
                    break;

                case 6:

                    isLoggedIn = false;

                    printLogoutMessage();

                    break;

                default:

                    printError("Invalid option. Please select a valid option from the menu.");
                    break;
            }
        }

        scanner.close();
    }

    private static void printLogoutMessage() {
        System.out.println("\n========================");
        System.out.println("You have been logged out.");
        System.out.println("Goodbye!");
        System.out.println("========================");
    }

    private static void printLoginSuccess(String username) {
        System.out.println("\n========================");
        System.out.println("[SUCCESS] Login successful!");
        System.out.println("Welcome, " + username + "!");
        System.out.println("========================");
    }

    private static void updateTaskStatus(TaskManager taskManager) {
        System.out.println("\n========== UPDATE TASK STATUS ==========");

        if (!hasTasks(taskManager)) {
            return;
        }

        displayTaskSummary(taskManager);

        while (true) {

            System.out.print("\nTask ID (or 0 to cancel): ");
            String updateTaskId = scanner.nextLine();

            if (updateTaskId.equals("0")) {
                return;
            }

            try {

                taskManager.getTaskById(updateTaskId);

                while (true) {

                    System.out.print(
                            "New Status (OPEN, IN_PROGRESS, COMPLETED): ");

                    String newTaskStatus = scanner.nextLine();

                    try {

                        Status status = parseStatus(newTaskStatus);

                        taskManager.updateTaskStatus(updateTaskId,
                                status);

                        printSuccess("Task status updated successfully!");
                        System.out.println("\n===== UPDATED TASK DETAILS =====");
                        System.out.println(taskManager.getTaskById(updateTaskId));

                        return;

                    } catch (InvalidStatusException e) {
                        printError(e.getMessage());
                    }
                }

            } catch (TaskNotFoundException e) {

                printError(e.getMessage());
            }
        }
    }

    private static boolean hasTasks(TaskManager taskManager) {
        if (taskManager.getAllTasks().isEmpty()) {
            printError("No tasks available.");
            return false;
        }
        return true;
    }

    private static void displayTaskSummary(TaskManager taskManager) {
        System.out.println("\nAvailable Tasks:");

        List<Task> tasks = new ArrayList<>(taskManager.getAllTasks());

        tasks.sort(Comparator.comparing(Task::getDueDate));

        for (Task task : tasks) {
            System.out.println(task.getTaskId()
                    + " - "
                    + task.getTaskName()
                    + " ("
                    + task.getDueDate()
                    + ")");
        }
    }

    private static void searchAndAssignTask(EmployeeManager employeeManager, TaskManager taskManager) {
        System.out.println("\n========== ASSIGN TASK ==========");

        if(!hasTasks(taskManager)) {
            return;
        }

        displayTaskSummary(taskManager);

        System.out.print("\nTask ID (or 0 to cancel): ");
        String searchTaskId = scanner.nextLine();

        if (searchTaskId.equals("0")) {
            return;
        }

        try {

            Task foundTask = taskManager.getTaskById(searchTaskId);

            System.out.println("\n===== TASK DETAILS =====");
            System.out.println(foundTask);

        } catch (TaskNotFoundException e) {

            printError(e.getMessage());
            return;
        }

        if(!hasEmployees(employeeManager, "No employees has been assigned to this task.")) {
            return;
        }

        displayEmployeeSummary(employeeManager);

        while (true) {

            System.out.print("\nEmployee ID (or 0 to cancel): ");
            String assignedEmployeeId = scanner.nextLine();

            if (assignedEmployeeId.equals("0")) {
                System.out.println("Assignment cancelled.");
                return;
            }

            try {

                taskManager.assignTask(searchTaskId, assignedEmployeeId);

                printSuccess("Task assigned successfully!");
                System.out.println("\n===== UPDATED TASK DETAILS =====");
                System.out.println(taskManager.getTaskById(searchTaskId));

                return;

            } catch (EmployeeNotFoundException e) {

                printError(e.getMessage());
            }
        }
    }

    private static void displayEmployeeSummary(EmployeeManager employeeManager) {
        System.out.println("\nAvailable Employees:");

        employeeManager.getAllEmployees().forEach(employee ->
                System.out.println(employee.getEmployeeId()
                        + " - "
                        + employee.getEmployeeName()));
    }

    private static void createTask(TaskManager taskManager) {
        System.out.println("\n========== CREATE TASK ==========");

        System.out.print("Task ID (e.g. AB-123): ");
        String taskId = scanner.nextLine();

        System.out.print("Task Name: ");
        String taskName = scanner.nextLine();

        Priority priority = readPriority();

        LocalDate parsedDate = readDueDate();

        Task task =
                new Task(taskId,
                        taskName,
                        Status.OPEN,
                        priority,
                        null,
                        parsedDate);

        try {

            taskManager.createTask(task);

            printSuccess("Task created successfully!");

        } catch (DuplicateTaskIdException | IllegalArgumentException e) {

            printError(e.getMessage());
        }
    }

    private static Priority readPriority() {
        Priority priority;

        while (true) {

            System.out.print("Priority (LOW, MEDIUM, HIGH): ");
            String taskPriority = scanner.nextLine();

            try {

                priority =
                        Priority.valueOf(taskPriority.toUpperCase());
                break;

            } catch (IllegalArgumentException e) {

                printError("Invalid priority.");
            }
        }
        return priority;
    }

    private static LocalDate readDueDate() {
        LocalDate parsedDate;

        while (true) {

            System.out.print("Due Date (DD-MM-YYYY, e.g. 01-05-2026): ");
            String dueDate = scanner.nextLine();

            try {

                parsedDate =
                        LocalDate.parse(dueDate, DATE_FORMAT);

                break;

            } catch (DateTimeParseException e) {

                printError("Invalid date format.");
            }
        }
        return parsedDate;
    }

    private static void searchAndListEmployee(EmployeeManager employeeManager) {
        System.out.println("\n========== SEARCH EMPLOYEE ==========");

        if (!hasEmployees(employeeManager, "No employees found")) {
            return;
        }

        displayEmployeeSummary(employeeManager);

        System.out.print("\nEmployee ID (leave blank to list all): ");
        String searchEmployeeId = scanner.nextLine();

        if (searchEmployeeId.isBlank()) {

            System.out.println("\n===== ALL EMPLOYEES =====");

            for (Employee employee : employeeManager.getAllEmployees()) {
                System.out.println(employee);
                System.out.println();
            }

        } else {

            try {

                Employee foundEmployee =
                        employeeManager.getEmployeeById(searchEmployeeId);

                System.out.println("\n===== EMPLOYEE DETAILS =====");
                System.out.println(foundEmployee);

            } catch (EmployeeNotFoundException e) {

                printError(e.getMessage());
            }
        }
    }

    private static boolean hasEmployees(EmployeeManager employeeManager, String message) {
        if (employeeManager.getAllEmployees().isEmpty()) {
            printError(message);
            return false;
        }
        return true;
    }

    private static void addEmployee(EmployeeManager employeeManager) {
        System.out.println("\n========== ADD EMPLOYEE ==========");

        System.out.print("Employee ID: ");
        String employeeId = scanner.nextLine();

        System.out.print("Employee Name: ");
        String employeeName = scanner.nextLine();

        System.out.print("Department: ");
        String employeeDepartment = scanner.nextLine();

        try {

            employeeManager.addEmployee(
                    new Employee(employeeId,
                            employeeName,
                            employeeDepartment));

            printSuccess("Employee added successfully!");

        } catch (IllegalArgumentException e) {

            printError(e.getMessage());
        }
    }

    private static void printError(String message) {
        System.out.println("\n========================");
        System.out.println("[ERROR] " + message);
        System.out.println("========================");
    }

    private static void printSuccess(String message) {
        System.out.println("\n========================");
        System.out.println("[SUCCESS] " + message);
        System.out.println("========================");
    }

    private static void printMenu() {
        System.out.println("\n====================================");
        System.out.println("             MAIN MENU");
        System.out.println("====================================");
        System.out.println("1. Add Employee");
        System.out.println("2. Search/List Employees");
        System.out.println("3. Create Task");
        System.out.println("4. Search & Assign Task");
        System.out.println("5. Update Task Status");
        System.out.println("6. Exit");
        System.out.println("====================================");

        System.out.print("Select an option: ");
    }
}