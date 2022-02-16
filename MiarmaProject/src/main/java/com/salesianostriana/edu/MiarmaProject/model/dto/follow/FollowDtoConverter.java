package com.salesianostriana.edu.MiarmaProject.model.dto.follow;

import com.salesianostriana.edu.MiarmaProject.model.PeticionFollow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowDtoConverter {


    public PeticionFollow createFollowDtoToPeticionFollow(CreateFollowDto createFollowDto){
        return PeticionFollow.builder()
                .mensaje(createFollowDto.getMensaje())
                .build();
    }
    public GetFollowDto FollowToGetFollowDto(PeticionFollow follow){
        return GetFollowDto.builder()
                .id(follow.getId())
                .destinatario(follow.getDestinatario().getUsername())
                .emisor(follow.getEmisor().getUsername())
                .mensaje(follow.getMensaje())
                .build();
    }
}
