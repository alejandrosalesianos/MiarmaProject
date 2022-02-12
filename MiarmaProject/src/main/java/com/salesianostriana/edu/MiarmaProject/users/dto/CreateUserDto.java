package com.salesianostriana.edu.MiarmaProject.users.dto;

import com.salesianostriana.edu.MiarmaProject.users.model.UserProfile;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class CreateUserDto {

    private String nick;
    private String email;
    private String fechaNacimiento;
    private String avatar;
    private String telefono;
    private UserProfile perfil;
    private String password;
    private String password2;
}
