package com.salesianostriana.edu.MiarmaProject.controllers;

import com.salesianostriana.edu.MiarmaProject.error.exception.ListNotFoundException;
import com.salesianostriana.edu.MiarmaProject.users.dto.CreateUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.GetUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.UserDtoConverter;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> findUserById(@PathVariable UUID id) throws Exception, ListNotFoundException {
        Optional<UserEntity> user = userEntityService.findById(id);
        if (!user.isPresent()){
            return ResponseEntity.notFound().build();
        }else {
            GetUserDto getUserDto = userDtoConverter.UserEntityToGetUserDto(user.get());
            return ResponseEntity.ok().body(getUserDto);
        }
    }
    @PutMapping("/me")
    public ResponseEntity<GetUserDto> editProfile(@RequestPart("file")MultipartFile file, @RequestPart("user")CreateUserDto createUserDto, @AuthenticationPrincipal UserEntity user) throws ListNotFoundException, Exception {
        UserEntity newUser = userEntityService.edit(createUserDto,file,user);
        GetUserDto getUserDto = userDtoConverter.UserEntityToGetUserDto(newUser);
        return ResponseEntity.ok().body(getUserDto);
    }
}
