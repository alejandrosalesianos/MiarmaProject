package com.salesianostriana.edu.MiarmaProject.controllers;

import com.salesianostriana.edu.MiarmaProject.error.exception.ListNotFoundException;
import com.salesianostriana.edu.MiarmaProject.error.exception.NotFollowingException;
import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.model.PostType;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.CreatePostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.PostDtoConverter;
import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.services.impl.PostService;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.model.UserProfile;
import com.salesianostriana.edu.MiarmaProject.users.repository.UserEntityRepository;
import com.salesianostriana.edu.MiarmaProject.utils.PaginationLinksUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final StorageService storageService;
    private final PostDtoConverter postDtoConverter;
    private final UserEntityRepository userEntityRepository;
    private final PaginationLinksUtil paginationLinksUtil;

    @PostMapping("/")
    public ResponseEntity<GetPostDto> createPost(@RequestParam("titulo")String titulo,@RequestParam("tipoPubli")PostType tipoPublicacion,@RequestParam("contenido")String contenido, @RequestPart("file")MultipartFile file, @AuthenticationPrincipal UserEntity user) throws IOException {

        CreatePostDto createPostDto = CreatePostDto.builder()
                .titulo(titulo)
                .contenido(contenido)
                .tipoPublicacion(tipoPublicacion)
                .build();
    Post post = postService.save(postDtoConverter.createPostDtoToPost(createPostDto, file),file,user);
    GetPostDto getPostDto = postDtoConverter.postToGetPostDtoWithUser(post,user);
    return ResponseEntity.created(URI.create(post.getContenidoMultimedia())).body(getPostDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GetPostDto> editPost(@PathVariable Long id, @RequestPart("file") MultipartFile file,@RequestPart("post")CreatePostDto post,@AuthenticationPrincipal UserEntity user) throws IOException, ListNotFoundException {

        Post post2 = postDtoConverter.createPostDtoToPost(post,file);
        Post actualPost = postService.edit(id,post2,file,user);
        GetPostDto postDto = postDtoConverter.postToGetPostDtoWithUser(actualPost,user);
        return ResponseEntity.ok().body(postDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            storageService.deleteFile(post.get().getContenidoMultimedia());
            storageService.deleteFile(post.get().getContenidoOriginal());
            postService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<GetPostDto> findOnePost (@PathVariable Long id,@AuthenticationPrincipal UserEntity user) throws NotFollowingException {
        Optional<Post> post = postService.findById(id);
        if (!post.isPresent()){
            return ResponseEntity.notFound().build();
        }
        if (post.get().getTipoPublicacion() == PostType.PRIVADA && !post.get().getUser().getFollowers().contains(user)){
            throw new NotFollowingException("No sigues al usuario "+post.get().getUser().getNombreUsuario());
        }
        else {
            return ResponseEntity.ok().body(postDtoConverter.postToGetPostDto(post.get()));
        }
    }


    @GetMapping("/public")
    public ResponseEntity<Page<GetPostDto>> listAllPublic(@PageableDefault(size = 100) Pageable pageable, HttpServletRequest request) throws ListNotFoundException {
        Page<GetPostDto> listPosts = postService.PostListToGetPostDtoList(pageable);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
        return ResponseEntity.ok().header("link",paginationLinksUtil.createLinkHeader(listPosts,uriBuilder)).body(listPosts);
    }
    @GetMapping("/")
    public ResponseEntity<List<GetPostDto>> listAllPostsByNick(@RequestParam(value = "nick") String nick, @AuthenticationPrincipal UserEntity user) throws NotFollowingException, ListNotFoundException {
        Optional<UserEntity> user1 = userEntityRepository.findByNombreUsuario(nick);
        if (!user1.isPresent()){
            return ResponseEntity.notFound().build();
        }if (user1.get().getPerfil() == UserProfile.PRIVADO &&
                userEntityRepository.findAllFollowers(user1.get().getId()).get(0).getFollowers().contains(user)){
            throw new NotFollowingException("No sigues a este usuario");
        }
        else{
            return ResponseEntity.ok().body(postService.PostListToGetPostDtoListUsers(nick));
        }
    }
    @GetMapping("/me")
    public ResponseEntity<List<GetPostDto>> listAllMyPosts(@AuthenticationPrincipal UserEntity user) throws ListNotFoundException {

        return ResponseEntity.ok().body(postService.PostListToGetPostDtoListUsers(user.getUsername()));
    }


}
