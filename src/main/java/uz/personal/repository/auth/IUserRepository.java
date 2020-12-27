package uz.personal.repository.auth;

import uz.personal.criteria.auth.UserCriteria;
import uz.personal.domain.auth._User;
import uz.personal.repository.IGenericCrudRepository;

public interface IUserRepository extends IGenericCrudRepository<_User, UserCriteria> {

    _User findByUsername(String username);

}
