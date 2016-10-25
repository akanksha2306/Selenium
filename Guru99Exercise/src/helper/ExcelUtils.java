
package helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import helper.Constants;

public class ExcelUtils {
    private static XSSFWorkbook ExcelWBook;
    private static XSSFSheet ExcelWSheet;
    private static XSSFCell Cell;
    private static XSSFRow Row;
    public static int TotalRows;

    public static void setExcelFile(String Path, String SheetName) throws Exception {
        FileInputStream ExcelFile = new FileInputStream(Path);
        ExcelWBook = new XSSFWorkbook(ExcelFile);
        ExcelWSheet = ExcelWBook.getSheet(SheetName);
        TotalRows = ExcelWSheet.getLastRowNum();
    }

    public static String getCellData(int RowNum, int ColNum) {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.getStringCellValue();
            return CellData;
        }
        catch (Exception e) {
            return "";
        }
    }

    public static void setCellData(String Result, int RowNum, int ColNum) throws Exception {
        Row = ExcelWSheet.getRow(RowNum);
        Cell = Row.getCell(ColNum, XSSFRow.RETURN_BLANK_AS_NULL);
        if (Cell == null) {
            Cell = Row.createCell(ColNum);
            Cell.setCellValue(Result);
        } else {
            Cell.setCellValue(Result);
        }
        FileOutputStream fileOut = new FileOutputStream(Constants.FileTestData);
        ExcelWBook.write((OutputStream)fileOut);
        fileOut.flush();
        fileOut.close();
    }
}
