package application;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordSaver {
	XWPFRun run;
	//TODO chech if file exists
	public void SaveWordfile(String text,File openfile) throws Exception { 
		XWPFDocument document = new XWPFDocument();
		FileOutputStream out = new FileOutputStream (openfile);
      
		XWPFParagraph paragraph = document.createParagraph();
		
		if(text.contains("\n")){
			String[] stringsOnNewLines = text.split( "\n" );
			for ( int i = 0; i < stringsOnNewLines.length; i++ ) {
				String textForLine = stringsOnNewLines[i];
				run = paragraph.createRun();
				run.setText(textForLine, 0);
				if(i!=stringsOnNewLines.length-1){
					run.addCarriageReturn();
				}
			}
		}
		else {
			run = paragraph.createRun();
			run.setText(text, 0);
		}
		document.write(out);
		out.close();
		document.close();
	}
}