package com.salesianostriana.edu.MiarmaProject.model;

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

    private String ContenidoMultimedia;

    private PostType tipoPublicacion;
}
