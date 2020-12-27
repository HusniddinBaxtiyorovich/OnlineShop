package uz.personal.dto.auth;

import lombok.*;
import uz.personal.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto extends GenericDto {

    private String code;
    private String name;

    private Long parentId;

    @Builder(builderMethodName = "childBuilder")
    public PermissionDto(Long id, String code, String name, Long parentId) {
        super(id);
        this.code = code;
        this.name = name;
        this.parentId = parentId;
    }
}
