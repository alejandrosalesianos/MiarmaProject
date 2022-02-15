package com.salesianostriana.edu.MiarmaProject.model;

import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String titulo;

    private String contenido;

    private String contenidoOriginal;

    private String contenidoMultimedia;

    private PostType tipoPublicacion;

    @ManyToOne
    private UserEntity user;

    //Helpers

    public void addToUser(UserEntity user){
        user.getPosts().add(this);
    }
}
