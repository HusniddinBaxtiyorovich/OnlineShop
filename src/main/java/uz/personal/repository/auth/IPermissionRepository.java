package uz.personal.repository.auth;

import uz.personal.criteria.auth.PermissionCriteria;
import uz.personal.domain.auth._Permission;
import uz.personal.repository.IGenericCrudRepository;

public interface IPermissionRepository extends IGenericCrudRepository<_Permission, PermissionCriteria> {
}
