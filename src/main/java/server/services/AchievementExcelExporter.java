package server.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import server.models.Achievement;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AchievementExcelExporter {


    private static void writeHeaders(XSSFSheet sheet, XSSFWorkbook workbook) {
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Идентификатор записи", style, sheet);
        createCell(row, 1, "ФИО студента", style, sheet);
        createCell(row, 2, "ФИО преподавателя", style, sheet);
        createCell(row, 3, "Форма контроля", style, sheet);
        createCell(row, 4, "Дисциплина", style, sheet);
        createCell(row, 5, "Оценка", style, sheet);
        createCell(row, 6, "Семестр", style, sheet);
        createCell(row, 7, "Дата", style, sheet);

    }

    private static void createCell(Row row, int columnCount, Object value, CellStyle style, XSSFSheet sheet) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof LocalDate) {
            cell.setCellValue((LocalDate) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private static void writeDataLines(XSSFSheet sheet, XSSFWorkbook workbook, List<Achievement> achievements) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setFont(font);
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mm-yyyy"));

        for (Achievement achievement : achievements) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, achievement.getId(), style, sheet);
            createCell(row, columnCount++, achievement.getStudent().getFullName(), style, sheet);
            createCell(row, columnCount++, achievement.getProfessor().getFullName(), style, sheet);
            createCell(row, columnCount++, achievement.getFormOfControl().getName(), style, sheet);
            createCell(row, columnCount++, achievement.getDiscipline().getName(), style, sheet);
            createCell(row, columnCount++, achievement.getMark(), style, sheet);
            createCell(row, columnCount++, achievement.getSemester(), style, sheet);
            createCell(row, columnCount++, achievement.getDate(), dateStyle, sheet);
        }
    }

    public static void export(HttpServletResponse response, List<Achievement> achievements) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Achievements");
        writeHeaders(sheet, workbook);
        writeDataLines(sheet, workbook, achievements);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
