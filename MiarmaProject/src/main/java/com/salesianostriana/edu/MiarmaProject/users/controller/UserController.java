package com.salesianostriana.edu.MiarmaProject.users.controller;

import com.salesianostriana.edu.MiarmaProject.error.exception.ListNotFoundException;
import com.salesianostriana.edu.MiarmaProject.users.dto.CreateUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.GetUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.UserDtoConverter;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.model.UserProfile;
import com.salesianostriana.edu.MiarmaProject.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/register")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter dtoConverter;

    @PostMapping("/")
    public ResponseEntity<?> nuevoUser(@RequestParam("telefono") String telefono, @Valid @RequestParam("nick") String nick, @RequestParam String fechaNacimiento, @RequestParam("perfil") UserProfile rol,  @RequestParam("password") String password,  @RequestParam("password2") String password2,  @RequestParam("email") String email, @RequestPart("file")MultipartFile file) throws Exception, ListNotFoundException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        CreateUserDto userDto = CreateUserDto.builder()
                .nick(nick)
                .email(email)
                .fechaNacimiento(LocalDate.parse(fechaNacimiento, formatter))
                .telefono(telefono)
                .perfil(rol)
                .password(password)
                .password2(password2)
                .build();

        UserEntity user = userEntityService.saveUser(userDto,file);
        GetUserDto getUserDto = dtoConverter.UserEntityToGetUserDto(user);
        return ResponseEntity.created(URI.create(user.getFotoPerfil())).body(getUserDto);
    }
}
