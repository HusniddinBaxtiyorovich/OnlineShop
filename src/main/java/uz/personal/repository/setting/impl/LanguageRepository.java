package uz.personal.repository.setting.impl;

import org.springframework.stereotype.Repository;
import uz.personal.criteria.setting.LanguageCriteria;
import uz.personal.domain.setting._Language;
import uz.personal.repository.GenericDao;
import uz.personal.repository.setting.ILanguageRepository;

import java.util.List;
import java.util.Map;

@Repository
public class LanguageRepository extends GenericDao<_Language, LanguageCriteria> implements ILanguageRepository {

    @Override
    protected void onDefineWhereCause(LanguageCriteria criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {

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

        super.onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }
}
