package uz.personal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.personal.domain.Auditable;
import uz.personal.dto.GenericDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface GenericMapper {
    GenericDto fromDomain(Auditable domain);
}
