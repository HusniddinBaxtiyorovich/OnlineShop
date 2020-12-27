package uz.personal.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.personal.dto.GenericCrudDto;
import uz.personal.enums.UserType;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User create request")
public class UserCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    @Size(max = 100, message = " max size %s")
    private String firstName;

    @ApiModelProperty(required = true)
    @Size(max = 100, message = " max size %s")
    private String lastName;

    @Size(max = 100, message = " max size %s")
    private String middleName;

    @ApiModelProperty
    private UserType userType;

    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private String password;

    @ApiModelProperty(example = "simple@gmail.com")
    private String email;

    @ApiModelProperty(example = "+998977777777")
    @Size(max = 13, message = " max size %s")
    private String phone;
}
