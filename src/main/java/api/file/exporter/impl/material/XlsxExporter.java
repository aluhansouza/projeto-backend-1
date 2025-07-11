package api.file.exporter.impl.material;

import api.dto.response.MaterialResponseDTO;
import api.file.exporter.contract.MaterialExporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class XlsxExporter implements MaterialExporter {

    @Override
    public Resource exportMateriais(List<MaterialResponseDTO> materiais) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Materiais");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Nome"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            int rowIndex = 1;
            for (MaterialResponseDTO materialResponseDTO : materiais) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(materialResponseDTO.getId());
                row.createCell(1).setCellValue(materialResponseDTO.getNome());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    @Override
    public Resource exportMaterial(MaterialResponseDTO material) throws Exception {
        return null;
    }



}
