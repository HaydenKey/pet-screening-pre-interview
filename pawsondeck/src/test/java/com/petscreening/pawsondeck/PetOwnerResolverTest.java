package com.petscreening.pawsondeck.resolver;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.any;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.petscreening.pawsondeck.model.PetOwner;
import com.petscreening.pawsondeck.service.PetOwnerService;


class PetOwnerResolverTest {

    @Mock
    private PetOwnerService petOwnerService;

    @InjectMocks
    private PetOwnerResolver petOwnerResolver;

    private PetOwner petOwner1;
    private PetOwner petOwner2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        petOwner1 = new PetOwner();
        petOwner1.setId(1L);
        petOwner1.setName("John Doe");
        petOwner1.setContactInformation("john.doe@example.com");

        petOwner2 = new PetOwner();
        petOwner2.setId(2L);
        petOwner2.setName("Jane Doe");
        petOwner2.setContactInformation("jane.doe@example.com");
    }

    @Test
    void testGetAllPetOwners() {
        when(petOwnerService.getPetOwners()).thenReturn(Arrays.asList(petOwner1, petOwner2));

        List<PetOwner> petOwners = petOwnerResolver.petOwners();
        assertEquals(2, petOwners.size());
        assertEquals("John Doe", petOwners.get(0).getName());
        assertEquals("Jane Doe", petOwners.get(1).getName());
    }

    @Test
    void testGetPetOwnerById() {
        when(petOwnerService.findPetOwnerById(1L)).thenReturn(petOwner1);

        PetOwner petOwner = petOwnerResolver.getPetOwnerById(1L);
        assertNotNull(petOwner);
        assertEquals("John Doe", petOwner.getName());
    }

    @Test
    void testAddPetOwner() {
        // Create a valid PetOwner object
        PetOwner petOwnerToAdd = new PetOwner("John Doe", "john.doe@example.com");

        // Mock the service method to return the petOwnerToAdd object
        when(petOwnerService.addPetOwner(any(PetOwner.class))).thenReturn(petOwnerToAdd);

        // Call the resolver method
        PetOwner petOwner = petOwnerResolver.addPetOwner("John Doe", "john.doe@example.com");

        // Verify the results
        assertNotNull(petOwner);
        assertEquals("John Doe", petOwner.getName());
        assertEquals("john.doe@example.com", petOwner.getContactInformation());
    }

}
