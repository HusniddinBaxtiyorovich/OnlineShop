package uz.personal.domain.auth;

import lombok.*;
import org.springframework.util.StringUtils;
import uz.personal.domain.Auditable;
import uz.personal.enums.UserType;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auth_users")
public class _User extends Auditable {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "is_locked", columnDefinition = "boolean default false")
    private boolean locked;
    @Column(name = "is_system_admin", columnDefinition = "boolean default false")
    private boolean systemAdmin;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "auth_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Collection<_Role> roles;

    public String getShortName() {
        return String.format("%s %s",
                StringUtils.isEmpty(lastName) ? "" : lastName,
                StringUtils.isEmpty(firstName) ? "" : firstName.substring(0, 1));
    }
}
