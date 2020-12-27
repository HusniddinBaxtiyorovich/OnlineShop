package uz.personal.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.personal.controller.ApiController;
import uz.personal.criteria.auth.RoleCriteria;
import uz.personal.dto.GenericDto;
import uz.personal.dto.auth.RoleCreateDto;
import uz.personal.dto.auth.RoleDto;
import uz.personal.dto.auth.RoleUpdateDto;
import uz.personal.response.DataDto;
import uz.personal.service.auth.IRoleService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Role controller", tags = "auth-role-controller")
@RestController
public class RoleController extends ApiController<IRoleService> {

    public RoleController(IRoleService service) {
        super(service);
    }

    @ApiOperation(value = "Get Single Role")
    @RequestMapping(value = API_PATH + V_1 + "/role/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<RoleDto>> getRole(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Get List Role")
    @RequestMapping(value = API_PATH + V_1 + "/role/get/all", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<RoleDto>>> getAllRoles(@Valid RoleCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Role Create")
    @RequestMapping(value = API_PATH + V_1 + "/role/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createRole(@RequestBody RoleCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Role update")
    @RequestMapping(value = API_PATH + V_1 + "/role/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<RoleDto>> updateRole(@RequestBody RoleUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Role delete")
    @RequestMapping(value = API_PATH + V_1 + "/role/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteRole(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }
}
