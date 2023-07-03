package com.debuggeando_ideas.best_travel.infraestructure.service;

import com.debuggeando_ideas.best_travel.domain.app.Constant;
import com.debuggeando_ideas.best_travel.domain.entities.jpa.CustomerEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.jpa.CustomerRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.IReportService;
import com.debuggeando_ideas.best_travel.util.exceptions.FileXLSXNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.binary.XSSFBParseException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements IReportService {

    private final CustomerRepository customerRepository;

    private static int getTotalPurchases(CustomerEntity customerEntity) {
        return customerEntity.getTotalLodgings() + customerEntity.getTotalFlights()
            + customerEntity.getTotalTours();
    }

    private static void createFile(XSSFWorkbook workbook, String fileName) {
        try (FileOutputStream os = new FileOutputStream(fileName)) {
            workbook.write(os);
        } catch (IOException e) {
            log.error("Error to read file", e);
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public byte[] readFile() {
        try {
            createReport();
            Path path = Paths.get(Constant.REPORTS_PATH,
                String.format(Constant.FILE_NAME, LocalDate.now().getMonth())).toAbsolutePath();
            return Files.readAllBytes(path);
        }catch (IOException e) {
            log.error("Error creating report", e);
            throw new FileXLSXNotFoundException(e.getMessage());
        }
    }

    private void createReport() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            log.info("Creating report");
            XSSFSheet sheet = workbook.createSheet(Constant.SHEET_NAME);
            sheet.setColumnWidth(0, 7000);
            sheet.setColumnWidth(1, 7000);
            sheet.setColumnWidth(2, 3000);

            XSSFRow row = sheet.createRow(0);

            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = workbook.createFont();
            font.setFontName(Constant.FONT_TYPE);
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);
            cellStyle.setFont(font);

            XSSFCell cell = row.createCell(0);
            cell.setCellValue(Constant.COLUMN_CUSTOMER_ID);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellValue(Constant.COLUMN_CUSTOMER_NAME);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue(Constant.COLUMN_CUSTOMER_PURCHASES);
            cell.setCellStyle(cellStyle);

            row.setRowStyle(cellStyle);

            XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
            xssfCellStyle.setWrapText(true);

            List<CustomerEntity> customerEntityList = customerRepository.findAll();
            int rowPosition = 1;
            for (CustomerEntity customerEntity : customerEntityList) {
                XSSFRow sheetRow = sheet.createRow(rowPosition);
                XSSFCell sheetRowCell = sheetRow.createCell(0);
                sheetRowCell.setCellValue(customerEntity.getDni());
                sheetRowCell.setCellStyle(xssfCellStyle);

                sheetRowCell = sheetRow.createCell(1);
                sheetRowCell.setCellValue(customerEntity.getFullName());
                sheetRowCell.setCellStyle(xssfCellStyle);

                sheetRowCell = sheetRow.createCell(2);
                sheetRowCell.setCellValue(getTotalPurchases(customerEntity));
                sheetRowCell.setCellStyle(xssfCellStyle);
                rowPosition++;
            }

            File report = new File(
                String.format(Constant.REPORTS_PATH_WITH_NAME, LocalDate.now().getMonth()));
            String absolutePath = report.getAbsolutePath();
            String fileName = absolutePath.concat(Constant.FILE_TYPE);
            createFile(workbook, fileName);
        } catch (Exception e) {
            log.error("Error creating report", e);
            throw new XSSFBParseException(e.getMessage());
        }
    }
}
