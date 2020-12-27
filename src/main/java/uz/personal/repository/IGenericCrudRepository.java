package uz.personal.repository;

import uz.personal.criteria.GenericCriteria;
import uz.personal.domain.Auditable;

public interface IGenericCrudRepository<T extends Auditable, C extends GenericCriteria> extends IGenericRepository<T, C> {

    Long save(T entity);

    T update(T entity);

    void delete(T entity);
}
