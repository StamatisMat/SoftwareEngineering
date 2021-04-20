package application;

import java.io.File;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;



public class SampleController {

	ArrayList<File> recentfiles = new ArrayList<File>();
	float speed=130.0f;
	 Voice voice;
	 AudioPlayer audioplayer;
	 Thread speechThread = new Thread(new Runnable() {
         public void run() {            
         }
     });
	
	
	@FXML
	TextArea textArea;

	@FXML
	Label filename;
	
	@FXML
	ListView lista;
	

	@FXML
	private void handlePlay() {
		if(speechThread.isAlive()){
			//speechThread.stop();
			System.out.println("Eimai edw");
		}
    	System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        //String text = "Voice check!";
    	String text = textArea.getSelectedText();
    	lista.getItems().add(textArea.getSelectedText());
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin");
        voice.setPitch(150.0f);
        voice.setRate(speed);
        voice.allocate();
        //voice.speak(text);
        //voice.speak(text);
        speechThread = new Thread(new Runnable() {
            public void run() {
                voice.speak(text);
               
            }
        });
        speechThread.setDaemon(true);//kill all threads after main window close
        speechThread.start();
        
	}// end handlePlay
	
	
	@FXML
	private void handleSpeedup() {
		speed+=10;
		System.out.println(speed);
	}
	
	@FXML
	private void handleStop() {
		audioplayer=voice.getAudioPlayer();
        audioplayer.cancel();
	}
	
	@FXML
	private void handleSpeeddown() {
		speed-=10;
		System.out.println(speed);
	}
	
	@FXML
	private void handleContinue() {
		audioplayer=voice.getAudioPlayer();
        audioplayer.resume();
	}
	
	@FXML
	private void handlePause() {
		audioplayer=voice.getAudioPlayer();
        audioplayer.pause();
	}
	
	@FXML
	private void handleExit() {
		System.exit(0);
	}// end handleExit
	
	@FXML
	private void handleAbout() {
		Label label=new Label("Dimosthenis Georgoulas AM 4039\nStamatis Matziounis AM 4107\nLiatsos Nikolaos AM 4101");
		label.setWrapText(true);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information about the project");
		alert.setHeaderText("Software Engineering Project");
		alert.getDialogPane().setContent(label);
		alert.showAndWait();
	}
	
	@FXML
	private void handleRecentFiles(File file) {
		System.exit(0);
	}// end handleRecentFiles
    
	@FXML
	private void handleLoad() {
		String fileextension = "";
		ArrayList<String> data = new ArrayList<String>();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Pick a file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"),
				new FileChooser.ExtensionFilter("Word-2003", "*.doc"),
				new FileChooser.ExtensionFilter("Word", "*.docx"),
				new FileChooser.ExtensionFilter("Excel-2003", "*.xls"),
				new FileChooser.ExtensionFilter("Excel", "*.xlsx"),
				new FileChooser.ExtensionFilter("Text", "*.txt"));
		File file = fileChooser.showOpenDialog(new Stage());
		
		fileextension = getFileExtension(file);
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
		
		textArea.clear();
		filename.setText(file.getName());
		recentfiles.add(file);
		for (int i = 0; i < data.size(); i++) {
			textArea.appendText(data.get(i));
		}
	}// end handleLoad

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}	 

}

