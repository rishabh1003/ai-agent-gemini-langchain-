package com.agent.tools;

import com.agent.model.Employee;
import com.agent.repository.EmployeeRepository;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeDatabaseTool {

    // Inject the live database connection
    private final EmployeeRepository employeeRepository;

    public EmployeeDatabaseTool(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @SuppressWarnings("null")
    @Tool("Fetches employee details and current leave balance from the HR database using their precise Employee ID.")
    public String getEmployeeDetails(
            @P("The exact alphanumeric employee ID, strictly formatted like 'EMP-1001'") String employeeId) {
        
        System.out.println("Invoked Live DB Tool: getEmployeeDetails for ID: '" + employeeId + "'");

        // Query PostgreSQL
        Optional<Employee> record = employeeRepository.findById(employeeId.toUpperCase());

        if (record.isEmpty()) {
            return "Database Error: No employee found with ID " + employeeId;
        }

        // Extract the entity
        Employee emp = record.get();

        // Return formatted string to the LLM
        return String.format(
                "Database Record Found -> Name: %s | Department: %s | Available Leave Balance: %d days",
                emp.getName(), emp.getDepartment(), emp.getLeaveBalance()
        );
    }
}
