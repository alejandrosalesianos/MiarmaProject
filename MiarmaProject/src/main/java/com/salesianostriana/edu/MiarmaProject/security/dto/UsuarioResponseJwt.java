package com.salesianostriana.edu.MiarmaProject.security.dto;

import lombok.*;

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
}
