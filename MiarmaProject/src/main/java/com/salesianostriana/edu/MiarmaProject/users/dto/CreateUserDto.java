package com.salesianostriana.edu.MiarmaProject.users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.edu.MiarmaProject.users.model.UserProfile;
import com.salesianostriana.edu.MiarmaProject.validation.simple.UniqueNick;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class CreateUserDto {

    @NotBlank(message = "{user.nick.notblank}")
    @UniqueNick(message = "{user.nick.unique}")
    private String nick;
    @NotBlank(message = "{user.email.notblank}")
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento;

    private String telefono;

    private UserProfile perfil;

    @NotBlank(message = "{user.password.notblank}")
    private String password;
    @NotBlank(message = "{user.password2.notblank}")
    private String password2;
}
