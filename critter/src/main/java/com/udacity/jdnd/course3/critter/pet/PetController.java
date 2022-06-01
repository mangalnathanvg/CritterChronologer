package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    private PetDTO getPetDTO(Pet pet)
    {
        return new PetDTO(pet.getId(), pet.getType(), pet.getName(), pet.getCustomer().getId(), pet.getBirthDate(), pet.getNotes());
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet(petDTO.getType(), petDTO.getName(), petDTO.getBirthDate(), petDTO.getNotes());
        PetDTO convertedPetDTO;
        try {
            convertedPetDTO = getPetDTO(petService.savePet(pet, petDTO.getOwnerId()));
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet cannot be saved", exception);
        }
        return convertedPetDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet;
        try {
            pet = petService.getPetById(petId);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet not found", exception);
        }
        return getPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(this::getPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets;
        try {
            pets = petService.getPetsByCustomerId(ownerId);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner not found", exception);
        }
        return pets.stream().map(this::getPetDTO).collect(Collectors.toList());
    }
}
