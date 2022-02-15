package com.salesianostriana.edu.MiarmaProject.controllers;

import com.salesianostriana.edu.MiarmaProject.exception.ListNotFoundException;
import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.model.PostType;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.CreatePostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.PostDtoConverter;
import com.salesianostriana.edu.MiarmaProject.repositories.PostRepository;
import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.services.impl.PostService;
import com.salesianostriana.edu.MiarmaProject.users.dto.GetUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.UserDtoConverter;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final StorageService storageService;
    private final UserDtoConverter userDtoConverter;
    private final PostDtoConverter postDtoConverter;
    private final PostRepository postRepository;

    @PostMapping("/")
    public ResponseEntity<GetPostDto> createPost(@RequestPart("post")CreatePostDto createPostDto, @RequestPart("file")MultipartFile file, @AuthenticationPrincipal UserEntity user) throws IOException {

    Post post = postService.save(postDtoConverter.createPostDtoToPost(createPostDto, file),file,user);
    GetPostDto getPostDto = postDtoConverter.postToGetPostDtoWithUser(post,user);
    return ResponseEntity.created(URI.create(post.getContenidoMultimedia())).body(getPostDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GetPostDto> editPost(@PathVariable Long id, @RequestPart("file") MultipartFile file,@RequestPart("post")Post post,@AuthenticationPrincipal UserEntity user) throws IOException {

        Post actualPost = postService.edit(id,post,file,user);
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
            postService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
    @GetMapping("/public")
    public ResponseEntity<List<GetPostDto>> ListAllPublic() throws ListNotFoundException {
        return ResponseEntity.ok().body(postService.PostListToGetPostDtoList());
    }
    @GetMapping("/{nick}")
    public ResponseEntity<List<GetPostDto>> ListAllPostsByNick(@PathVariable String nick){
        return ResponseEntity.ok().body(postService.PostListToGetPostDtoListUsers(nick));
    }


}
