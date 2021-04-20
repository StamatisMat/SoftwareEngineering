package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.nio.file.Files;
import java.nio.file.Paths;

public class WordReader {

	public ArrayList<String> fileReader(File file) {
		ArrayList<String> data = new ArrayList<String>();

		try (XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(file.getCanonicalPath())))) {

			XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
			String docText = xwpfWordExtractor.getText();
			data.add(docText);
			xwpfWordExtractor.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return data;
	}

}
