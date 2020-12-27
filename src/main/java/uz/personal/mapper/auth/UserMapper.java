package uz.personal.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.personal.domain.auth._User;
import uz.personal.dto.auth.UserCreateDto;
import uz.personal.dto.auth.UserDto;
import uz.personal.dto.auth.UserMeDto;
import uz.personal.dto.auth.UserUpdateDto;
import uz.personal.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {RoleMapper.class})
@Component
public interface UserMapper extends BaseMapper<_User, UserDto, UserCreateDto, UserUpdateDto> {

    @Override
    @Mapping(source = "userType.code", target = "userTypeName")
    UserDto toDto(_User entity);

    @Mapping(source = "userType.code", target = "userTypeName")
    UserMeDto toUserMeDto(_User entity);
}
