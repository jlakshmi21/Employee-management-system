package com.jlakshmi.ems.repository;

import com.jlakshmi.ems.entity.Employee;

public interface EmployeeRepositoryCustom {
	Employee findBypanNumber(String phoneNumber);
	Employee findByemail(String email);
	Employee findByphoneNumber(String phoneNumber);
	
}
