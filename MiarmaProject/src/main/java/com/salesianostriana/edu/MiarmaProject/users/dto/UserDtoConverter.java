package com.salesianostriana.edu.MiarmaProject.users.dto;

import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.PostDtoConverter;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    private final PostDtoConverter postDtoConverter;


    public GetUserDto UserEntityToGetUserDto(UserEntity user) throws Exception {

        return GetUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .avatar(user.getFotoPerfil())
                .perfil(user.getPerfil().name())
                .posts(user.getPosts()
                        .stream().map(
                                p -> GetPostDto.builder()
                                        .id(p.getId())
                                        .titulo(p.getTitulo())
                                        .contenidoOriginal(p.getContenidoOriginal())
                                        .contenido(p.getContenido())
                                        .tipoPublicacion(p.getTipoPublicacion().name())
                                        .contenidoMultimedia(p.getContenidoMultimedia())
                                        .user(user.getUsername())
                                        .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
