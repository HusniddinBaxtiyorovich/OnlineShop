package uz.personal.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.personal.dto.GenericDto;
import uz.personal.enums.State;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends GenericDto {

    private String firstName;

    private String lastName;

    private String middleName;

    private String shortName;

    private String username;

    private String email;

    private String phone;

    private State state;

    private List<RoleDto> roles;

    private String userTypeName;

    @ApiModelProperty(hidden = true)
    private boolean locked;

    @ApiModelProperty(hidden = true)
    private boolean systemAdmin;

    @Builder(builderMethodName = "childBuilder")
    public UserDto(Long id, String firstName, String lastName, String middleName, String shortName, String username, String email, String phone, State state, List<RoleDto> roles, String userTypeName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.shortName = shortName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.state = state;
        this.roles = roles;
        this.userTypeName = userTypeName;
    }
}
