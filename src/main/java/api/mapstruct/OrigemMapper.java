package api.mapstruct;

import api.dto.request.OrigemRequestDTO;
import api.dto.request.SetorRequestDTO;
import api.dto.response.OrigemResponseDTO;
import api.dto.response.SetorResponseDTO;
import api.entity.Origem;
import api.entity.Setor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrigemMapper {


    Origem toEntity(OrigemRequestDTO dto);


    OrigemResponseDTO toResponse(Origem entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(OrigemRequestDTO dto, @MappingTarget Origem entity);
}
