package uz.personal.service.auth;

import uz.personal.criteria.auth.RoleCriteria;
import uz.personal.domain.auth._Role;
import uz.personal.dto.auth.RoleCreateDto;
import uz.personal.dto.auth.RoleDto;
import uz.personal.dto.auth.RoleUpdateDto;
import uz.personal.service.IGenericCrudService;

public interface IRoleService extends IGenericCrudService<_Role, RoleDto, RoleCreateDto, RoleUpdateDto, Long, RoleCriteria> {

}