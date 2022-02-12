package com.salesianostriana.edu.MiarmaProject.users.controller;

import com.salesianostriana.edu.MiarmaProject.users.dto.CreateUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.GetUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.UserDtoConverter;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/register")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter dtoConverter;

    @PostMapping("/")
    public ResponseEntity<GetUserDto> nuevoUser(@RequestBody CreateUserDto userDto){
        UserEntity user = userEntityService.saveUser(userDto);
        GetUserDto getUserDto = dtoConverter.UserEntityToGetUserDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(getUserDto);
    }
}
