package com.salesianostriana.edu.MiarmaProject.model.dto.post;

import com.salesianostriana.edu.MiarmaProject.model.PostType;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class CreatePostDto {

    private String titulo;
    private String contenido;
    private PostType tipoPublicacion;
    private Long idUsuario;

}
