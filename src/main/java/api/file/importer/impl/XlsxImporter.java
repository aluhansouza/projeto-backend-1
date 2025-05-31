package api.file.importer.impl;

import org.apache.poi.ss.usermodel.DataFormatter;
import api.dto.EquipamentoDTO;
import api.file.importer.contract.FileImporter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporter implements FileImporter {

    @Override
    public List<EquipamentoDTO> importFile(InputStream inputStream) throws Exception {

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) rowIterator.next();

            return parseRowsToEquipamentoDtoList(rowIterator);

        }
    }

    private List<EquipamentoDTO> parseRowsToEquipamentoDtoList(Iterator<Row> rowIterator) {
        List<EquipamentoDTO> equipamentos = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isRowValid(row)) {
                equipamentos.add(parseRowToEquipamentoDto(row));
            }
        }
        return equipamentos;
    }



    // Determinar a coluna, por exemplo Coluna 0 = ID, Coluna 1 = Nome
    private EquipamentoDTO parseRowToEquipamentoDto(Row row) {
        EquipamentoDTO equipamentoDTO = new EquipamentoDTO();
        equipamentoDTO.setNome(row.getCell(1).getStringCellValue());
        return equipamentoDTO;
    }



    private static boolean isRowValid(Row row) {
        return row.getCell(1) != null && row.getCell(1).getCellType() != CellType.BLANK;
    }
}
