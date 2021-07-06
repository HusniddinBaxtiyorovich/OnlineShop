package uz.personal.service.auth.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.personal.criteria.auth.PermissionCriteria;
import uz.personal.domain.auth._Permission;
import uz.personal.dto.GenericDto;
import uz.personal.dto.auth.PermissionCreateDto;
import uz.personal.dto.auth.PermissionDto;
import uz.personal.dto.auth.PermissionUpdateDto;
import uz.personal.enums.ErrorCodes;
import uz.personal.exception.IdRequiredException;
import uz.personal.exception.ValidationException;
import uz.personal.mapper.GenericMapper;
import uz.personal.mapper.auth.PermissionMapper;
import uz.personal.repository.auth.IPermissionRepository;
import uz.personal.repository.setting.IErrorRepository;
import uz.personal.response.DataDto;
import uz.personal.service.GenericCrudService;
import uz.personal.service.auth.IPermissionService;
import uz.personal.utils.BaseUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service(value = "permissionService")
public class PermissionService extends GenericCrudService<_Permission, PermissionDto, PermissionCreateDto, PermissionUpdateDto, PermissionCriteria, IPermissionRepository> implements IPermissionService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final PermissionMapper permissionMapper;
    private final GenericMapper genericMapper;

    @Autowired
    public PermissionService(IPermissionRepository repository, BaseUtils utils, IErrorRepository errorRepository, PermissionMapper permissionMapper, GenericMapper genericMapper) {
        super(repository, utils, errorRepository);
        this.permissionMapper = permissionMapper;
        this.genericMapper = genericMapper;
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).PERMISSION_READ)")
    public ResponseEntity<DataDto<PermissionDto>> get(Long id) {
        _Permission permission = repository.find(PermissionCriteria.childBuilder().selfId(id).build());
        validate(permission, id);
        return new ResponseEntity<>(new DataDto<>(permissionMapper.toDto(permission)), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).PERMISSION_READ)")
    public ResponseEntity<DataDto<List<PermissionDto>>> getAll(PermissionCriteria criteria) {
        Long total = repository.getTotalCount(criteria);
        return new ResponseEntity<>(new DataDto<>(permissionMapper.toDto(repository.findAll(criteria)), total), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).PERMISSION_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull PermissionCreateDto dto) {
        if (dto.getParentId() != null && dto.getParentId() == 0) {
            dto.setParentId(null);
        }
        _Permission permission = permissionMapper.fromCreateDto(dto);
        baseValidation(permission);
        permission.setCode(permission.getCode().toUpperCase());
        repository.save(permission);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(permission)), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).PERMISSION_UPDATE)")
    public ResponseEntity<DataDto<PermissionDto>> update(@NotNull PermissionUpdateDto dto) {
        if (utils.isEmpty(dto.getId())) {
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_ID_REQUIRED, "Permission"));
        }
        _Permission permission = repository.find(dto.getId());
        validate(permission, dto.getId());

        permission = permissionMapper.fromUpdateDto(dto, permission);
        baseValidation(permission);

        permission.setCode(permission.getCode().toUpperCase());
        repository.save(permission);

        return get(permission.getId());
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).PERMISSION_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        _Permission permission = repository.find(id);
        validate(permission, id);
        repository.delete(permission);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
    public void baseValidation(@NotNull _Permission entity) {
        if (utils.isEmpty(entity.getCode()))
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("code", "Permission")));
        if (utils.isEmpty(entity.getName()))
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("name", "Permission")));
    }

    @Override
    public void validate(@NotNull _Permission entity, @NotNull Long id) {
        if (utils.isEmpty(entity)) {
            logger.error(String.format("Permission with id '%s' not found", id));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_NOT_FOUND_ID, utils.toErrorParams("Permission", id)));
        }
    }
}
