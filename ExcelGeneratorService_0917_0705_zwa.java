// 代码生成时间: 2025-09-17 07:05:59
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExcelGeneratorService {

    /**
     * Generate an Excel file.
     * 
     * @param data The data to be written into the Excel file.
     * @param fileName The name of the Excel file to be generated.
     * @throws IOException If an I/O error occurs.
     */
    public void generateExcel(Map<String, Object[]> data, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook(); // Create a new Excel workbook
        Sheet sheet = workbook.createSheet(