package uz.personal.domain.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import uz.personal.domain.Auditable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auth_permissions")
public class _Permission extends Auditable implements GrantedAuthority {

    @Column(unique = true)
    private String code;

    @Column
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Override
    public String getAuthority() {
        return getCode();
    }
}
