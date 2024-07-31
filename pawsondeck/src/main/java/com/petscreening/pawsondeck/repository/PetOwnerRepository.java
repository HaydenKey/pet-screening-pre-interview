package com.petscreening.pawsondeck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petscreening.pawsondeck.model.PetOwner;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {
}
