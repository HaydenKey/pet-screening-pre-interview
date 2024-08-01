package com.petscreening.pawsondeck.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.petscreening.pawsondeck.model.Pet;
import com.petscreening.pawsondeck.model.PetOwner;
import com.petscreening.pawsondeck.repository.PetRepository;
import com.petscreening.pawsondeck.repository.PetOwnerRepository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Service
@Validated
public class PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetOwnerRepository petOwnerRepository;

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Transactional
    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Optional<Pet> findPetById(Long id) {
        return petRepository.findById(id);
    }


    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new IllegalArgumentException("Pet with ID " + id + " does not exist.");
        }
        petRepository.deleteById(id);
    }

    public boolean isPetEligibleForRental(Pet pet) {
        if (pet.getWeight() > 25) {
            return false;
        }
        if (pet.getBreed().equalsIgnoreCase("Poodle")) {
            return false;
        }
        if (!pet.isVaccinated()) {
            return false;
        }
        if (pet.getTrainingLevel() < 3) {
            return false;
        }
        return true;
    }
}
