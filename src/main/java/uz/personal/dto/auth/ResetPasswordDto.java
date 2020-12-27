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
@ApiModel(value = "Reset Password request")
public class ResetPasswordDto {

    @ApiModelProperty(required = true)
    private Long userId;

    @ApiModelProperty(required = true)
    private String password;
}
