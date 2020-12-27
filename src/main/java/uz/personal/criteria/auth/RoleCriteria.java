package uz.personal.criteria.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.personal.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleCriteria extends GenericCriteria {

    private String code;

    private String name;

    @ApiModelProperty(hidden = true)
    private boolean permissionOnly;

    @Builder(builderMethodName = "childBuilder")
    public RoleCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String code, String name, boolean permissionOnly) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.code = code;
        this.name = name;
        this.permissionOnly = permissionOnly;
    }
}
