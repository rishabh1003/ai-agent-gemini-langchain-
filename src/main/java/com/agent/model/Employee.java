package com.agent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private String id;
    private String name;
    private String department;
    private int leaveBalance;

    // Default constructor required by JPA
    public Employee() {}

    public Employee(String id, String name, String department, int leaveBalance) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.leaveBalance = leaveBalance;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public int getLeaveBalance() { return leaveBalance; }
}