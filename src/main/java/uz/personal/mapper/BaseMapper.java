package uz.personal.mapper;

import org.mapstruct.MappingTarget;
import uz.personal.domain.Auditable;
import uz.personal.dto.CrudDto;

import java.util.List;

/**
 * @param <E>  - Entity
 * @param <D>  - Dto
 * @param <CD> - CreateDTO
 * @param <UD> - UpdateDTO
 */

public interface BaseMapper<E extends Auditable, D, CD extends CrudDto, UD extends CrudDto> {

    D toDto(E entity);

    E fromDto(D dto);

    List<D> toDto(List<E> entityList);

    List<E> fromDto(List<D> dtoList);

    E fromCreateDto(CD createDto);

    E fromUpdateDto(UD updateDto, @MappingTarget E entity);
}
