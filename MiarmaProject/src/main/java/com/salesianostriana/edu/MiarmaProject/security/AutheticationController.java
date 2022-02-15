package com.salesianostriana.edu.MiarmaProject.security;

import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import com.salesianostriana.edu.MiarmaProject.security.dto.LoginDto;
import com.salesianostriana.edu.MiarmaProject.security.dto.UsuarioResponseJwt;
import com.salesianostriana.edu.MiarmaProject.security.jwt.JwtProvider;
import com.salesianostriana.edu.MiarmaProject.users.dto.UserDtoConverter;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AutheticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDtoConverter userDtoConverter;

    String jwt = "";

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        jwt = jwtProvider.generateToken(authentication);

        UserEntity user = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(userToUsuarioResponseJwt(user,jwt));
    }
    private UsuarioResponseJwt userToUsuarioResponseJwt(UserEntity user, String jwt){
        return UsuarioResponseJwt.builder()
                .nick(user.getNombreUsuario())
                .email(user.getEmail())
                .avatar(user.getFotoPerfil())
                .perfil(user.getPerfil().name())
                .posts(user.getPosts()
                        .stream().map(
                                p -> GetPostDto.builder()
                                        .id(p.getId())
                                        .titulo(p.getTitulo())
                                        .contenidoOriginal(p.getContenidoOriginal())
                                        .contenido(p.getContenido())
                                        .tipoPublicacion(p.getTipoPublicacion().name())
                                        .contenidoMultimedia(p.getContenidoMultimedia())
                                        .user(user.getUsername())
                                        .build())
                        .collect(Collectors.toList()))
                .token(jwt)
                .build();
    }
    @GetMapping("/me")
    public ResponseEntity<?> myProfile(@AuthenticationPrincipal UserEntity user) throws Exception {
        return ResponseEntity.ok(userDtoConverter.UserEntityToGetUserDto(user));
    }
}
