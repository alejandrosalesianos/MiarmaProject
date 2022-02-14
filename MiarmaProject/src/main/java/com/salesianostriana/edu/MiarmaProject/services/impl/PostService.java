package com.salesianostriana.edu.MiarmaProject.services.impl;

import com.salesianostriana.edu.MiarmaProject.exception.ListNotFoundException;
import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.model.PostType;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.CreatePostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import com.salesianostriana.edu.MiarmaProject.repositories.PostRepository;
import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.services.base.BaseService;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post,Long, PostRepository> {

    private final StorageService storageService;

    public Post save(Post post, MultipartFile file, UserEntity user) throws IOException {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();
        return repository.save(Post.builder()
                .contenido(post.getContenido())
                .ContenidoMultimedia(uri)
                .tipoPublicacion(post.getTipoPublicacion())
                .titulo(post.getTitulo())
                .user(user)
                .build());
    }
    public Post edit(Long id, Post post,MultipartFile file) throws IOException {

        String filename = storageService.store(file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();
        storageService.deleteFile(post.getContenidoMultimedia());

        return repository.findById(id).map(newPost ->
                save(newPost.builder()
                        .id(id)
                        .titulo(post.getTitulo())
                        .tipoPublicacion(post.getTipoPublicacion())
                        .contenido(post.getContenido())
                        .ContenidoMultimedia(uri)
                        .build())).get();
    }
}
