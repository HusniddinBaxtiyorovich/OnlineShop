package uz.personal.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.personal.domain.auth._Permission;
import uz.personal.dto.auth.PermissionCreateDto;
import uz.personal.dto.auth.PermissionDto;
import uz.personal.dto.auth.PermissionUpdateDto;
import uz.personal.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface PermissionMapper extends BaseMapper<_Permission, PermissionDto, PermissionCreateDto, PermissionUpdateDto> {
}
