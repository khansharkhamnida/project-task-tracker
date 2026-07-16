package org.tasktracker.manager;

import java.util.ArrayList;
import java.util.HashMap;
import org.tasktracker.exceptions.EmployeeNotFoundException;
import org.tasktracker.model.Employee;

public class EmployeeManager {
    private HashMap<String, Employee> employees;

    public EmployeeManager() {
        this.employees = new HashMap<>();
    }

    public void validateEmployeeExists(String employeeId) {
        if(employeeId == null) {
            throw new EmployeeNotFoundException("Employee Id cannot be null");
        }
        if(!employees.containsKey(employeeId)) {
            throw new EmployeeNotFoundException("Employee with ID " + employeeId + " does not exist");
        }
    }

    public void addEmployee(Employee employee) {
        if(employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty");
        }
        if(employees.containsKey(employee.getEmployeeId())) {
            throw new IllegalArgumentException("Employee with ID " + employee.getEmployeeId() + " already exists");
        }
        employees.put(employee.getEmployeeId(), employee);
    }

    public Employee getEmployeeById(String employeeId) {
        validateEmployeeExists(employeeId);
        return employees.get(employeeId);
    }

    public ArrayList<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }
    
}
