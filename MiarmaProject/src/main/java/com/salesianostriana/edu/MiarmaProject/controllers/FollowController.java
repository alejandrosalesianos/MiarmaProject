package com.salesianostriana.edu.MiarmaProject.controllers;

import com.salesianostriana.edu.MiarmaProject.model.PeticionFollow;
import com.salesianostriana.edu.MiarmaProject.model.dto.follow.CreateFollowDto;
import com.salesianostriana.edu.MiarmaProject.model.dto.follow.FollowDtoConverter;
import com.salesianostriana.edu.MiarmaProject.model.dto.follow.GetFollowDto;
import com.salesianostriana.edu.MiarmaProject.services.impl.FollowService;
import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import com.salesianostriana.edu.MiarmaProject.users.repository.UserEntityRepository;
import com.salesianostriana.edu.MiarmaProject.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowDtoConverter followDtoConverter;
    private final FollowService followService;
    private final UserEntityService userEntityService;

    @PostMapping("/{nick}")
    public ResponseEntity<GetFollowDto> postFollow(@RequestBody CreateFollowDto createFollowDto, @PathVariable String nick, @AuthenticationPrincipal UserEntity user){
        UserEntity userEmisor = userEntityService.findByUsername(nick);
        PeticionFollow peticionFollow = followService.save(createFollowDto,userEmisor,user);
        return ResponseEntity.status(HttpStatus.CREATED).body(followDtoConverter.FollowToGetFollowDto(peticionFollow));
    }

}
