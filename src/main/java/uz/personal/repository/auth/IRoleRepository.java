package uz.personal.repository.auth;

import uz.personal.criteria.auth.RoleCriteria;
import uz.personal.domain.auth._Role;
import uz.personal.repository.IGenericCrudRepository;

public interface IRoleRepository extends IGenericCrudRepository<_Role, RoleCriteria> {
}
