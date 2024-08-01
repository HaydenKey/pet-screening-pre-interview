package com.petscreening.pawsondeck.resolver;

import com.petscreening.pawsondeck.model.PetOwner;
import com.petscreening.pawsondeck.service.PetOwnerService;
import jakarta.transaction.Transactional;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.petscreening.pawsondeck.model.Pet;
import com.petscreening.pawsondeck.service.PetService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PetResolver {
    private PetService petService;
    private PetOwnerService petOwnerService;

    @Autowired
    PetResolver(PetService petService, PetOwnerService petOwnerService) {
        this.petService = petService;
        this.petOwnerService = petOwnerService;
    }

    @QueryMapping
    public List<Pet> pets() {
        List<Pet> pets = petService.getAllPets();
        return pets;
    }

    @QueryMapping
    public Pet getPetById(@Argument Long id) {
        return petService.findPetById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    @MutationMapping
    @Transactional
    public Pet addPet(
            @Argument String name,
            @Argument double weight,
            @Argument String breed,
            @Argument boolean vaccinated,
            @Argument int trainingLevel,
            @Argument Long petOwnerId
    ) {
        Pet pet = new Pet();

        pet.setName(name);
        pet.setWeight(weight);
        pet.setBreed(breed);
        pet.setVaccinated(vaccinated);
        pet.setTrainingLevel(trainingLevel);
        pet.setPetOwner(petOwnerService.findPetOwnerById(petOwnerId));

        return petService.addPet(pet);
    }
}
