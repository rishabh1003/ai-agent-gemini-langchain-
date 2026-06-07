package com.agent.repository;

import com.agent.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    // Spring Data JPA automatically provides standard CRUD operations.
    // The generic types <Employee, String> indicate the Entity and its Primary Key type.
}