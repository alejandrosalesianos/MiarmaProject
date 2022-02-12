package com.salesianostriana.edu.MiarmaProject.users.dto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class GetUserDto {

    private UUID id;
    private String username;
    private String email;
    private String telefono;
    private String avatar;
    private String perfil;

}
