package application.SaversReaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public ArrayList<String> fileReader(File file) {
		ArrayList<String> data = new ArrayList<String>();
		Workbook workbook=null;
		FileInputStream inputStream=null;
		try {
			workbook = getWorkbook(file);
			inputStream = new FileInputStream(new File(file.getCanonicalPath()));
	        Sheet firstSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = firstSheet.iterator();
	         
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	             
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                
	                switch (cell.getCellType()) {              
		                case STRING:
		                    data.add(cell.getStringCellValue());
		                    break;						
						/*
						 * case BOOLEAN: System.out.print(cell.getBooleanCellValue());
						 * data.add(cell.getBooleanCellValue()); break;
						 */
						case NUMERIC:
							data.add(String.valueOf(cell.getNumericCellValue()));
							break;
		            }
	                data.add(" ");
	            }
	            data.add("\n");
	        }
	         
	        workbook.close();
	        inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		//data.add(cell.getStringCellValue());
		return data;
        
	}
	
	
	private Workbook getWorkbook(File inputFile)
	        throws IOException {
		String filepath=inputFile.getCanonicalPath();
		FileInputStream inputStream = new FileInputStream(new File(filepath));
		Workbook workbook = null;
		
	    
	    if (filepath.endsWith("xlsx")) {
	        workbook = new XSSFWorkbook(inputStream);
	    } else if (filepath.endsWith("xls")) {
	        workbook = new HSSFWorkbook(inputStream);
	    } else {
	        throw new IllegalArgumentException("The specified file is not Excel file");
	    }
	 
	    return workbook;
	}

}

