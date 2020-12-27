package uz.personal.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.personal.controller.ApiController;
import uz.personal.dto.auth.*;
import uz.personal.response.DataDto;
import uz.personal.service.auth.IAuthService;

import javax.servlet.http.HttpServletRequest;

@Api(value = "Auth controller")
@RestController
public class AuthController extends ApiController<IAuthService> {

    public AuthController(IAuthService service) {
        super(service);
    }

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.POST)
    public ResponseEntity<DataDto<SessionDto>> login(@RequestBody AuthUserDto dto, HttpServletRequest request) {
        return service.login(dto, request);
    }

    @RequestMapping(value = LOGOUT_URL, method = RequestMethod.GET)
    public ResponseEntity<DataDto<Boolean>> logout(HttpServletRequest request) {
        return service.logout(request);
    }

    @RequestMapping(value = REFRESH_TOKEN_URL, method = RequestMethod.POST)
    public ResponseEntity<DataDto<SessionDto>> refreshToken(@RequestBody AuthUserRefreshTokenDto dto, HttpServletRequest request) {
        return service.refreshToken(dto, request);
    }

    @ApiOperation(value = "Change User Password")
    @RequestMapping(value = AUTH + "/change/password", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Boolean>> changePassword(@RequestBody ChangePasswordDto dto) {
        return service.changePassword(dto);
    }

    @ApiOperation(value = "Reset User Password")
    @RequestMapping(value = AUTH + "/reset/password", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Boolean>> resetPassword(@RequestBody ResetPasswordDto dto) {
        return service.resetPassword(dto);
    }
}
