package api.file.exporter.impl;

import api.dto.EquipamentoResponseDTO;
import api.file.exporter.contract.EquipamentoExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExporter implements EquipamentoExporter {

    @Override
    public Resource exportEquipamentos(List<EquipamentoResponseDTO> equipamentos) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("ID", "Nome")
                .setSkipHeaderRecord(false)
                .build();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {
            for (EquipamentoResponseDTO equipamento : equipamentos) {
                csvPrinter.printRecord(
                        equipamento.getId(),
                        equipamento.getNome()
                );
            }

        }
        return new ByteArrayResource(outputStream.toByteArray());
    }

    @Override
    public Resource exportEquipamento(EquipamentoResponseDTO equipamento) throws Exception {
        return null;
    }
}
