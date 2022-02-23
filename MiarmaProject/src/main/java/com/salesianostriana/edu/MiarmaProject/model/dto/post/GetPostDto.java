package com.salesianostriana.edu.MiarmaProject.model.dto.post;

import com.salesianostriana.edu.MiarmaProject.model.PostType;
import com.salesianostriana.edu.MiarmaProject.users.dto.GetUserDto;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class GetPostDto {

    private Long id;
    private String titulo;
    private String contenido;
    private String contenidoOriginal;
    private String contenidoMultimedia;
    private String tipoPublicacion;
    private String user;
    private String avatarUser;

}
