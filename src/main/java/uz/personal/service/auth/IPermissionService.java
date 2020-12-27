package uz.personal.service.auth;

import uz.personal.criteria.auth.PermissionCriteria;
import uz.personal.domain.auth._Permission;
import uz.personal.dto.auth.PermissionCreateDto;
import uz.personal.dto.auth.PermissionDto;
import uz.personal.dto.auth.PermissionUpdateDto;
import uz.personal.service.IGenericCrudService;

public interface IPermissionService extends IGenericCrudService<_Permission, PermissionDto, PermissionCreateDto, PermissionUpdateDto, Long, PermissionCriteria> {

}
