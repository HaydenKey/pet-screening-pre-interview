package com.petscreening.pawsondeck.resolver;

import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.petscreening.pawsondeck.model.PetOwner;
import com.petscreening.pawsondeck.service.PetOwnerService;

import java.util.List;


@Component
public class PetOwnerResolver {

    @Autowired
    private PetOwnerService petOwnerService;

    @QueryMapping
    public List<PetOwner> petOwners() {
        List<PetOwner> owners = petOwnerService.petOwners();

        System.out.println(owners);

        return owners;
    }

    @QueryMapping
    public PetOwner getPetOwnerById(Long id) {
        return petOwnerService.findPetOwnerById(id);
    }

    @MutationMapping
    public PetOwner addPetOwner(String name, String contactInformation) {
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
