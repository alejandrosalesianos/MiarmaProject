package com.salesianostriana.edu.MiarmaProject.services.impl;

import com.salesianostriana.edu.MiarmaProject.model.PeticionFollow;
import com.salesianostriana.edu.MiarmaProject.model.dto.follow.CreateFollowDto;
import com.salesianostriana.edu.MiarmaProject.repositories.FollowRepository;
import com.salesianostriana.edu.MiarmaProject.services.base.BaseService;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService extends BaseService<PeticionFollow,Long, FollowRepository> {

    public PeticionFollow save(CreateFollowDto dto,UserEntity userEmisor, UserEntity userDestinatario){
        return repository.save(PeticionFollow.builder()
                .mensaje(dto.getMensaje())
                .emisor(userEmisor)
                .destinatario(userDestinatario)
                .build());
    }
}
