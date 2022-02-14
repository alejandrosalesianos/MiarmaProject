package com.salesianostriana.edu.MiarmaProject.users.services;

import com.salesianostriana.edu.MiarmaProject.services.StorageService;
import com.salesianostriana.edu.MiarmaProject.services.base.BaseService;
import com.salesianostriana.edu.MiarmaProject.users.dto.CreateUserDto;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.model.UserProfile;
import com.salesianostriana.edu.MiarmaProject.users.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service("userDetailService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findFirstByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " No encontrado"));
    }
    public List<UserEntity> loadUserByProfile(UserProfile userProfile) throws UsernameNotFoundException{
        return this.repository.findByPerfil(userProfile).orElseThrow(() -> new UsernameNotFoundException(userProfile+" No encontrado"));
    }
    public UserEntity loadUserById(UUID id) throws UsernameNotFoundException{
        return this.repository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id+" No encontrado"));
    }

    public UserEntity saveUser(CreateUserDto userDto, MultipartFile file) throws IOException {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        if (userDto.getPassword().equals(userDto.getPassword2())) {
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
}
