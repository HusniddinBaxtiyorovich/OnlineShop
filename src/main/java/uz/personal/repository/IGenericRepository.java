package uz.personal.repository;

import uz.personal.criteria.GenericCriteria;
import uz.personal.domain.Auditable;

import java.util.List;

public interface IGenericRepository<T extends Auditable, C extends GenericCriteria> extends IAbstractRepository {

    T find(Long id);

    T find(C criteria);

    Long getTotalCount(C criteria);

    List<T> findAll(C criteria);
}
