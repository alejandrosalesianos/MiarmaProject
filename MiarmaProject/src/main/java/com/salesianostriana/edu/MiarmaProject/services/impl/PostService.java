package com.salesianostriana.edu.MiarmaProject.services.impl;

import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.model.PostType;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.PostDtoConverter;
import com.salesianostriana.edu.MiarmaProject.repositories.PostRepository;
import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.services.base.BaseService;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post,Long, PostRepository> {

    private final StorageService storageService;
    private final PostDtoConverter postDtoConverter;

    public Post save(Post post, MultipartFile file, UserEntity user) throws IOException {

        String filenameOriginal = storageService.store(file);
        String filename = storageService.store(file);

        String extension = StringUtils.getFilenameExtension(filename);

        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        BufferedImage escaledImage = storageService.simpleResizer(originalImage,1024);

        OutputStream outputStream = Files.newOutputStream(storageService.load(filename));

        ImageIO.write(escaledImage,extension,outputStream);

        String uriEscaled = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();
        String uriOriginal = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filenameOriginal)
                .toUriString();
        return repository.save(Post.builder()
                .contenido(post.getContenido())
                .contenidoMultimedia(uriEscaled)
                .contenidoOriginal(uriOriginal)
                .tipoPublicacion(post.getTipoPublicacion())
                .titulo(post.getTitulo())
                .user(user)
                .build());
    }
    public Post edit(Long id,Post post,MultipartFile file,UserEntity user) throws IOException {

            String filenameOriginal = storageService.store(file);
            String filename = storageService.store(file);

            String extension = StringUtils.getFilenameExtension(filename);

            BufferedImage originalImage = ImageIO.read(file.getInputStream());

            BufferedImage escaledImage = storageService.simpleResizer(originalImage,1024);

            OutputStream outputStream = Files.newOutputStream(storageService.load(filename));

            ImageIO.write(escaledImage,extension,outputStream);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(filename)
                    .toUriString();
            String uri2 = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(filenameOriginal)
                    .toUriString();


            return repository.findById(id).map(newPost ->
                    save(newPost.builder()
                            .id(id)
                            .titulo(post.getTitulo())
                            .tipoPublicacion(post.getTipoPublicacion())
                            .contenido(post.getContenido())
                            .contenidoMultimedia(uri)
                            .contenidoOriginal(uri2)
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
