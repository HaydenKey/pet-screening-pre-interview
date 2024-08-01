package com.petscreening.pawsondeck.resolver;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;

import com.petscreening.pawsondeck.model.PetOwner;
import com.petscreening.pawsondeck.service.PetOwnerService;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class PetOwnerResolver {
    private PetOwnerService petOwnerService;

    @Autowired
    PetOwnerResolver(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    @QueryMapping
    public List<PetOwner> petOwners() {
        List<PetOwner> owners = petOwnerService.getPetOwners();
        return owners;
    }

    @QueryMapping
    public PetOwner getPetOwnerById(@Argument Long id) {
        return petOwnerService.findPetOwnerById(id);
    }

    @MutationMapping
    @Transactional
    public PetOwner addPetOwner(@Argument String name, @Argument String contactInformation) {
        try {
            PetOwner petOwner = new PetOwner();
            petOwner.setName(name);
            petOwner.setContactInformation(contactInformation);
            PetOwner result = petOwnerService.addPetOwner(petOwner);
            if (result == null) {
                throw new RuntimeException("Failed to add PetOwner");
            }
            return result;
        } catch (Exception e) {
            // Log and handle exception
            throw new RuntimeException("Error adding PetOwner: " + e.getMessage());
        }
    }
}
