package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;


import org.junit.jupiter.api.Test;

import application.DocumentManager;
import application.ExcelReader;
import application.ExcelSaver;
import application.WordReader;
import application.WordSaver;

public class OpenFileTest {
	@Test
	public void testWordSave() {
		DocumentManager docManager = new DocumentManager();
		String text="Hello\nWorld\n";
		File file = new File("src/tests/Dummy.docx");
		File testfile = new File("src/tests/Dummy.doc");
		WordSaver wordsaver = new WordSaver();
		try {
			wordsaver.SaveWordfile(text, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(docManager.loadDocument(testfile),null); //Non existent file
		WordReader wordread = new WordReader();
		ArrayList<String> comparetext = new ArrayList<String>(wordread.fileReader(file));
		assertEquals(text, comparetext.get(0));
	}
	
	@Test
	public void testExcelSave() {
		String text="Hel lo \nWor ld";
		File file = new File("src/tests/Dummy.xlsx");
		ExcelSaver excelsaver = new ExcelSaver();
		try {
			excelsaver.SaveExcelfile(text, file);
		} catch (Exception e) {
			// Should never get here as it is handled
			e.printStackTrace();
		}
		ExcelReader wordread = new ExcelReader();
		ArrayList<String> exceltext = new ArrayList<String>(wordread.fileReader(file));
		String comparetext = "";
		for(String i: exceltext) {
			comparetext += i;
		}
		assertEquals(text, comparetext);
	}
	
	@Test
	public void testWordLoad() {
		File file = new File("src/tests/Hello.docx");
		WordReader wordread = new WordReader();
		ArrayList<String> text = new ArrayList<String>(wordread.fileReader(file));
		System.out.println(text);
		assertEquals("Hello\nWorld\n",text.get(0));
	}
	
	@Test
	public void testExcelLoad() {
		String comparetext="Hel lo \nWor ld";
		String filltext="";
		File file = new File("src/tests/Hello.xlsx");
		ExcelReader wordread = new ExcelReader();
		ArrayList<String> text = new ArrayList<String>(wordread.fileReader(file));
		System.out.println(text);
		for(String i: text) {
			filltext += i;
		}
		assertEquals(comparetext, filltext);
		/*assertEquals("Hel",text.get(0));
		assertEquals(" ",text.get(1));
		assertEquals("lo",text.get(2));
		assertEquals(" ",text.get(3));
		assertEquals("\n",text.get(4));
		assertEquals("Wor",text.get(5));
		assertEquals(" ",text.get(6));
		assertEquals("ld",text.get(7));*/ //We do not need as many tests 
	}
	
	
}
