package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import application.SaversReaders.*;

/*
	Document Manager class, handles save/load from different file formats
*/



public class DocumentManager {
	
	// Simple saver function, we try to get the file, check the prefix and save according to the format
	public void saveDocument(File file, String text) {
		String filepath="";
		
		try { // Try to get the path for the file
			filepath=file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 // Check the prefix of the file and save
		 if (filepath.endsWith("doc") || filepath.endsWith("docx")) { // Use WordSaver for doc/docx file format
				WordSaver saver = new WordSaver(); 
				try {
					saver.SaveWordfile(text,file);
				} catch (Exception e) {
					System.err.println("Error in Saving Word file: "+e.getLocalizedMessage());
				}
				
			}
			
			else if (filepath.endsWith("xls") || filepath.endsWith("xlsx")) { // Use ExcelSaver for xls/xlsx file format
				ExcelSaver saver = new ExcelSaver();
				try {
					saver.SaveExcelfile(text,file);
				} catch (Exception e) {
					System.err.println("Error in Saving Excel file: "+e.getLocalizedMessage());
				}
	
			}
			
			else{ // We have a generic saver to save files to txt format
				 TxtSaver saver = new TxtSaver();
				 try {
					saver.SaveTxtFile(text,file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
	
	// Simple loader function, we try to get the file, check the prefix and load according to the format
	public ArrayList<String> loadDocument(File file) {
		String filepath="";
		
		try { // Try to get the file path 
			filepath=file.getCanonicalPath();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		// Create a list for the data, then load according to the format
		ArrayList<String> data = new ArrayList<String>();
		if (filepath.endsWith("doc") || filepath.endsWith("docx")) { // Use WordReader for doc/docx file format
			WordReader reader = new WordReader();
			data = reader.fileReader(file);
		}
		
		else if (filepath.endsWith("xls") || filepath.endsWith("xlsx")) { // Use ExcelReader for xls/xlsx file format
			ExcelReader reader = new ExcelReader();
			data = reader.fileReader(file);
		}
		
		else { // Use a generic reader for not recognized formats
			TxtReader reader = new TxtReader();
			data = reader.fileReader(file);
		}
	
		return data;
	}
}
