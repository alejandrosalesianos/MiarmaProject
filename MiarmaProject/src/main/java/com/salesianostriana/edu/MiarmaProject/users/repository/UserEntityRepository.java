package com.salesianostriana.edu.MiarmaProject.users.repository;

import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID>{

    Optional<UserEntity> findFirstByEmail(String email);

    Optional<UserEntity> findByNombreUsuario(String username);

    Optional<List<UserEntity>> findByPerfil(UserProfile userProfile);

    Optional<UserEntity> findById(UUID id);

    @Query(
            """
            select u
            from UserEntity u
            join fetch u.followers
            where u.id = :id   
            """
    )
    List<UserEntity> findAllFollowers(@Param("id") UUID id);



}
