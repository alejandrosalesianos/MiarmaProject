package com.salesianostriana.edu.MiarmaProject.services.impl;

import com.salesianostriana.edu.MiarmaProject.exception.ListNotFoundException;
import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.model.PostType;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.CreatePostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.PostDtoConverter;
import com.salesianostriana.edu.MiarmaProject.repositories.PostRepository;
import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.services.base.BaseService;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post,Long, PostRepository> {

    private final StorageService storageService;
    private final PostDtoConverter postDtoConverter;

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
    public Post edit(Long id, Post post,MultipartFile file,UserEntity user) throws IOException {

        String filename = storageService.store(file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();
        storageService.deleteFile(file.getOriginalFilename());

        return repository.findById(id).map(newPost ->
                save(newPost.builder()
                        .id(id)
                        .titulo(post.getTitulo())
                        .tipoPublicacion(post.getTipoPublicacion())
                        .contenido(post.getContenido())
                        .ContenidoMultimedia(uri)
                        .user(user)
                        .build())).get();
    }

    public List<GetPostDto> PostListToGetPostDtoList(){
        List<Post> posts = repository.findAll();
        List<Post> postsPublicos = repository.findByTipoPublicacion(PostType.PUBLICA);
        if (posts.isEmpty() && postsPublicos.isEmpty()){
            return Collections.EMPTY_LIST;
        }else {
            return postsPublicos.stream().map(postDtoConverter::postToGetPostDto).collect(Collectors.toList());
        }
    }
    public List<GetPostDto> PostListToGetPostDtoListUsers(String user){
        List<Post> posts = repository.findAll();
        List<Post> postsUsers = repository.findAllByUserNombreUsuario(user);
        if (posts.isEmpty() && postsUsers.isEmpty()){
            return Collections.EMPTY_LIST;
        }else {
            return postsUsers.stream().map(postDtoConverter::postToGetPostDto).collect(Collectors.toList());
        }
    }
}
