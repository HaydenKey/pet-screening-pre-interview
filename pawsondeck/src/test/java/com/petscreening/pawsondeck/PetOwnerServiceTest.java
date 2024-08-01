package com.petscreening.pawsondeck.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.petscreening.pawsondeck.model.PetOwner;
import com.petscreening.pawsondeck.repository.PetOwnerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PetOwnerServiceTest {

    @Mock
    private PetOwnerRepository petOwnerRepository;

    @InjectMocks
    private PetOwnerService petOwnerService;

    private PetOwner petOwner1;
    private PetOwner petOwner2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        petOwner1 = new PetOwner("John Doe", "john.doe@example.com");
        petOwner1.setId(1L);

        petOwner2 = new PetOwner("Jane Doe", "jane.doe@example.com");
        petOwner2.setId(2L);
    }

    @Test
    void testAddPetOwner() {
        when(petOwnerRepository.save(any(PetOwner.class))).thenReturn(petOwner1);

        PetOwner savedPetOwner = petOwnerService.addPetOwner(petOwner1);

        assertNotNull(savedPetOwner);
        assertEquals("John Doe", savedPetOwner.getName());
        assertEquals("john.doe@example.com", savedPetOwner.getContactInformation());
        verify(petOwnerRepository, times(1)).save(petOwner1);
    }

    @Test
    void testGetAllPetOwners() {
        when(petOwnerRepository.findAll()).thenReturn(Arrays.asList(petOwner1, petOwner2));

        List<PetOwner> petOwners = petOwnerService.getPetOwners();

        assertNotNull(petOwners);
        assertEquals(2, petOwners.size());
        assertEquals("John Doe", petOwners.get(0).getName());
        assertEquals("Jane Doe", petOwners.get(1).getName());
    }

    @Test
    void testFindPetOwnerById() {
        when(petOwnerRepository.findById(1L)).thenReturn(Optional.of(petOwner1));

        PetOwner foundPetOwner = petOwnerService.findPetOwnerById(1L);

        assertNotNull(foundPetOwner);
        assertEquals("John Doe", foundPetOwner.getName());
        assertEquals("john.doe@example.com", foundPetOwner.getContactInformation());
    }

    @Test
    void testFindPetOwnerById_NotFound() {
        when(petOwnerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            petOwnerService.findPetOwnerById(1L);
        });

        assertEquals("Pet Owner not found", exception.getMessage());
    }

    @Test
    void testDeletePetOwner() {
        doNothing().when(petOwnerRepository).deleteById(1L);

        petOwnerService.deletePetOwner(1L);

        verify(petOwnerRepository, times(1)).deleteById(1L);
    }
}
