package uz.personal.repository.auth.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.personal.criteria.auth.UserCriteria;
import uz.personal.domain.auth._User;
import uz.personal.repository.GenericDao;
import uz.personal.repository.auth.IUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository extends GenericDao<_User, UserCriteria> implements IUserRepository {

    /**
     * Common logger for use in subclasses.
     */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected EntityManager entityManager;

    @Override
    protected void defineCriteriaOnQuerying(UserCriteria criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {

        if (!utils.isEmpty(criteria.getSelfId())) {
            whereCause.add("t.id = :selfId");
            params.put("selfId", criteria.getSelfId());
        }
        if (!utils.isEmpty(criteria.getUsername())) {
            whereCause.add("t.username = :username");
            params.put("username", criteria.getUsername());
        }

        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }

    @Override
    protected Query defineQuerySelect(UserCriteria criteria, StringBuilder queryBuilder, boolean onDefineCount) {
        String queryStr;
        if (criteria.isOnlyId()) {
            queryStr = " select" + (onDefineCount ? " count(t) " : " t.id ") + " from _User t " +
                    joinStringOnQuerying(criteria) +
                    " where t.state <> 2 " + queryBuilder.toString() + (onDefineCount ? "" : onSortBy(criteria).toString());
            return entityManager.createQuery(queryStr);
        } else {
            queryStr = " select " + (onDefineCount ? " count(t) " : " t ") + " from _User t " +
                    joinStringOnQuerying(criteria) +
                    " where t.state <> 2 " + queryBuilder.toString() + (onDefineCount ? "" : onSortBy(criteria).toString());
            return onDefineCount ? entityManager.createQuery(queryStr, Long.class) : entityManager.createQuery(queryStr);
        }
    }

    @Override
    public _User findByUsername(String username) {
        try {
            return (_User) entityManager.createQuery("SELECT t FROM _User t WHERE t.username = '" + username + "' ORDER BY t.id DESC ").getSingleResult();
        } catch (NoResultException ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(String.format("User with username '%s' not found", username));
        }
    }
}
