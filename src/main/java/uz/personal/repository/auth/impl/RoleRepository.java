package uz.personal.repository.auth.impl;

import org.springframework.stereotype.Repository;
import uz.personal.criteria.auth.RoleCriteria;
import uz.personal.domain.auth._Role;
import uz.personal.repository.GenericDao;
import uz.personal.repository.auth.IRoleRepository;

import java.util.List;
import java.util.Map;

@Repository
public class RoleRepository extends GenericDao<_Role, RoleCriteria> implements IRoleRepository {

    @Override
    protected void defineCriteriaOnQuerying(RoleCriteria criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {

        if (!utils.isEmpty(criteria.getSelfId())) {
            whereCause.add("t.id = :selfId");
            params.put("selfId", criteria.getSelfId());
        }
        if (!utils.isEmpty(criteria.getCode())) {
            whereCause.add("t.code = :code");
            params.put("code", criteria.getCode());
        }
        if (!utils.isEmpty(criteria.getName())) {
            whereCause.add(("t.name = :name"));
            params.put("name", criteria.getName());
        }

        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }
}
