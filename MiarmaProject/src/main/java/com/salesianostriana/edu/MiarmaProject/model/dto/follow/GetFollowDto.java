package com.salesianostriana.edu.MiarmaProject.model.dto.follow;

import lombok.*;

@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class GetFollowDto {

    private Long id;
    private String emisor;
    private String destinatario;
    private String mensaje;
}
