package com.petscreening.pawsondeck.resolver;

import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.petscreening.pawsondeck.model.Pet;
import com.petscreening.pawsondeck.service.PetService;

import java.util.List;
import java.util.Optional;

@Component
public class PetResolver {

    @Autowired
    private PetService petService;

    @QueryMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @QueryMapping
    public Pet getPetById(Long id) {
        return petService.findPetById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    @MutationMapping
    public Pet addPet(String name, double weight, String breed, boolean vaccinated, int trainingLevel, Long petOwnerId) {
        return petService.addPet(name, weight, breed, vaccinated, trainingLevel, petOwnerId);
    }
}
