package api.file.importer.contract;

import api.dto.EquipamentoDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<EquipamentoDTO> importFile(InputStream inputStream) throws Exception;
}
