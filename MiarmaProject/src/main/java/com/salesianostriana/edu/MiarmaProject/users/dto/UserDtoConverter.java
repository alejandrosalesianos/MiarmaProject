package com.salesianostriana.edu.MiarmaProject.users.dto;

import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    private final StorageService storageService;

    public GetUserDto UserEntityToGetUserDto(UserEntity user, MultipartFile file) throws IOException {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return GetUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .avatar(uri)
                .perfil(user.getPerfil().name())
                .build();
    }
}
