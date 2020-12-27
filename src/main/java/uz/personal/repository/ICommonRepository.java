package uz.personal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import uz.personal.domain.Auditable;

@NoRepositoryBean
public interface ICommonRepository<T extends Auditable> extends CrudRepository<T, Long>, IAbstractRepository {

}
