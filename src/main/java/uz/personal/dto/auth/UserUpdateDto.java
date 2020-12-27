package uz.personal.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.personal.dto.GenericCrudDto;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User update request")
public class UserUpdateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private Long id;

    @Size(max = 100, message = " max size %s")
    private String firstName;

    @Size(max = 100, message = " max size %s")
    private String lastName;

    @Size(max = 100, message = " max size %s")
    private String middleName;

    @ApiModelProperty(example = "simple@gmail.com")
    private String email;

    @ApiModelProperty(example = "+998977777777")
    @Size(max = 13, message = " max size %s")
    private String phone;
}
