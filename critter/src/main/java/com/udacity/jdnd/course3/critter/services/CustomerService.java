package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    public List<Customer> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    public Customer saveCustomer(Customer customer, List<Long> petIds){
        List<Pet> customerPets = new ArrayList<>();
        if(petIds != null && !petIds.isEmpty())
        {
            for(Long petId: petIds)
            {
                customerPets.add(petRepository.getOne(petId));
            }
        }
        customer.setPets(customerPets);
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long petId)
    {
        Pet pet = petRepository.getOne(petId);
        return pet.getCustomer();
    }
}
