package api.mapstruct;

import api.dto.request.CategoriaRequestDTO;
import api.dto.request.SetorRequestDTO;
import api.dto.response.CategoriaResponseDTO;
import api.dto.response.SetorResponseDTO;
import api.entity.Categoria;
import api.entity.Setor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoriaMapper {


    Categoria toEntity(CategoriaRequestDTO dto);


    CategoriaResponseDTO toResponse(Categoria entity);

    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(CategoriaRequestDTO dto, @MappingTarget Categoria entity);
}
