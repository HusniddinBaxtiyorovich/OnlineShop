package uz.personal.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.personal.controller.ApiController;
import uz.personal.criteria.auth.PermissionCriteria;
import uz.personal.dto.GenericDto;
import uz.personal.dto.auth.PermissionCreateDto;
import uz.personal.dto.auth.PermissionDto;
import uz.personal.dto.auth.PermissionUpdateDto;
import uz.personal.response.DataDto;
import uz.personal.service.auth.IPermissionService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Permission controller", tags = "auth-permission-controller")
@RestController
public class PermissionController extends ApiController<IPermissionService> {

    public PermissionController(IPermissionService service) {
        super(service);
    }

    @ApiOperation(value = "Get Single Permission ")
    @RequestMapping(value = API_PATH + V_1 + "/permission/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<PermissionDto>> getPermission(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Get List Permission")
    @RequestMapping(value = API_PATH + V_1 + "/permission/get/all", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<PermissionDto>>> getAllPermissions(@Valid PermissionCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Permission Create")
    @RequestMapping(value = API_PATH + V_1 + "/permission/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createPermission(@RequestBody PermissionCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Permission Update")
    @RequestMapping(value = API_PATH + V_1 + "/permission/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<PermissionDto>> updatePermission(@RequestBody PermissionUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Permission Delete")
    @RequestMapping(value = API_PATH + V_1 + "/permission/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deletePermission(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }
}
