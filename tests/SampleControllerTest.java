package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SampleControllerTest {
	
	@Test
	public void recentFilesTester() {
		ArrayList<String> choices = new ArrayList<>();
		ArrayList<File> recentfiles = new ArrayList<>();
		File file1 = new File("src/tests/Hello.xlsx");
		File file2 = new File("src/tests/Hello.docx");
		File file3 = new File("src/tests/Hello.doc");
		File prevfile = file1; //setting prevfile to a valid file 
		recentfiles.add(file1);
		recentfiles.add(file2);
		recentfiles.add(file3);
		
		for(File file: recentfiles){
			try {
				if(!choices.contains(file.getCanonicalPath())) {
					choices.add(0,file.getCanonicalPath());
				}	
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		assertEquals(choices.size(),3);
		
		
		for(File i: recentfiles) {
			File openfile = i;
			if(!openfile.isFile()) {
				recentfiles.remove(openfile);				
				openfile=prevfile;
			}
		}
		assertEquals(recentfiles.size(),2);
	}
	
	@Test
	public void handlePlayTester() {
		String temp_text = "World";
		ArrayList<String> lista = new ArrayList<>();
		lista.add("Hello");
		lista.add("World");
    	
    	int listsize=lista.size();
    	if(listsize>0) {
    		if(temp_text.equals(lista.get(listsize-1)) || temp_text.equals("")) {
    			lista.add("hii");
    		}
    	}

    	lista.add(temp_text);
    	assertEquals(lista.size(),4);
	}

}
