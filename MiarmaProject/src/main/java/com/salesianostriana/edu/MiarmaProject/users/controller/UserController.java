package com.salesianostriana.edu.MiarmaProject.users.controller;

import com.salesianostriana.edu.MiarmaProject.users.dto.CreateUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.GetUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.UserDtoConverter;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/register")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter dtoConverter;

    @PostMapping("/")
    public ResponseEntity<?> nuevoUser(@RequestPart("user") CreateUserDto userDto, @RequestPart("file")MultipartFile file) throws IOException {
        UserEntity user = userEntityService.saveUser(userDto,file);
        GetUserDto getUserDto = dtoConverter.UserEntityToGetUserDto(user,file);
        return ResponseEntity.status(HttpStatus.CREATED).body(getUserDto);
    }
}
