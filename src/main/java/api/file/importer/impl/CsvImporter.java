package api.file.importer.impl;

import api.dto.EquipamentoRequestDTO;
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
    public List<EquipamentoRequestDTO> importFile(InputStream inputStream) throws Exception {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));
        return parseRecordsToEquipamentoDTOs(records);
    }

    private List<EquipamentoRequestDTO> parseRecordsToEquipamentoDTOs(Iterable<CSVRecord> records) {
        List<EquipamentoRequestDTO> equipamentos = new ArrayList<>();

        for (CSVRecord record : records) {
            EquipamentoRequestDTO equipamentoRequestDTO = new EquipamentoRequestDTO();
            equipamentoRequestDTO.setNome(record.get("nome"));

            equipamentos.add(equipamentoRequestDTO);
        }
        return equipamentos;
    }
}
