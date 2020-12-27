package uz.personal.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.personal.dto.GenericDto;
import uz.personal.enums.State;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMeDto extends GenericDto {

    private String firstName;

    private String lastName;

    private String middleName;

    private String shortName;

    private String username;

    private String email;

    private String phone;

    private State state;

    private String userTypeName;

    @ApiModelProperty(hidden = true)
    private boolean locked;

    @ApiModelProperty(hidden = true)
    private boolean systemAdmin;

    @Builder(builderMethodName = "childBuilder")
    public UserMeDto(Long id, String firstName, String lastName, String middleName, String shortName, String username, String email, String phone, String userTypeName, boolean locked, boolean systemAdmin) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.shortName = shortName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.userTypeName = userTypeName;
        this.locked = locked;
        this.systemAdmin = systemAdmin;
    }
}