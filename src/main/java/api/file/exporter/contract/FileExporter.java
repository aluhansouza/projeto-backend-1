package api.file.exporter.contract;

import api.dto.EquipamentoDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {

    Resource exportFile(List<EquipamentoDTO> equipamentos) throws Exception;
}