package com.salesianostriana.edu.MiarmaProject.users.dto;

import com.salesianostriana.edu.MiarmaProject.model.Post;
import com.salesianostriana.edu.MiarmaProject.model.dto.follow.GetFollowDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.post.GetPostDto;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class GetUserDto {

    private UUID id;
    private String username;
    private String email;
    private String telefono;
    private String avatar;
    private String perfil;
    private LocalDate fecha;
    private List<GetPostDto> posts;
    private List<GetFollowDto> listaPeticiones;
    private List<GetUserWithoutLists> followers;

}
