package org.tasktracker.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private String taskId;
    private String name;
    private Status status;
    private Priority priority;
    private Employee assignedEmployee;
    private LocalDate dueDate;

    public Task(String taskId, String name, Status status, Priority priority, Employee assignedEmployee, LocalDate dueDate) {
        this.taskId = taskId;
        this.name = name;
        this.status = status;
        this.priority = priority;
        this.assignedEmployee = assignedEmployee;
        this.dueDate = dueDate;
    }

    public String getTaskId() {
        return taskId;
    }
    
    public String getTaskName() {
        return name;
    }
    
    public void setTaskName(String name) {
        this.name = name;
    }

    public Status getTaskStatus() {
        return status;
    }

    public void setTaskStatus(Status status) {
        this.status = status;
    }

    public Priority getTaskPriority() {
        return priority;
    }

    public void setTaskPriority(Priority priority) {
        this.priority = priority;
    }

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }
    
    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return "Task ID: " + taskId + "\n"
                + "Task Name: " + name + "\n"
                + "Status: " + status + "\n"
                + "Priority: " + priority + "\n"
                + "Assigned Employee: "
                + (assignedEmployee == null ? "None" : assignedEmployee.getEmployeeName())
                + "\n"
                + "Due Date: " + dueDate.format(formatter);
    }

}

