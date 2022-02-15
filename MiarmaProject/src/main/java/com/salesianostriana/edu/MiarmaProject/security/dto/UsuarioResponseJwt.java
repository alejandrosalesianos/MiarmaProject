package com.salesianostriana.edu.MiarmaProject.security.dto;

import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseJwt {

    private String email;
    private String nick;
    private String avatar;
    private String perfil;
    private String token;
    private List<GetPostDto> posts;
}
