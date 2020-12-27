package uz.personal.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.personal.controller.ApiController;
import uz.personal.criteria.auth.UserCriteria;
import uz.personal.dto.GenericDto;
import uz.personal.dto.auth.AttachRoleDto;
import uz.personal.dto.auth.UserCreateDto;
import uz.personal.dto.auth.UserDto;
import uz.personal.dto.auth.UserUpdateDto;
import uz.personal.response.DataDto;
import uz.personal.service.auth.IUserService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "User controller", tags = "auth-user-controller", description = "Foydalanuvchilar")
@RestController
public class UserController extends ApiController<IUserService> {

    public UserController(IUserService service) {
        super(service);
    }

    @ApiOperation(value = "Get Single User")
    @RequestMapping(value = API_PATH + V_1 + "/user/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<UserDto>> getUser(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Get List Users")
    @RequestMapping(value = API_PATH + V_1 + "/user/get/all", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<UserDto>>> getAllUsers(@Valid UserCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "User Create")
    @RequestMapping(value = API_PATH + V_1 + "/user/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createUser(@RequestBody UserCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "User Update")
    @RequestMapping(value = API_PATH + V_1 + "/user/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<UserDto>> updateUser(@RequestBody UserUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "User Delete")
    @RequestMapping(value = API_PATH + V_1 + "/user/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteUser(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

    @ApiOperation(value = "Role Attach To User")
    @RequestMapping(value = API_PATH + V_1 + "/user/attach/role", method = RequestMethod.POST)
    public ResponseEntity<DataDto<UserDto>> attachRoles(@RequestBody AttachRoleDto dto) {
        return service.attachRolesToUser(dto);
    }
}
