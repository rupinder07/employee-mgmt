package org.nagarro.nagp.apigateway.controllers;

import org.nagarro.nagp.apigateway.dto.EmployeeDto;
import org.nagarro.nagp.apigateway.exception.EmployeeNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final List<EmployeeDto> employees = new ArrayList<>();

    static {
        employees.add(new EmployeeDto("1", "Employee1", "employee1@nagarro.com"));
        employees.add(new EmployeeDto("2", "Employee2", "employee2@nagarro.com"));
    }

    @GetMapping
    public List<EmployeeDto> get(){
        return employees;
    }

    @GetMapping
    @RequestMapping("/{id}")
    public EmployeeDto get(@PathVariable final String id){
        return employees.stream()
                .filter(emp -> emp.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with id %s not found", id)));
    }

    @PostMapping
    public EmployeeDto create(@RequestBody final EmployeeDto employeeDto){
        final int id = employees.stream().mapToInt(emp -> Integer.parseInt(emp.getId())).max().orElse(1);
        employeeDto.setId(Integer.toString(id + 1));
        employees.add(employeeDto);
        return employeeDto;
    }
}
