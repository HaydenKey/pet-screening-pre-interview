package com.petscreening.pawsondeck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petscreening.pawsondeck.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
