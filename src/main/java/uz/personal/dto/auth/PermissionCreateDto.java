package uz.personal.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.personal.dto.GenericCrudDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Permission create request")
public class PermissionCreateDto extends GenericCrudDto {

    @ApiModelProperty(value = "CODE", required = true)
    private String code;

    @ApiModelProperty(required = true)
    private String name;

    private Long parentId;
}
