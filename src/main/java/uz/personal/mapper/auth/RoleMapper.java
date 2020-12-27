package uz.personal.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.personal.domain.auth._Role;
import uz.personal.dto.auth.RoleCreateDto;
import uz.personal.dto.auth.RoleDto;
import uz.personal.dto.auth.RoleUpdateDto;
import uz.personal.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {PermissionMapper.class})
@Component
public interface RoleMapper extends BaseMapper<_Role, RoleDto, RoleCreateDto, RoleUpdateDto> {

}
