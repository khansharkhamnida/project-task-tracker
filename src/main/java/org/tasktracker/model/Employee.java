package org.tasktracker.model;
public class Employee {
    private String employeeId;
    private String employeeName;
    private String employeeDepartment;

    public Employee(String employeeId, String employeeName, String employeeDepartment) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeDepartment = employeeDepartment;
    }
    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    @Override
    public String toString() {
        return "Employee ID: " + employeeId +
                "\nEmployee Name: " + employeeName +
                "\nDepartment: " + employeeDepartment;
    }
}
