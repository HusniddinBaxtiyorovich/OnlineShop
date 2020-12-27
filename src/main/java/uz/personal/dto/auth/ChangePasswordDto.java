package uz.personal.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Change Password request")
public class ChangePasswordDto {

    @ApiModelProperty(required = true)
    private String currentPassword;

    @ApiModelProperty(required = true)
    private String newPassword;
}
