package api.file.importer.impl;

import api.dto.EquipamentoDTO;
import api.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter implements FileImporter {

    @Override
    public List<EquipamentoDTO> importFile(InputStream inputStream) throws Exception {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));
        return parseRecordsToEquipamentoDTOs(records);
    }

    private List<EquipamentoDTO> parseRecordsToEquipamentoDTOs(Iterable<CSVRecord> records) {
        List<EquipamentoDTO> equipamentos = new ArrayList<>();

        for (CSVRecord record : records) {
            EquipamentoDTO equipamentoDTO = new EquipamentoDTO();
            equipamentoDTO.setNome(record.get("nome"));

            equipamentos.add(equipamentoDTO);
        }
        return equipamentos;
    }
}
