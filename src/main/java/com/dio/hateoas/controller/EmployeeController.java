package com.dio.hateoas.controller;

import com.dio.hateoas.entity.Employee;
import com.dio.hateoas.exception.EmployeeNotFoundException;
import com.dio.hateoas.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository){
        this.repository = repository;
    }

    @PostMapping("/employees")
    public Employee newEmployee(@RequestBody Employee newEmployee){
        return repository.save(newEmployee);
    }

    @GetMapping("/employees")
    public List<Employee> listOfEmployeeAll(){
        return repository.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee consultById(@PathVariable Long id){
        return repository.findById(id).orElseThrow(()->new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, long id){
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            employee.setAddress(newEmployee.getAddress());
            return repository.save(newEmployee);
        }).orElseGet(()-> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
    }

    @DeleteMapping("/employees?{id}")
    void deleteEmployee(@PathVariable long id){
        repository.deleteById(id);
    }

}

