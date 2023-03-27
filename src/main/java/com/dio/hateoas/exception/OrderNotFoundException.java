package com.dio.hateoas.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(long id){
        super("Could not found the order: "+id);
    }
}
