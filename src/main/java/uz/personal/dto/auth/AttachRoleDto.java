package uz.personal.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.personal.dto.GenericDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Attaching roles To User request ")
public class AttachRoleDto {

    @ApiModelProperty(required = true, example = "1")
    private Long userId;

    @ApiModelProperty(required = true)
    private List<GenericDto> roles;

    @Builder(builderMethodName = "childBuilder")
    public AttachRoleDto(Long userId, List<GenericDto> roles) {
        this.userId = userId;
        this.roles = roles;
    }
}
