package com.customer.ApiCustomer;

import com.customer.ApiCustomer.model.Customer;
import com.customer.ApiCustomer.repository.CustomerRepository;
import com.customer.ApiCustomer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCustomer() {
        // Dado un nuevo Customer
        Customer customer = new Customer();
        customer.setFirstName("Javier");
        customer.setLastName("Martinez");
        customer.setDni("42563254");
        customer.setEmail("javierM@egmail.com");

        when(customerRepository.save(customer)).thenReturn(customer);

        // Cuando se llama a createCustomer
        Customer createdCustomer = customerService.createCustomer(customer);

        // Entonces
        assertNotNull(createdCustomer);
        assertEquals("Javier", createdCustomer.getFirstName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testGetCustomerById() {
        // Dado un Customer existente
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Juana");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Cuando se llama a getCustomerById
        Customer foundCustomer = customerService.getCustomerById(1L);

        // Entonces
        assertNotNull(foundCustomer);
        assertEquals("Juana", foundCustomer.getFirstName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCustomerById_NotFound() {
        // Dado que el Customer no existe
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Cuando se llama a getCustomerById
        Customer foundCustomer = customerService.getCustomerById(1L);

        // Entonces se devuelve null
        assertNull(foundCustomer);
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateCustomer() {
        // Dado un Customer existente
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setFirstName("John");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("Jane");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

        // Cuando se llama a updateCustomer
        Customer result = customerService.updateCustomer(1L, updatedCustomer);

        // Entonces se actualizan los datos
        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    public void testDeleteCustomer() {
        // Dado que el Customer existe
        when(customerRepository.existsById(1L)).thenReturn(true);

        // Cuando se llama a deleteCustomer
        boolean isDeleted = customerService.deleteCustomer(1L);

        // Entonces
        assertTrue(isDeleted);
        verify(customerRepository, times(1)).existsById(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }
}
