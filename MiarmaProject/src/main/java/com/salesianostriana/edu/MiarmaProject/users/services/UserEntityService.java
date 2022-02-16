package com.salesianostriana.edu.MiarmaProject.users.services;

import com.salesianostriana.edu.MiarmaProject.exception.ListNotFoundException;
import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.services.base.BaseService;
import com.salesianostriana.edu.MiarmaProject.users.dto.CreateUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.GetUserDto;
import com.salesianostriana.edu.MiarmaProject.users.dto.UserDtoConverter;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.model.UserProfile;
import com.salesianostriana.edu.MiarmaProject.users.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service("userDetailService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;
    private final UserDtoConverter userDtoConverter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findFirstByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " No encontrado"));
    }
    public UserEntity findByUsername(String username){
        return this.repository.findByNombreUsuario(username).orElseThrow(() -> new UsernameNotFoundException(username +"No encontrado"));
    }

    public UserEntity saveUser(CreateUserDto userDto, MultipartFile file) throws IOException {

        if (userDto.getPassword().equals(userDto.getPassword2())) {

            String filename = storageService.store(file);

            String extension = StringUtils.getFilenameExtension(filename);

            BufferedImage originalImage = ImageIO.read(file.getInputStream());

            BufferedImage escaledImage = storageService.simpleResizer(originalImage,128);

            OutputStream outputStream = Files.newOutputStream(storageService.load(filename));

            ImageIO.write(escaledImage,extension,outputStream);


            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(filename)
                    .toUriString();

            UserEntity userEntity = UserEntity.builder()
                    .nombreUsuario(userDto.getNick())
                    .email(userDto.getEmail())
                    .fechaNacimiento(userDto.getFechaNacimiento())
                    .fotoPerfil(uri)
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .telefono(userDto.getTelefono())
                    .perfil(userDto.getPerfil())
                    .build();
            return repository.save(userEntity);
        } else {
            return null;
        }
    }
    public UserEntity edit(CreateUserDto createUserDto,MultipartFile file,UserEntity userEntity) throws IOException, ListNotFoundException {

        if (createUserDto.getPassword().equals(createUserDto.getPassword2())) {

            Optional<UserEntity> user = findById(userEntity.getId());
            if (!user.isPresent()) {
                throw new ListNotFoundException("No se encontro el usuario");
            } else {

                storageService.deleteFile(user.get().getFotoPerfil());

                String filename = storageService.store(file);

                String extension = StringUtils.getFilenameExtension(filename);

                BufferedImage originalImage = ImageIO.read(file.getInputStream());

                BufferedImage escaledImage = storageService.simpleResizer(originalImage,128);

                OutputStream outputStream = Files.newOutputStream(storageService.load(filename));

                ImageIO.write(escaledImage,extension,outputStream);


                String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/download/")
                        .path(filename)
                        .toUriString();

                return findById(user.get().getId()).map(newUser -> save(newUser.builder()
                        .id(userEntity.getId())
                        .nombreUsuario(createUserDto.getNick())
                        .email(createUserDto.getEmail())
                        .fechaNacimiento(createUserDto.getFechaNacimiento())
                        .fotoPerfil(uri)
                        .password(passwordEncoder.encode(createUserDto.getPassword()))
                        .telefono(createUserDto.getTelefono())
                        .perfil(createUserDto.getPerfil())
                        .posts(userEntity.getPosts())
                        .build())).get();
            }

        }else {
            return null;
        }
    }
    /*public List<GetUserDto> ListGetUserDto(){
        List<UserEntity> users = repository.findAll();
        if (users.isEmpty()){
            return Collections.EMPTY_LIST;
        }else {
            return users.stream().map(userDtoConverter::UserEntityToGetUserDto).collect(Collectors.toList());
        }
    }*/
}
