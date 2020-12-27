package uz.personal.service;

import org.springframework.http.ResponseEntity;
import uz.personal.criteria.GenericCriteria;
import uz.personal.domain.Auditable;
import uz.personal.dto.CrudDto;
import uz.personal.dto.GenericDto;
import uz.personal.response.DataDto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @param <T>  Entity
 * @param <D>  Dto
 * @param <CR> CreateDto
 * @param <UP> UpdateDto
 * @param <C>  Criteria
 */

public interface IGenericCrudService<T extends Auditable, D extends GenericDto, CR extends CrudDto, UP extends CrudDto, ID extends Serializable, C extends GenericCriteria> extends IAbstractService {

    ResponseEntity<DataDto<D>> get(ID id);

    ResponseEntity<DataDto<List<D>>> getAll(C criteria);

    ResponseEntity<DataDto<GenericDto>> create(@NotNull CR dto);

    ResponseEntity<DataDto<D>> update(@NotNull UP dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull ID id);

    void baseValidation(@NotNull T entity);

    void validate(@NotNull T entity, @NotNull Long id);
}
