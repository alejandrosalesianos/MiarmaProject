package com.salesianostriana.edu.MiarmaProject.repositories;

import com.salesianostriana.edu.MiarmaProject.model.PeticionFollow;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<PeticionFollow,Long> {

    @EntityGraph(value = "Peticiones-usuarios")
    List<PeticionFollow> findByEmisorId(UUID id);
}
