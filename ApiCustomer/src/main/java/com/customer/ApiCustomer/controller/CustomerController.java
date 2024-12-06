package com.customer.ApiCustomer.controller;

import com.customer.ApiCustomer.model.Customer;
import java.util.List;
import java.util.Map;

import com.customer.ApiCustomer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")

@Tag(name = "Customers", description = "Customer Management")

public class CustomerController {
    @Autowired
    private  CustomerService customerService;


    @Operation(summary = "Lista todos los clientes", description = "Lista todos los clientes del sistema")
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    @Operation(summary = "Registra un cliente", description = "Registra un cliente en el sistema")
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @Operation(summary = "Busca un cliente por ID", description = "Busca un cliente del sistema por ID")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Actualiza datos de un cliente", description = "Actualiza datos de un cliente del sistema")
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.updateCustomer(id, customer), HttpStatus.OK);
    }

    @Operation(summary = "Elimina un cliente", description = "Elimina un cliente del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }





}
