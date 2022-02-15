package com.salesianostriana.edu.MiarmaProject.model.dto.post;

import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.services.StorageService;
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


    public GetPostDto postToGetPostDto(Post post) {
        return GetPostDto.builder()
                .id(post.getId())
                .contenido(post.getContenido())
                .contenidoOriginal(post.getContenidoOriginal())
                .contenidoMultimedia(post.getContenidoMultimedia())
                .tipoPublicacion(post.getTipoPublicacion().name())
                .titulo(post.getTitulo())
                .user(post.getUser().getUsername())
                .build();
    }
    public GetPostDto postToGetPostDtoWithUser(Post post,UserEntity user) {
        return GetPostDto.builder()
                .id(post.getId())
                .contenido(post.getContenido())
                .contenidoOriginal(post.getContenidoOriginal())
                .contenidoMultimedia(post.getContenidoMultimedia())
                .tipoPublicacion(post.getTipoPublicacion().name())
                .titulo(post.getTitulo())
                .user(user.getUsername())
                .build();
    }
    public Post createPostDtoToPost(CreatePostDto createPostDto, MultipartFile file) throws IOException {

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(file.getOriginalFilename())
                .toUriString();
        String uri2 = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(file.getName())
                .toUriString();

        return Post.builder()
                .titulo(createPostDto.getTitulo())
                .contenido(createPostDto.getContenido())
                .contenidoOriginal(uri2)
                .tipoPublicacion(createPostDto.getTipoPublicacion())
                .contenidoMultimedia(uri)
                .build();
    }
}
