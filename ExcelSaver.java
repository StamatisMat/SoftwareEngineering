package application;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ExcelSaver {
	//TODO chech if file exists
	public void SaveExcelfile(String text,File openfile) throws Exception { 
	    XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet();
	    
		FileOutputStream out = new FileOutputStream (openfile);
		
		String[] rows = text.split( "\n" );
		int row_counter=0;
		int column_counter=0;
		for (String row_text : rows) {
			column_counter=0;
            Row row = sheet.createRow(row_counter);
            String[] columns = row_text.split(" ");
            row_counter+=1;
            for (String column_text : columns) {
                Cell cell = row.createCell(column_counter);
                cell.setCellValue(column_text);
                column_counter+=1;
            }
        }
		
		workbook.write(out);
		out.close();
		workbook.close();
	}
}