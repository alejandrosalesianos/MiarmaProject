package com.salesianostriana.edu.MiarmaProject.controllers;

import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.CreatePostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.PostDtoConverter;
import com.salesianostriana.edu.MiarmaProject.services.impl.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostDtoConverter postDtoConverter;

    @PostMapping("/")
    public ResponseEntity<GetPostDto> createPost(@RequestPart("post")CreatePostDto createPostDto, @RequestPart("file")MultipartFile file) throws IOException {

    Post post = postService.save(createPostDto,file);
    GetPostDto getPostDto = postDtoConverter.postToGetPostDto(post);
    return ResponseEntity.status(HttpStatus.CREATED).body(getPostDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GetPostDto> editPost(@PathVariable Long id, @RequestPart("file") MultipartFile file,@RequestPart("post")CreatePostDto createPostDto) throws IOException {

        Post post = postService.edit(id,createPostDto,file);
        GetPostDto postDto = postDtoConverter.postToGetPostDto(post);
        return ResponseEntity.ok().body(postDto);
    }
    //Aqui falta el delete
    /*@GetMapping("/public")
    public ResponseEntity<List<Post>> ListAllPublic(){

    }*/


}
