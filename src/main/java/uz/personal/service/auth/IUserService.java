package uz.personal.service.auth;

import org.springframework.http.ResponseEntity;
import uz.personal.criteria.auth.UserCriteria;
import uz.personal.domain.auth._User;
import uz.personal.dto.auth.AttachRoleDto;
import uz.personal.dto.auth.UserCreateDto;
import uz.personal.dto.auth.UserDto;
import uz.personal.dto.auth.UserUpdateDto;
import uz.personal.response.DataDto;
import uz.personal.service.IGenericCrudService;

import javax.validation.constraints.NotNull;

public interface IUserService extends IGenericCrudService<_User, UserDto, UserCreateDto, UserUpdateDto, Long, UserCriteria> {

    ResponseEntity<DataDto<UserDto>> attachRolesToUser(@NotNull AttachRoleDto dto);

}
