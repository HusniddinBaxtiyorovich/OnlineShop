package uz.personal.criteria.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.personal.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCriteria extends GenericCriteria {

    private String fullName;

    private String username;

    private String email;

    private String phone;

    @ApiModelProperty(hidden = true)
    private boolean forAuthenticate;

    @ApiModelProperty(hidden = true)
    private boolean onlyId;

    @ApiModelProperty(hidden = true)
    private boolean forDetails;

    @Builder(builderMethodName = "childBuilder")
    public UserCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String fullName, String username, String email, String phone, boolean forAuthenticate, boolean onlyId, boolean forDetails) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.forAuthenticate = forAuthenticate;
        this.onlyId = onlyId;
        this.forDetails = forDetails;
    }
}
