package com.petscreening.pawsondeck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.petscreening.pawsondeck.model.Pet;
import com.petscreening.pawsondeck.model.PetOwner;
import com.petscreening.pawsondeck.repository.PetRepository;
import com.petscreening.pawsondeck.repository.PetOwnerRepository;
import java.util.Optional;
import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetOwnerRepository petOwnerRepository;

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet addPet(String name, double weight, String breed, boolean vaccinated, int trainingLevel, Long petOwnerId) {
        if (trainingLevel < 1 || trainingLevel > 10) {
            throw new IllegalArgumentException("Training level must be an integer between 1 and 10");
        }

        Optional<PetOwner> petOwnerOptional = petOwnerRepository.findById(petOwnerId);
        if (!petOwnerOptional.isPresent()) {
            throw new IllegalArgumentException("PetOwner with ID " + petOwnerId + " does not exist.");
        }

        PetOwner petOwner = petOwnerOptional.get();

        Pet pet = new Pet();
        pet.setName(name);
        pet.setWeight(weight);
        pet.setBreed(breed);
        pet.setVaccinated(vaccinated);
        pet.setTrainingLevel(trainingLevel);
        pet.setPetOwner(petOwner);

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
