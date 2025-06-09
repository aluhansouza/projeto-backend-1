package api.file.exporter.contract;

import api.dto.response.EquipamentoResponseDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface EquipamentoExporter {

    Resource exportEquipamentos(List<EquipamentoResponseDTO> equipamentos) throws Exception;

    Resource exportEquipamento(EquipamentoResponseDTO equipamento) throws Exception;
}