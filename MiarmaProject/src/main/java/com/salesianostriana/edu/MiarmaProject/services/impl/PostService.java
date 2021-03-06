package com.salesianostriana.edu.MiarmaProject.services.impl;

import com.salesianostriana.edu.MiarmaProject.error.exception.ListNotFoundException;
import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.model.PostType;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.PostDtoConverter;
import com.salesianostriana.edu.MiarmaProject.repositories.PostRepository;
import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.services.base.BaseService;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Post edit(Long id,Post post,MultipartFile file,UserEntity user) throws IOException, ListNotFoundException {

            Optional<Post> post1 = findById(id);
            if (!post1.isPresent()){
                throw new ListNotFoundException("No se encontro el post");
            }else {
                storageService.deleteFile(post1.get().getContenidoOriginal());
                storageService.deleteFile(post1.get().getContenidoMultimedia());

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
    }



    public Page<GetPostDto> PostListToGetPostDtoList(Pageable pageable) throws ListNotFoundException {
        Page<Post> postsPublicos = repository.findByTipoPublicacion(PostType.PUBLICA,pageable);
            return new PageImpl<>(postsPublicos.stream().map(postDtoConverter::postToGetPostDto).collect(Collectors.toList()));
    }
    public List<GetPostDto> PostListToGetPostDtoListUsers(String user) throws ListNotFoundException {
        List<Post> postsUsers = repository.findAllByUserNombreUsuario(user);
            return postsUsers.stream().map(postDtoConverter::postToGetPostDto).collect(Collectors.toList());
    }
    public List<GetPostDto> PostListEntityGraph(UserEntity user){
        List<Post> postList = repository.findByUserId(user.getId());
            return postList.stream().map(postDtoConverter::postToGetPostDto).collect(Collectors.toList());
    }
}
