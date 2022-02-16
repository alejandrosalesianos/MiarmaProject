package com.salesianostriana.edu.MiarmaProject.services.impl;

import com.salesianostriana.edu.MiarmaProject.model.PeticionFollow;
import com.salesianostriana.edu.MiarmaProject.model.dto.follow.CreateFollowDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.follow.FollowDtoConverter;
import com.salesianostriana.edu.MiarmaProject.model.dto.follow.GetFollowDto;
import com.salesianostriana.edu.MiarmaProject.repositories.FollowRepository;
import com.salesianostriana.edu.MiarmaProject.services.base.BaseService;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService extends BaseService<PeticionFollow,Long, FollowRepository> {

    private final FollowDtoConverter followDtoConverter;

    public PeticionFollow save(CreateFollowDto dto,UserEntity userEmisor, UserEntity userDestinatario){
        return repository.save(PeticionFollow.builder()
                .mensaje(dto.getMensaje())
                .emisor(userEmisor)
                .destinatario(userDestinatario)
                .build());
    }
    public List<GetFollowDto> findUserById(UUID uuid){
        List<PeticionFollow> lista = repository.findAll();
        List<PeticionFollow> listaPeticiones = repository.findByEmisorId(uuid);
        if (lista.isEmpty() && listaPeticiones.isEmpty()){
            return Collections.EMPTY_LIST;
        }else {
            return listaPeticiones.stream().map(followDtoConverter::FollowToGetFollowDto).collect(Collectors.toList());
        }
    }

    public void acceptPeticionFollow(Long id, UserEntity user){

        Optional<PeticionFollow> peticionFollow = findById(id);
        if (!peticionFollow.isPresent()){
        }
        else {
            if (peticionFollow.get().getDestinatario().equals(user)){
                user.getFollowers().add(peticionFollow.get().getEmisor());
                deleteById(id);
            }
            else {
            }
        }

    }
}
