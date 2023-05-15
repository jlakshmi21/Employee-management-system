package com.jlakshmi.ems.service;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import com.jlakshmi.ems.entity.Employee;
import com.jlakshmi.ems.repository.EmployeeRepositoryCustom;

public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    @Autowired
    private MongoTemplate mongoTemplate;

	@Override
	public Employee findBypanNumber(String panNumber) {
		 Query query = new Query(Criteria.where("panNumber").is(panNumber));
	        return mongoTemplate.findOne(query, Employee.class);
	}
	@Override
	public Employee findByemail(String email) {
		 Query query = new Query(Criteria.where("email").is(email));
	        return mongoTemplate.findOne(query, Employee.class);
	}
	@Override
	public Employee findByphoneNumber(String phoneNumber) {
		 Query query = new Query(Criteria.where("phoneNumber").is(phoneNumber));
	        return mongoTemplate.findOne(query, Employee.class);
	}
    
}
