package com.salesianostriana.edu.MiarmaProject.users.dto;

import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter2 {

    public GetUserWithoutLists userEntityToGetUserWithoutLists(UserEntity user){

        return GetUserWithoutLists.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .avatar(user.getFotoPerfil())
                .perfil(user.getPerfil().name())
                .build();
    }
}
