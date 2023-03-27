package com.dio.hateoas.controller;

import com.dio.hateoas.entity.Order;
import com.dio.hateoas.repository.OrderRepository;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {

    private final OrderRepository repositoryOrder;

    public OrderController(OrderRepository repositoryOrder){

        this.repositoryOrder = repositoryOrder;
    }

    @GetMapping("/orders")
    ResponseEntity<List<Order>> consultOrderAll(){
        long idOrder;
        Link linkUri;
        List<Order> orderList = repositoryOrder.findAll();
        if (orderList.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        for (Order order : orderList){
            idOrder = order.getId();
            linkUri = linkTo(methodOn(OrderController.class).consultOneOrder(idOrder)).withSelfRel();
            order.add(linkUri);
            linkUri = linkTo(methodOn(OrderController.class).consultOrderAll()).withRel("Customer order list");
            order.add(linkUri);
         }
        return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);

    }

    @GetMapping("/orders/{id}")
    ResponseEntity<Order> consultOneOrder(@PathVariable Long id){
        Optional<Order> orderPointer = repositoryOrder.findById(id);
        if (orderPointer.isPresent()){
            Order order = orderPointer.get();
            order.add(linkTo(methodOn(OrderController.class).consultOrderAll()).withRel("All orders"));
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/orders")
    Order newOrder(@RequestBody Order newOrder){
        return repositoryOrder.save(newOrder);
    }

    @PutMapping("/orders/{id}")
    Order replaceOrder(@RequestBody Order newOrder, long id) {
        return repositoryOrder.findById(id).map(order -> {
            order.setDescription(newOrder.getDescription());
            order.setStatus(newOrder.getStatus());
            return repositoryOrder.save(newOrder);
        }).orElseGet(() -> {
            newOrder.setId(id);
            return repositoryOrder.save(newOrder);
        });
    }
    @DeleteMapping("/orders/{id}")
            void deleteOrder(@PathVariable long id){
                    repositoryOrder.deleteById(id);
    }
}

