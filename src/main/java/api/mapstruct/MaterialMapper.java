package api.mapstruct;

import org.mapstruct.*;
import api.dto.request.MaterialRequestDTO;
import api.dto.response.MaterialResponseDTO;
import api.entity.Material;
import api.entity.Categoria;
import api.entity.Setor;
import api.entity.Origem;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MaterialMapper {

    @Mappings({
            @Mapping(source = "categoriaId", target = "categoria"),
            @Mapping(source = "setorId", target = "setor"),
            @Mapping(source = "origemId", target = "origem")
    })
    Material toEntity(MaterialRequestDTO dto);

    @Mappings({
            @Mapping(source = "categoria.id", target = "categoriaId"),
            @Mapping(source = "setor.id", target = "setorId"),
            @Mapping(source = "origem.id", target = "origemId")
    })
    MaterialResponseDTO toResponse(Material entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(source = "categoriaId", target = "categoria"),
            @Mapping(source = "setorId", target = "setor"),
            @Mapping(source = "origemId", target = "origem")

    })
    void updateFromRequest(MaterialRequestDTO dto, @MappingTarget Material entity);

    // Helpers para tipos aninhados
    default Categoria mapCategoria(Long id) {
        if (id == null) return null;
        Categoria c = new Categoria();
        c.setId(id);
        return c;
    }

    default Setor mapSetor(Long id) {
        if (id == null) return null;
        Setor s = new Setor();
        s.setId(id);
        return s;
    }

    default Origem mapOrigem(Long id) {
        if (id == null) return null;
        Origem o = new Origem();
        o.setId(id);
        return o;
    }
}