package api.file.importer.contract;

import api.dto.request.EquipamentoRequestDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<EquipamentoRequestDTO> importFile(InputStream inputStream) throws Exception;
}
