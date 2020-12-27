package uz.personal.service.auth;

import org.springframework.http.ResponseEntity;
import uz.personal.dto.auth.*;
import uz.personal.response.DataDto;
import uz.personal.service.IAbstractService;

import javax.servlet.http.HttpServletRequest;

public interface IAuthService extends IAbstractService {

    ResponseEntity<DataDto<SessionDto>> login(AuthUserDto user, HttpServletRequest request);

    ResponseEntity<DataDto<Boolean>> logout(HttpServletRequest request);

    ResponseEntity<DataDto<SessionDto>> refreshToken(AuthUserRefreshTokenDto user, HttpServletRequest request);

    ResponseEntity<DataDto<Boolean>> changePassword(ChangePasswordDto dto);

    ResponseEntity<DataDto<Boolean>> resetPassword(ResetPasswordDto dto);
}
