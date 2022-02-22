package com.salesianostriana.edu.MiarmaProject.repositories;

import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.model.PostType;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post,Long> {

    Page<Post> findByTipoPublicacion(PostType postType, Pageable pageable);

    List<Post> findAllByUserNombreUsuario(String nombre);

    @EntityGraph(value = "Post-Usuarios")
    List<Post> findByUserId(UUID uuid);
}
