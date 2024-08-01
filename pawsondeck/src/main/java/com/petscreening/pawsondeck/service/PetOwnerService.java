package com.petscreening.pawsondeck.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

import java.util.List;

import com.petscreening.pawsondeck.model.PetOwner;
import com.petscreening.pawsondeck.repository.PetOwnerRepository;

@Service
@Validated
public class PetOwnerService {

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    @Transactional
    public PetOwner addPetOwner(@Valid PetOwner petOwner) {
        return petOwnerRepository.save(petOwner);
    }


    public List<PetOwner> getPetOwners() {
        return petOwnerRepository.findAll();
    }

    public PetOwner findPetOwnerById(Long id) {
        return petOwnerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet Owner not found"));
    }

    @Transactional
    public void deletePetOwner(Long id) {
        petOwnerRepository.deleteById(id);
    }
}
