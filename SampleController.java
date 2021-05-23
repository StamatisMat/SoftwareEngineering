package application;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.audio.AudioPlayer;



public class SampleController {
	
	File openfile;
	ArrayList<File> recentfiles = new ArrayList<File>();
	float speed=120.0f;
	Voice voice;
	AudioPlayer audioplayer;
	boolean isPlaying=false;
	FTTSGenerator ftts = new FTTSGenerator(this);
	DocumentManager docmanager = new DocumentManager();

	@FXML
	TextArea textArea;

	@FXML
	Label filename;
	
	@FXML
	ListView lista;
	
	@FXML
	Button pausebutton;
	
	@FXML
	Button playbutton;
	
	@FXML
	Slider volumeID;
	
	@FXML
	Slider pitchID;
	
	@FXML 
	Label speedID;
	
	
    public void setPauseButton(boolean var) {
        playbutton.setDisable(var);
        playbutton.setVisible(!var);
        pausebutton.setDisable(!var);
        pausebutton.setVisible(var);
    }
	
	@FXML
	private void handlePlay() {		
		setPauseButton(true);
		
    	String temp_text = textArea.getSelectedText();
    	if(temp_text.equals("")) {
            temp_text = textArea.getText();
            if(temp_text.equals("")) {
                setPauseButton(false);
                return;
            }   
        }
    	
    	int listsize=lista.getItems().size();
    	if(listsize>0) {
    		if(isPlaying&&(temp_text.equals(lista.getItems().get(listsize-1)) || temp_text.equals(""))) {
    			handleContinue();
    			return;
    		}
    	}
    	
    	int index = lista.getSelectionModel().getSelectedIndex();
    	if(index!=-1) {
    		temp_text=(String) lista.getSelectionModel().getSelectedItem();
    		lista.getSelectionModel().clearSelection(index);
    	}
    	
    	
    	ftts.fttsStopRunningThread();
		
		final String text =temp_text;
    	lista.getItems().add(temp_text);
    	float pitch = 120.0f + (float) pitchID.getValue();
    	ftts.generateFTTS(text,speed, pitch);
	}// end handlePlay
	
	@FXML
	public void handleRot() {
		Encoding rot = new Rot();
		textArea.setText(rot.encode(textArea.getText()));
	}
	
	@FXML
	public void handleAtbash() {
		Encoding atbash = new Atbash();
		textArea.setText(atbash.encode(textArea.getText()));
	}
	
	
	@FXML
    public void handleListPlayer(MouseEvent click) {	 
        if (click.getClickCount() == 2) {
        	handlePlay();
        }
    }//End handleListPlayer
	
	@FXML
	private void handleSpeedup() {
		speed+=10;
		setSpeedOnLabels();
	}//End handleSpeedup
	
	@FXML
	private void handleSpeeddown() {
		speed-=10;
		setSpeedOnLabels();
	}//End handleSpeeddown
	
	@FXML
	private void handleStop() {
		setPauseButton(false);
		ftts.fttsStop();		
		isPlaying=false;
	}//End handleStop
	
	@FXML
	private void handleContinue() {
		setPauseButton(true);
		ftts.fttsContinue();
	}//End handleContinue
	
	@FXML
	private void handlePause() {
		setPauseButton(false);
		ftts.fttsPause();
	}//End handlePause
	
	@FXML
	private void handleVolume() {
		ftts.fttsHandleVolume((float) volumeID.getValue()/100);
	}//End handleVolume
	
	@FXML
	private void handleExit() {
		System.exit(0);
	}// end handleExit
	
	@FXML
	private void handleClear() {
		lista.getItems().clear();
	}// end handleClear
	
	@FXML
	private void handleAbout() {
		Label label=new Label("Dimosthenis Georgoulas AM 4039\nStamatis Matziounis AM 4107\nLiatsos Nikolaos AM 4101");
		label.setWrapText(true);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information about the project");
		alert.setHeaderText("Software Engineering Project");
		alert.getDialogPane().setContent(label);
		alert.showAndWait();
	}// end handleAbout
	
	@FXML
	private void handleRecentFiles() {
		ArrayList<String> choices = new ArrayList<>();
		File prevfile=openfile;
		for(File file: recentfiles){
			try {
				if(!choices.contains(file.getCanonicalPath())) {
					choices.add(0,file.getCanonicalPath());
				}	
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		if(choices.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Recent Files Warning");
			alert.setContentText("No records found!");

			alert.showAndWait();
			return;
		}
		
		ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
		dialog.setTitle("Recent Files");
		dialog.setHeaderText("Open recent file");
		dialog.setContentText("Choose your file:");

		Optional<String> result = dialog.showAndWait();
		if(!result.isPresent()) {
			return;
		}
		openfile=new File(result.get());
		if(!openfile.isFile()) {
			recentfiles.remove(openfile);
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Recent Files Warning");
			alert.setContentText("File does not exist!");
			alert.showAndWait();
			openfile=prevfile;
			return;
		}
		LoadData();
		
	}// end handleRecentFiles
    
	@FXML
	private void handleLoad() {		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Pick a file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"),
				new FileChooser.ExtensionFilter("Word-2003", "*.doc"),
				new FileChooser.ExtensionFilter("Word", "*.docx"),
				new FileChooser.ExtensionFilter("Excel-2003", "*.xls"),
				new FileChooser.ExtensionFilter("Excel", "*.xlsx"),
				new FileChooser.ExtensionFilter("Text", "*.txt"));
		openfile = fileChooser.showOpenDialog(new Stage());
		if (openfile == null) return;

		LoadData();
	}// end handleLoad
	
	
    @FXML
    private void handleSaveAs() {
		
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("Word-2003", "*.doc"),
                new FileChooser.ExtensionFilter("Word", "*.docx"),
                new FileChooser.ExtensionFilter("Excel-2003", "*.xls"),
                new FileChooser.ExtensionFilter("Excel", "*.xlsx"),
                new FileChooser.ExtensionFilter("Text", "*.txt"));
        openfile = fileChooser.showSaveDialog(new Stage());    
        
        if(openfile==null) return;

        docmanager.saveDocument(openfile, textArea.getText());
            
		LoadData();
    }// end handleSaveAs
    
    @FXML
    private void handleSave() {
    	if(openfile==null) {
    		handleSaveAs();
    		LoadData();	
    	}
    	else {
    		docmanager.saveDocument(openfile, textArea.getText());
    	}
    }// end handleSave
	
	//TODO
	private void LoadData() {
		ArrayList<String> data= new ArrayList <String>();
		data = docmanager.loadDocument(openfile);
		filename.setText(openfile.getName());
		recentfiles.add(openfile);
		textArea.clear();
		for (int i = 0; i < data.size(); i++) {
			textArea.appendText(data.get(i));
		}	
	}
	
	private void setSpeedOnLabels() {
		String text="Speed = "+speed;
		speedID.setText(text);
	}
	
}