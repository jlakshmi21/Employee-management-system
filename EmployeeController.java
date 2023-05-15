package com.jlakshmi.ems.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jlakshmi.ems.entity.Employee;
import com.jlakshmi.ems.repository.EmployeeRepository;


@Controller
public class EmployeeController 
{
	@Autowired
	EmployeeRepository employeeRepository;
	@GetMapping("/")
	public String index(Model model)
	{
		model.addAttribute("listEmployees",employeeRepository.findAll());
		return "index";
	}
	@GetMapping("/showNewEmployeeForm")
	public String shownewEmpForm(Model model) 
	{
		Employee employee = new Employee();

		// We can use this attribute "employee" to perform server-side rendering of the HTML with using Thymeleaf.
		// We set employee object as "employee"
		model.addAttribute("employee", employee);

		//	shows the new_employee.html template:
		return "employee_form";
	}
	@PostMapping("/saveEmployee")
	public String submitEmp(@ModelAttribute("employee") Employee employee,Model model)
	{
		String message="";
		boolean edit=false;
		Employee panemp =employeeRepository.findBypanNumber(employee.getPanNumber());
		Employee emailemp =employeeRepository.findByemail(employee.getEmail());
		Employee phoneemp =employeeRepository.findByphoneNumber(employee.getPhoneNumber());
		while(panemp!=null || emailemp!=null || phoneemp!=null)
		{
			
			if(emailemp!=null)
			{
				if(emailemp.getId().equals(employee.getId())){ emailemp=null;edit=true; }
				else
				{	System.out.println(emailemp.getId()+"and"+employee.getId()+"is"+(emailemp.getId().equals(employee.getId())) );
					message=message+"Email ";
					employee.setEmail(null);emailemp=null;
				}
				
			}
			else if(panemp!=null )
			{
				if((panemp.getId().equals(employee.getId()))){ panemp=null;edit=true; }
				else
				{	System.out.println(panemp.getId()+"and"+employee.getId()+"is"+(panemp.getId().equals(employee.getId())));
					message=message+"Pan Number ";//model.addAttribute("message", "Pan Number");
					employee.setPanNumber(null);panemp=null;
				}
				
			}
			
			else if(phoneemp!=null )
			{
				if((phoneemp.getId().equals(employee.getId()))){ phoneemp=null; edit=true; } 
				else
				{	System.out.println(phoneemp.getId()+"and"+employee.getId()+"is"+(phoneemp.getId().equals(employee.getId())) );
					message=message+"Phone Number ";//model.addAttribute("message", "Pan Number");
					employee.setPhoneNumber(null);phoneemp=null;
				}
				
			}
		}
			if(message!="")
			{
				message=message+"already in use by another emloyee ";
				if(edit)
				{
					model.addAttribute("message", message);
					model.addAttribute("employee", employee);
					model.addAttribute("editMode", true);
					return "employee_form";
				}
				else 
				{
					model.addAttribute("message", message);
					model.addAttribute("employee", employee);
					return "employee_form";
				}
			
			}
			employeeRepository.save(employee);
			return "redirect:/";
		
	}
	@GetMapping("/delete/{id}")
	public String deleteEmp(@PathVariable String id)
	{
		employeeRepository.deleteById(id);
		return "redirect:/";
	}
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable String id,Model model)
	{
		Employee employee = employeeRepository.findById(id).orElse(null);

		//	We can use this attribute "employee" to perform server-side rendering of the HTML with using Thymeleaf.
		//	We set employee data as "employee"
		model.addAttribute("employee", employee);
		model.addAttribute("editMode", true);
		return "employee_form";
	}
	//view controller
	@GetMapping("/viewemp/{id}")
	@ResponseBody
	public Employee getEmployee(@PathVariable("id") String id) {
	    return employeeRepository.findById(id).orElse(null);
	}
	@GetMapping("/employees/{id}")
	public ModelAndView viewEmployee(@PathVariable("id") String id) {
	  Optional<Employee> optional = employeeRepository.findById(id);
	  ModelAndView modelAndView = new ModelAndView("employee-details");
	  modelAndView.addObject("employee", optional.get());
	  return modelAndView;
	}

	

}
