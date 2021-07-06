package uz.personal.service.auth.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import uz.personal.criteria.auth.RoleCriteria;
import uz.personal.domain.auth._Role;
import uz.personal.dto.GenericDto;
import uz.personal.dto.auth.RoleCreateDto;
import uz.personal.dto.auth.RoleDto;
import uz.personal.dto.auth.RoleUpdateDto;
import uz.personal.enums.ErrorCodes;
import uz.personal.exception.IdRequiredException;
import uz.personal.exception.ValidationException;
import uz.personal.mapper.GenericMapper;
import uz.personal.mapper.auth.RoleMapper;
import uz.personal.repository.auth.IRoleRepository;
import uz.personal.repository.setting.IErrorRepository;
import uz.personal.response.DataDto;
import uz.personal.service.GenericCrudService;
import uz.personal.service.auth.IRoleService;
import uz.personal.utils.BaseUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service(value = "roleService")
public class RoleService extends GenericCrudService<_Role, RoleDto, RoleCreateDto, RoleUpdateDto, RoleCriteria, IRoleRepository> implements IRoleService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final RoleMapper roleMapper;
    private final GenericMapper genericMapper;

    @Autowired
    public RoleService(IRoleRepository repository, BaseUtils utils, IErrorRepository errorRepository, RoleMapper roleMapper, GenericMapper genericMapper) {
        super(repository, utils, errorRepository);
        this.roleMapper = roleMapper;
        this.genericMapper = genericMapper;
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).ROLE_READ)")
    public ResponseEntity<DataDto<RoleDto>> get(Long id) {
        _Role role = repository.find(RoleCriteria.childBuilder().selfId(id).build());
        validate(role, id);
        return new ResponseEntity<>(new DataDto<>(roleMapper.toDto(role)), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).ROLE_READ)")
    public ResponseEntity<DataDto<List<RoleDto>>> getAll(RoleCriteria criteria) {
        Long total = repository.getTotalCount(criteria);
        return new ResponseEntity<>(new DataDto<>(roleMapper.toDto(repository.findAll(criteria)), total), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).ROLE_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull RoleCreateDto dto) {
        _Role role = roleMapper.fromCreateDto(dto);
        baseValidation(role);
        role.setCode(role.getCode().toUpperCase());
        repository.save(role);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(role)), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).ROLE_UPDATE)")
    public ResponseEntity<DataDto<RoleDto>> update(@NotNull RoleUpdateDto dto) {
        if (utils.isEmpty(dto.getId())) {
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_ID_REQUIRED, "Role"));
        }
        _Role role = repository.find(dto.getId());
        validate(role, dto.getId());

        role = roleMapper.fromUpdateDto(dto, role);
        baseValidation(role);

        role.setCode(role.getCode().toUpperCase());
        repository.save(role);

        return get(role.getId());
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).ROLE_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        _Role role = repository.find(id);
        validate(role, id);
        repository.delete(role);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
    public void baseValidation(@NotNull _Role entity) {
        if (utils.isEmpty(entity.getCode()))
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("code", "Role")));
        if (utils.isEmpty(entity.getName()))
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("name", "Role")));
    }

    @Override
    public void validate(@NotNull _Role entity, @NotNull Long id) {
        if (utils.isEmpty(entity)) {
            logger.error(String.format("Role with id '%s' not found", id));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_NOT_FOUND_ID, utils.toErrorParams("Role", id)));
        }
    }
}
