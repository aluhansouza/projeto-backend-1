package api.file.importer.impl;

import api.dto.EquipamentoRequestDTO;
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
    public List<EquipamentoRequestDTO> importFile(InputStream inputStream) throws Exception {

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) rowIterator.next();

            return parseRowsToEquipamentoDtoList(rowIterator);

        }
    }

    private List<EquipamentoRequestDTO> parseRowsToEquipamentoDtoList(Iterator<Row> rowIterator) {
        List<EquipamentoRequestDTO> equipamentos = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isRowValid(row)) {
                equipamentos.add(parseRowToEquipamentoDto(row));
            }
        }
        return equipamentos;
    }



    // Determinar a coluna, por exemplo Coluna 0 = ID, Coluna 1 = Nome
    private EquipamentoRequestDTO parseRowToEquipamentoDto(Row row) {
        EquipamentoRequestDTO equipamentoRequestDTO = new EquipamentoRequestDTO();
        equipamentoRequestDTO.setNome(row.getCell(1).getStringCellValue());
        return equipamentoRequestDTO;
    }



    private static boolean isRowValid(Row row) {
        return row.getCell(1) != null && row.getCell(1).getCellType() != CellType.BLANK;
    }
}
