package com.dio.hateoas.exception;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(long id){
            super("Could not found the employee id: " + id);
        }
}
