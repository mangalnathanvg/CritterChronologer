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
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public List<Pet> getAllPets()
    {
        List<Pet> pets = petRepository.findAll();
        return pets;
    }

    public Pet savePet(Pet pet, Long customerId)
    {
        Customer customer = customerRepository.getOne(customerId);

        List<Pet> pets = new ArrayList<>();

        pet.setCustomer(customer);

        pet = petRepository.save(pet);
        pets.add(pet);

        customer.setPets(pets);
        customerRepository.save(customer);

        return pet;
    }

    public List<Pet> getPetsByCustomerId(Long customerId) {
        List<Pet> pets = petRepository.findPetByCustomerId(customerId);
        return pets;
    }

    public Pet getPetById(Long petId) {
        Pet pet = petRepository.getOne(petId);
        return pet;
    }
}
