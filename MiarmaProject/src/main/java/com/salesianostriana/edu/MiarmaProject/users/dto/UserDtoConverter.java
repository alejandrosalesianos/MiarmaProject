package com.salesianostriana.edu.MiarmaProject.users.dto;

import com.salesianostriana.edu.MiarmaProject.error.exception.ListNotFoundException;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.PostDtoConverter;
import com.salesianostriana.edu.MiarmaProject.services.impl.FollowService;
import com.salesianostriana.edu.MiarmaProject.services.impl.PostService;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.repository.UserEntityRepository;
import com.salesianostriana.edu.MiarmaProject.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    private final PostService postService;
    private final FollowService followService;
    private final UserEntityService userEntityService;

    public GetUserDto UserEntityToGetUserDto(UserEntity user) throws ListNotFoundException {

        return GetUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .avatar(user.getFotoPerfil())
                .perfil(user.getPerfil().name())
                .fecha(user.getFechaNacimiento())
                .posts(postService.PostListEntityGraph(user))
                .listaPeticiones(followService.findUserById(user.getId()))
                .followers(userEntityService.ListGetUserDto(user))
                .build();
    }
}
