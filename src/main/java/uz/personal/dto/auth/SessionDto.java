package uz.personal.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDto {

    private String sessionToken;
    private String refreshToken;

    private Long expiresIn;

    private UserMeDto user;
}
