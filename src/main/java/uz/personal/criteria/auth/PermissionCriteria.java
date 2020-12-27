package uz.personal.criteria.auth;

import lombok.*;
import uz.personal.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCriteria extends GenericCriteria {

    private String code;

    private String name;

    private Long parentId;

    @Builder(builderMethodName = "childBuilder")
    public PermissionCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String code, String name, Long parentId) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.code = code;
        this.name = name;
        this.parentId = parentId;
    }
}
