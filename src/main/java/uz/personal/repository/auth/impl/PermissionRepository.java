package uz.personal.repository.auth.impl;

import org.springframework.stereotype.Repository;
import uz.personal.criteria.auth.PermissionCriteria;
import uz.personal.domain.auth._Permission;
import uz.personal.repository.GenericDao;
import uz.personal.repository.auth.IPermissionRepository;

import java.util.List;
import java.util.Map;

@Repository
public class PermissionRepository extends GenericDao<_Permission, PermissionCriteria> implements IPermissionRepository {

    @Override
    protected void defineCriteriaOnQuerying(PermissionCriteria criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {

        if (!utils.isEmpty(criteria.getSelfId())) {
            whereCause.add("t.id = :selfId");
            params.put("selfId", criteria.getSelfId());
        }
        if (!utils.isEmpty(criteria.getCode())) {
            whereCause.add("t.code = :code");
            params.put("code", criteria.getCode());
        }
        if (!utils.isEmpty(criteria.getName())) {
            whereCause.add("t.name = :name");
            params.put("name", criteria.getName());
        }
        if (!utils.isEmpty(criteria.getParentId())) {
            whereCause.add("t.parentId = :parentId");
            params.put("parentId", criteria.getParentId());
        }

        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }
}
