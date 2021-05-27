package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.TextArea;


public class DocumentManager {
	public void saveDocument(File file, String text) {
		String filepath="";
		
		try {
			filepath=file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 //Might Need to check for file replacement
		 if (filepath.endsWith("doc") || filepath.endsWith("docx")) {
				WordSaver saver = new WordSaver();
				try {
					saver.SaveWordfile(text,file);
				} catch (Exception e) {
					System.err.println("Error in Saving Word file: "+e.getLocalizedMessage());
				}
				
			}
			
			else if (filepath.endsWith("xls") || filepath.endsWith("xlsx")) {
				ExcelSaver saver = new ExcelSaver();
				try {
					saver.SaveExcelfile(text,file);
				} catch (Exception e) {
					System.err.println("Error in Saving Word file: "+e.getLocalizedMessage());
				}
	
			}
			
			else{
				 TxtSaver saver = new TxtSaver();
				 try {
					saver.SaveTxtFile(text,file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
	
	public ArrayList<String> loadDocument(File file) {
		String filepath="";
		
		try {
			filepath=file.getCanonicalPath();
			
			ArrayList<String> data = new ArrayList<String>();
			
			if (filepath.endsWith("doc") || filepath.endsWith("docx")) {
				WordReader wordfile = new WordReader();
				data = wordfile.fileReader(file);
			}
			
			else if (filepath.endsWith("xls") || filepath.endsWith("xlsx")) {
				ExcelReader wordfile = new ExcelReader();
				data = wordfile.fileReader(file);
			}
			
			else if (filepath.endsWith("txt")) {
				TxtReader txtfile = new TxtReader();
				data = txtfile.fileReader(file);
			}
		
			return data;
		}catch (IOException e) {
			//Should never get here
			return null;
		}
	}
}
