package uz.personal.dto.auth;

import lombok.*;
import uz.personal.dto.GenericDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends GenericDto {

    private String code;
    private String name;

    private List<PermissionDto> permissions;

    @Builder(builderMethodName = "childBuilder")
    public RoleDto(Long id, String code, String name, List<PermissionDto> permissions) {
        super(id);
        this.code = code;
        this.name = name;
        this.permissions = permissions;
    }
}
