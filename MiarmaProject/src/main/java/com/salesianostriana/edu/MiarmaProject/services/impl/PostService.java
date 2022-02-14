package com.salesianostriana.edu.MiarmaProject.services.impl;

import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.CreatePostDto;
import com.salesianostriana.edu.MiarmaProject.repositories.PostRepository;
import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.services.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post,Long, PostRepository> {

    private final StorageService storageService;

    public Post save(CreatePostDto createPostDto, MultipartFile file) throws IOException {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();
        return repository.save(Post.builder()
                .contenido(createPostDto.getContenido())
                .ContenidoMultimedia(uri)
                .tipoPublicacion(createPostDto.getTipoPublicacion())
                .titulo(createPostDto.getTitulo())
                .build());
    }
    public Post edit(Long id, CreatePostDto createPostDto,MultipartFile file) throws IOException {

        String filename = storageService.store(file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return repository.findById(id).map(post ->
                save(post.builder()
                        .titulo(createPostDto.getTitulo())
                        .tipoPublicacion(createPostDto.getTipoPublicacion())
                        .contenido(createPostDto.getContenido())
                        .ContenidoMultimedia(uri)
                        .build())).get();
    }
}
