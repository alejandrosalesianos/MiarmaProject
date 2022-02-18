package com.salesianostriana.edu.MiarmaProject.model.dto.post;

import com.salesianostriana.edu.MiarmaProject.model.PostType;
import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class CreatePostDto {


    @NotBlank(message = "{post.titulo.notblank}")
    private String titulo;
    private String contenido;
    private PostType tipoPublicacion;

}
