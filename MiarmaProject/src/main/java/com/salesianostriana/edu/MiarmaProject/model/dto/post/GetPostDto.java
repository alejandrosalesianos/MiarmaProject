package com.salesianostriana.edu.MiarmaProject.model.dto.post;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class GetPostDto {

    private Long id;
    private String titulo;
    private String contenido;
    private String contenidoMultimedia;
    private String tipoPublicacion;
}
