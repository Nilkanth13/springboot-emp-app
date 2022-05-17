package com.emp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.exception.ResourceNotFoundException;
import com.emp.model.Employee;
import com.emp.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository repository;

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee e) {
		return repository.save(e);
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not Found !!"));
		return ResponseEntity.ok(employee);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updatEmployee(@PathVariable Long id, @RequestBody Employee emp) {
		Employee employee = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not Found !!"));
		employee.setFirstname(emp.getFirstname());
		employee.setLastname(emp.getLastname());
		employee.setEmailId(emp.getEmailId());

		Employee updateemployee = repository.save(employee);

		return ResponseEntity.ok(updateemployee);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		Employee employee = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not Found !!"));

		repository.delete(employee);
		Map<String, Boolean> resMap = new HashMap<>();
		resMap.put("Deleted !!", true);

		return ResponseEntity.ok(resMap);

	}
}
