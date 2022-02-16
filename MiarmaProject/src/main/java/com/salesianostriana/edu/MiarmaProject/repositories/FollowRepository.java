package com.salesianostriana.edu.MiarmaProject.repositories;

import com.salesianostriana.edu.MiarmaProject.model.PeticionFollow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<PeticionFollow,Long> {
}
