package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.TextArea;

//TODO
public class DocumentManager {
	public void saveDocument(File file, String text) {
		String fileextension = getFileExtension(file);
	
		 //Might Need to check for file replacement
		 if (fileextension.equals("doc") || fileextension.equals("docx")) {
				WordSaver saver = new WordSaver();
				try {
					saver.SaveWordfile(text,file);
				} catch (Exception e) {
					System.err.println("Error in Saving Word file: "+e.getLocalizedMessage());
				}
				
			}
			
			else if (fileextension.equals("xls") || fileextension.equals("xlsx")) {
				ExcelSaver saver = new ExcelSaver();
				try {
					saver.SaveExcelfile(text,file);
				} catch (Exception e) {
					System.err.println("Error in Saving Word file: "+e.getLocalizedMessage());
				}

			}
			
			else{
				//TxtReader file = new TxtReader();
				 try {
			            FileWriter fileWriter = new FileWriter(file);
			            fileWriter.write(text);
			            fileWriter.close();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			}
	}
	
	public ArrayList<String> loadDocument(File file) {
		String fileextension = getFileExtension(file);
		
		ArrayList<String> data = new ArrayList<String>();
		
		if (fileextension.equals("doc") || fileextension.equals("docx")) {
			WordReader wordfile = new WordReader();
			data = wordfile.fileReader(file);
		}
		
		else if (fileextension.equals("xls") || fileextension.equals("xlsx")) {
			ExcelReader wordfile = new ExcelReader();
			data = wordfile.fileReader(file);
		}
		
		else if (fileextension.equals("txt")) {
			TxtReader txtfile = new TxtReader();
			data = txtfile.fileReader(file);
		}
		
		return data;
	}
	
	private String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}
}
