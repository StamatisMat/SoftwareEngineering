package application.SaversReaders;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordSaver {
	XWPFRun run;
	
	public void SaveWordfile(String text,File file) throws Exception { 
		XWPFDocument document = new XWPFDocument();
		FileOutputStream out = new FileOutputStream (file);
      
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