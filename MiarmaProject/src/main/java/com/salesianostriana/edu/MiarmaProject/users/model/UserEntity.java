package com.salesianostriana.edu.MiarmaProject.users.model;

import com.salesianostriana.edu.MiarmaProject.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(
        name = "UsuarioConPosts",attributeNodes = {
                @NamedAttributeNode("posts"),
                //@NamedAttributeNode("seguidores")
}
)
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private UUID id;

    private String nombreUsuario;

    private String email;

    private String fechaNacimiento;

    private String fotoPerfil;

    private String password;

    private String telefono;

    private UserProfile perfil;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    /*@OneToMany
    @JoinColumn(name = "Usuario-Seguidor",referencedColumnName = "ID")
    private List<UserEntity> seguidores = new ArrayList<>();*/



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USUARIO"));
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }






}
