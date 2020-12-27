package uz.personal.domain.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import uz.personal.domain.Auditable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auth_roles")
public class _Role extends Auditable implements GrantedAuthority {

    @Column(unique = true)
    private String code;

    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "auth_roles_permissions",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private Collection<_Permission> permissions;

    @Override
    public String getAuthority() {
        return "ROLE_" + getCode();
    }
}
