package uz.personal.service.auth.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.personal.criteria.auth.UserCriteria;
import uz.personal.domain.auth._Role;
import uz.personal.domain.auth._User;
import uz.personal.dto.GenericDto;
import uz.personal.dto.auth.AttachRoleDto;
import uz.personal.dto.auth.UserCreateDto;
import uz.personal.dto.auth.UserDto;
import uz.personal.dto.auth.UserUpdateDto;
import uz.personal.enums.ErrorCodes;
import uz.personal.enums.UserType;
import uz.personal.exception.IdRequiredException;
import uz.personal.exception.ValidationException;
import uz.personal.mapper.GenericMapper;
import uz.personal.mapper.auth.UserMapper;
import uz.personal.repository.auth.IRoleRepository;
import uz.personal.repository.auth.IUserRepository;
import uz.personal.repository.setting.IErrorRepository;
import uz.personal.response.DataDto;
import uz.personal.service.GenericCrudService;
import uz.personal.service.auth.IUserService;
import uz.personal.utils.BaseUtils;
import uz.personal.utils.UserSession;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserService extends GenericCrudService<_User, UserDto, UserCreateDto, UserUpdateDto, UserCriteria, IUserRepository> implements IUserService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final UserMapper userMapper;
    private final GenericMapper genericMapper;
    private final UserSession userSession;
    private final IRoleRepository roleRepository;

    @Autowired
    public UserService(IUserRepository repository, BaseUtils utils, IErrorRepository errorRepository, UserMapper userMapper, GenericMapper genericMapper, UserSession userSession, IRoleRepository roleRepository) {
        super(repository, utils, errorRepository);
        this.userMapper = userMapper;
        this.genericMapper = genericMapper;
        this.userSession = userSession;
        this.roleRepository = roleRepository;
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).USER_READ)")
    public ResponseEntity<DataDto<UserDto>> get(Long id) {
        _User user = repository.find(UserCriteria.childBuilder().selfId(id).build());
        validate(user, id);
        return new ResponseEntity<>(new DataDto<>(userMapper.toDto(user)), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).USER_READ)")
    public ResponseEntity<DataDto<List<UserDto>>> getAll(UserCriteria criteria) {
        Long total = repository.getTotalCount(criteria);
        return new ResponseEntity<>(new DataDto<>(userMapper.toDto(repository.findAll(criteria)), total), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).USER_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull UserCreateDto dto) {
        _User user = userMapper.fromCreateDto(dto);
        baseValidation(user);
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        repository.save(user);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(user)), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).USER_UPDATE)")
    public ResponseEntity<DataDto<UserDto>> update(@NotNull UserUpdateDto dto) {
        if (utils.isEmpty(dto.getId())) {
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_ID_REQUIRED, "User"));
        }
        _User user = repository.find(dto.getId());
        validate(user, dto.getId());

        user = userMapper.fromUpdateDto(dto, user);
        baseValidation(user);
        repository.save(user);

        return get(user.getId());
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).USER_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        _User user = repository.find(id);
        validate(user, id);
        repository.delete(user);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).USER_ATTACH_ROLE)")
    public ResponseEntity<DataDto<UserDto>> attachRolesToUser(@NotNull AttachRoleDto dto) {
        if (utils.isEmpty(dto.getUserId()))
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_ID_REQUIRED, "User"));
        if (utils.isEmpty(dto.getRoles()))
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("roles", "Role")));

        _User user = repository.find(dto.getUserId());
        validate(user, dto.getUserId());

        List<_Role> roles = new ArrayList<>();
        dto.getRoles().forEach(genericDto -> {
            _Role role = roleRepository.find(genericDto.getId());
            if (role != null) {
                roles.add(role);
            }
        });

        user.setRoles(roles);
        repository.save(user);

        return get(user.getId());
    }

    @Override
    public void baseValidation(_User entity) {
        if (utils.isEmpty(entity.getUsername()))
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("username", "User")));
        if (utils.isEmpty(entity.getPassword()))
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("password", "User")));

        if (utils.isEmpty(entity.getUserType())) {
            entity.setUserType(UserType.CLIENT);
        } else {
            if (UserType.ADMIN == entity.getUserType()) {
                if (userSession.getUser().isSystemAdmin()) {
                    entity.setUserType(UserType.ADMIN);
                } else {
                    throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.ACCESS_DENIED, "userType"));
                }
            }
        }
    }

    @Override
    public void validate(_User entity, Long id) {
        if (utils.isEmpty(entity)) {
            logger.error(String.format("User with id '%s' not found", id));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.USER_NOT_FOUND_ID, "" + id));
        }
    }
}
