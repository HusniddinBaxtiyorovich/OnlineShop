package uz.personal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import uz.personal.criteria.GenericCriteria;
import uz.personal.domain.Auditable;
import uz.personal.dto.CrudDto;
import uz.personal.dto.GenericDto;
import uz.personal.repository.IGenericRepository;
import uz.personal.repository.setting.IErrorRepository;
import uz.personal.response.DataDto;
import uz.personal.utils.BaseUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @param <T>  Entity
 * @param <D>  Dto
 * @param <CR> CreateDto
 * @param <UP> UpdateDto
 * @param <C>  Criteria
 * @param <R>  Repository
 */

public abstract class GenericCrudService<T extends Auditable, D extends GenericDto, CR extends CrudDto, UP extends CrudDto, C extends GenericCriteria, R extends IGenericRepository> {

    protected final R repository;
    protected final BaseUtils utils;
    protected final IErrorRepository errorRepository;

    @Autowired
    public GenericCrudService(R repository, BaseUtils utils, IErrorRepository errorRepository) {
        this.repository = repository;
        this.utils = utils;
        this.errorRepository = errorRepository;
    }

    public ResponseEntity<DataDto<D>> get(Long id) {
        return null;
    }

    public ResponseEntity<DataDto<List<D>>> getAll(C criteria) {
        return null;
    }

    public ResponseEntity<DataDto<GenericDto>> create(@NotNull CR dto) {
        return null;
    }

    public ResponseEntity<DataDto<D>> update(@NotNull UP dto) {
        return null;
    }

    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        return null;
    }

    public void baseValidation(@NotNull T entity) {

    }

    public void validate(@NotNull T entity, @NotNull Long id) {

    }
}
