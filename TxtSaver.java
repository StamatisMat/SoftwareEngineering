package application;

import java.io.File;
import java.io.FileWriter;

public class TxtSaver {
	public void SaveTxtFile(String text,File file) throws Exception {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(text);
        fileWriter.close();
	}
}