package com.jlakshmi.ems.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jlakshmi.ems.entity.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String>,EmployeeRepositoryCustom{
	
}
