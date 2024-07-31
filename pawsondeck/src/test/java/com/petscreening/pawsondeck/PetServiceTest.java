package com.petscreening.pawsondeck.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.petscreening.pawsondeck.model.Pet;
import com.petscreening.pawsondeck.model.PetOwner;
import com.petscreening.pawsondeck.repository.PetRepository;
import com.petscreening.pawsondeck.repository.PetOwnerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetOwnerRepository petOwnerRepository;

    @InjectMocks
    private PetService petService;

    private PetOwner petOwner1;
    private PetOwner petOwner2;

    private Pet pet1;
    private Pet pet2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize PetOwners
        petOwner1 = new PetOwner();
        petOwner1.setId(1L);
        petOwner1.setName("John Doe");
        petOwner1.setContactInformation("john.doe@example.com");

        petOwner2 = new PetOwner();
        petOwner2.setId(2L);
        petOwner2.setName("Jane Doe");
        petOwner2.setContactInformation("jane.doe@example.com");

        // Initialize Pets
        pet1 = new Pet("Rex", 20, "Labrador", true, 4, petOwner1);
        pet2 = new Pet("Whiskers", 30, "Siamese", true, 5, petOwner2);

        // Mock PetOwnerRepository
        when(petOwnerRepository.findById(1L)).thenReturn(Optional.of(petOwner1));
        when(petOwnerRepository.findById(2L)).thenReturn(Optional.of(petOwner2));
        when(petOwnerRepository.findById(3L)).thenReturn(Optional.empty());

        // Mock PetRepository
        when(petRepository.save(any(Pet.class))).thenReturn(pet1);
        when(petRepository.findAll()).thenReturn(Arrays.asList(pet1, pet2));
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet1));
        when(petRepository.findById(2L)).thenReturn(Optional.of(pet2));
        when(petRepository.findById(3L)).thenReturn(Optional.empty());
    }

    @Test
    void testAddPet() {
        when(petRepository.save(any(Pet.class))).thenReturn(pet1);

        Pet savedPet = petService.addPet("Rex", 20, "Labrador", true, 4, 1L);

        assertNotNull(savedPet);
        assertEquals("Rex", savedPet.getName());
        assertEquals("Labrador", savedPet.getBreed());
        assertEquals(20, savedPet.getWeight());
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    void testGetAllPets() {
        when(petRepository.findAll()).thenReturn(Arrays.asList(pet1, pet2));

        List<Pet> pets = petService.getAllPets();

        assertNotNull(pets);
        assertEquals(2, pets.size());
        assertEquals("Rex", pets.get(0).getName());
        assertEquals("Whiskers", pets.get(1).getName());
    }

    @Test
    void testFindPetById() {
        Optional<Pet> foundPet = petService.findPetById(1L);

        assertTrue(foundPet.isPresent());
        assertEquals("Rex", foundPet.get().getName());
    }

    @Test
    void testFindPetById_NotFound() {
        when(petRepository.findById(3L)).thenReturn(Optional.empty());

        Optional<Pet> foundPet = petService.findPetById(3L);

        assertTrue(foundPet.isEmpty(), "Expected no pet to be found");
    }


    @Test
    void testDeletePet() {
        // Ensure the pet exists in the repository mock
        when(petRepository.existsById(1L)).thenReturn(true);
        doNothing().when(petRepository).deleteById(1L);

        petService.deletePet(1L);

        verify(petRepository, times(1)).deleteById(1L);
    }

    @Test
    void testIsPetEligibleForRental() {
        Pet eligiblePet = new Pet("Rex", 20, "Labrador", true, 4, petOwner1);
        Pet overweightPet = new Pet("Whiskers", 30, "Siamese", true, 5, petOwner2);
        Pet poodle = new Pet("Poodle", 15, "Poodle", true, 4, petOwner1);
        Pet unvaccinatedPet = new Pet("Fido", 25, "Bulldog", false, 4, petOwner1);
        Pet poorlyTrainedPet = new Pet("Rover", 20, "Collie", true, 2, petOwner1);

        assertTrue(petService.isPetEligibleForRental(eligiblePet));
        assertFalse(petService.isPetEligibleForRental(overweightPet));
        assertFalse(petService.isPetEligibleForRental(poodle));
        assertFalse(petService.isPetEligibleForRental(unvaccinatedPet));
        assertFalse(petService.isPetEligibleForRental(poorlyTrainedPet));
    }
}
