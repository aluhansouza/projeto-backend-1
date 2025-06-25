package api.mapstruct;

import api.dto.request.SetorRequestDTO;
import api.dto.response.SetorResponseDTO;
import api.entity.Setor;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SetorMapper {

    // Mapeia o DTO para a entidade Setor
    Setor toEntity(SetorRequestDTO dto);

    // Mapeia a entidade Setor para o DTO de resposta
    SetorResponseDTO toResponse(Setor entity);

    // Atualiza a entidade Setor com dados do DTO (ignorando valores nulos)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(SetorRequestDTO dto, @MappingTarget Setor entity);
}
