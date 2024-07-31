package com.petscreening.pawsondeck.resolver;

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
import com.petscreening.pawsondeck.service.PetService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PetResolverTest {

    @Mock
    private PetService petService;

    @InjectMocks
    private PetResolver petResolver;

    private Pet pet1;
    private Pet pet2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Rex");
        pet1.setWeight(20.0);
        pet1.setBreed("Labrador");
        pet1.setVaccinated(true);
        pet1.setTrainingLevel(4);

        pet2 = new Pet();
        pet2.setId(2L);
        pet2.setName("Whiskers");
        pet2.setWeight(10.0);
        pet2.setBreed("Tabby");
        pet2.setVaccinated(false);
        pet2.setTrainingLevel(2);
    }

    @Test
    void testGetAllPets() {
        when(petService.getAllPets()).thenReturn(Arrays.asList(pet1, pet2));

        List<Pet> pets = petResolver.getAllPets();

        assertNotNull(pets);
        assertEquals(2, pets.size());
        assertEquals("Rex", pets.get(0).getName());
        assertEquals("Whiskers", pets.get(1).getName());
    }

    @Test
    void testGetPetById() {
        when(petService.findPetById(1L)).thenReturn(Optional.of(pet1));

        Pet pet = petResolver.getPetById(1L);

        assertNotNull(pet);
        assertEquals("Rex", pet.getName());
    }

    @Test
    void testGetPetById_NotFound() {
        when(petService.findPetById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            petResolver.getPetById(1L);
        });

        assertEquals("Pet not found", exception.getMessage());
    }

    @Test
    void testAddPet() {
        when(petService.addPet(anyString(), anyDouble(), anyString(), any(Boolean.class), anyInt(), anyLong()))
                .thenReturn(pet1);

        Pet pet = petResolver.addPet("Rex", 10.0, "Dog", true, 5, 1L);

        assertNotNull(pet);
        assertEquals("Rex", pet.getName());
    }
}
