package com.salesianostriana.edu.MiarmaProject.model;

import com.salesianostriana.edu.MiarmaProject.users.model.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class PeticionFollow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private UserEntity emisor;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private UserEntity destinatario;

    private String mensaje;
}
