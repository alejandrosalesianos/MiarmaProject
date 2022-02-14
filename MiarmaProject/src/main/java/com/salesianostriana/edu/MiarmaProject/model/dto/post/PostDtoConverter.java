package com.salesianostriana.edu.MiarmaProject.model.dto.post;

import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.users.dto.GetUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.UserDtoConverter;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PostDtoConverter {

    private final StorageService storageService;


    public GetPostDto postToGetPostDto(Post post, GetUserDto getUserDto) throws IOException {
        return GetPostDto.builder()
                .id(post.getId())
                .contenido(post.getContenido())
                .contenidoMultimedia(post.getContenidoMultimedia())
                .tipoPublicacion(post.getTipoPublicacion().name())
                .titulo(post.getTitulo())
                .user(getUserDto)
                .build();
    }
    public Post createPostDtoToPost(CreatePostDto createPostDto, MultipartFile file) throws IOException {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();
        return Post.builder()
                .titulo(createPostDto.getTitulo())
                .contenido(createPostDto.getContenido())
                .tipoPublicacion(createPostDto.getTipoPublicacion())
                .ContenidoMultimedia(uri)
                .build();
    }
}
