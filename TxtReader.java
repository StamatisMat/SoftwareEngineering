package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TxtReader {

	public ArrayList<String> fileReader(File file) {
		String line = "";
		BufferedReader br = null;
		ArrayList<String> data = new ArrayList<String>();
		
		try {

			br = new BufferedReader(new FileReader(file.getCanonicalPath()));
			while ((line = br.readLine()) != null) {
				data.add(line);
				data.add("\n");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}

}
