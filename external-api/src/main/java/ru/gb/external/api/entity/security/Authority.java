package ru.gb.external.api.entity.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String permission;

    @ManyToMany(mappedBy = "authorities")
    private Set<AccountRole> roles;


    @Override
    public String getAuthority() {
        return this.permission;
    }
}
