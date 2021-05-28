package application.SaversReaders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelSaver {
	
	public void SaveExcelfile(String text,File file) throws Exception { 
		Workbook workbook = getWorkbook(file);
	    Sheet sheet =getSheet(file);
	    sheet=workbook.createSheet();
	    
		FileOutputStream out = new FileOutputStream (file);
		
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
	
	private Workbook getWorkbook(File file) throws IOException {
		String filepath=file.getCanonicalPath();
		Workbook workbook = null;

	    if (filepath.endsWith("xlsx")) {
	        workbook = new XSSFWorkbook();
	    } else if (filepath.endsWith("xls")) {
	        workbook = new HSSFWorkbook();
	    }
	    return workbook;
	}
	
	private Sheet getSheet(File file) throws IOException {
		String filepath=file.getCanonicalPath();
		Sheet sheet = null;
		
	    if (filepath.endsWith("xlsx")) {
	        sheet = (XSSFSheet) sheet;
	    }
	    	
	    return sheet;
	}

}