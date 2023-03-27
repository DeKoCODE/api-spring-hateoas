package com.dio.hateoas;

import com.dio.hateoas.entity.Employee;
import com.dio.hateoas.entity.Order;
import com.dio.hateoas.entity.Status;
import com.dio.hateoas.repository.EmployeeRepository;
import com.dio.hateoas.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDB {
    private static final Logger log = LoggerFactory.getLogger(LoadDB.class);

    @Bean
    CommandLineRunner loadData(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        return args -> {
            log.info("Log of save event: " + employeeRepository.save(new Employee("Catarina", "Gerente", "Antonio Carlos,192")));
            log.info("Log of save event: " + employeeRepository.save(new Employee("Fabricio", "Coordenador", "Antonio Carlos,192")));

            orderRepository.save(new Order(Status.COMPLETED, "review"));
            orderRepository.save(new Order(Status.IN_PROGRESS, "travel"));
            orderRepository.save(new Order(Status.IN_PROGRESS, "sale"));
            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };

    }
}
