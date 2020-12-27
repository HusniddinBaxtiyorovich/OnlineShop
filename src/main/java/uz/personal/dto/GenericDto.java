package uz.personal.dto;

import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericDto implements Dto, Serializable {

    @ApiModelProperty(required = true, example = "1")
    private Long id;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
