package org.tasktracker.manager;

import java.util.ArrayList;
import java.util.HashMap;
import org.tasktracker.model.Task;
import org.tasktracker.model.Employee;
import org.tasktracker.model.Status;
import org.tasktracker.exceptions.DuplicateTaskIdException;
import org.tasktracker.exceptions.TaskNotFoundException;

public class TaskManager {

    private HashMap<String, Task> tasks;
    private EmployeeManager employeeManager;

    public TaskManager(EmployeeManager employeeManager) {
        this.tasks = new HashMap<>();
        this.employeeManager = employeeManager;
    }

    public boolean isValidTaskId(String taskId) {
        if(taskId == null) {
            return false;
        }else if (!taskId.matches("^[A-Z]{2}-\\d{3}$")) {
            return false;
        }
        return true;
    }
    public boolean taskExists(String taskId) {
        return tasks.containsKey(taskId);
    }

    public void createTask(Task task) {

        if (task.getTaskId() == null || task.getTaskId().isBlank()) {
            throw new IllegalArgumentException("Task ID cannot be empty.");
        }

        if (!isValidTaskId(task.getTaskId())) {
            throw new IllegalArgumentException(
                "Task ID must be in the format XX-123."
            );
        }

        if (taskExists(task.getTaskId())) {
            throw new DuplicateTaskIdException(
                "Task with ID " + task.getTaskId() + " already exists."
            );
        }

        tasks.put(task.getTaskId(), task);
    }

    public Task getTaskById(String taskId) {
        if (!taskExists(taskId)) {
            throw new TaskNotFoundException("Task with ID " + taskId + " does not exist");
        }
        return tasks.get(taskId);
    }

    public void assignTask(String taskId, String employeeId) {
        Task task = getTaskById(taskId);

        if (task.getAssignedEmployee() != null) {
            throw new IllegalStateException(
                "Task is already assigned to an employee."
            );
        }

        Employee employee = employeeManager.getEmployeeById(employeeId);
        task.setAssignedEmployee(employee);
    }

    public void updateTaskStatus(String taskId, Status status) {
        Task task = getTaskById(taskId);
        task.setTaskStatus(status);
    }


    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }


}
