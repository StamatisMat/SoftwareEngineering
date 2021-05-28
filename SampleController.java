package application;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.audio.AudioPlayer;

import application.Encodings.*;



public class SampleController {
	
	File openfile;
	ArrayList<File> recentfiles = new ArrayList<File>();
	float speed=120.0f, pitch = 120.0f, volume = 1.0f;
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
	
	
	
	// Function to swap between the pause and the play button 
    public void setPauseButton(boolean var) {
        playbutton.setDisable(var);
        playbutton.setVisible(!var);
        pausebutton.setDisable(!var);
        pausebutton.setVisible(var);
    }
	
    
    //Handles the press of the button "play", we set the var temp_text to the text we want, then we store it to a final variable so we pass it to FTTSGenerator
	@FXML
	private void handlePlay() {		
		setPauseButton(true); //Swaps to the pause button
		

    	String temp_text = textArea.getSelectedText();
    	//Check for selection empty, then select whole textArea
    	if(temp_text.equals("")) {
            temp_text = textArea.getText();
            if(temp_text.equals("")) { //Check if text area empty
                setPauseButton(false);
                return;
            }   
        }
    	
    	// Handle continuation
    	int listsize=lista.getItems().size();
    	if(listsize>0) { //if recent playback
    		if(isPlaying&&(temp_text.equals(lista.getItems().get(listsize-1)) || temp_text.equals(""))) { //if playing and selection has not changed
    			handleContinue();
    			return;
    		}
    	}
    	
    	// Handles play by history listView
    	int index = lista.getSelectionModel().getSelectedIndex();
    	if(index!=-1) { 
    		temp_text=(String) lista.getSelectionModel().getSelectedItem();
    		lista.getSelectionModel().clearSelection(index);
    	}
    	
    	// If it gets here, it needs to play new audio rather than continue playing so it needs to stop the previous audio
    	ftts.fttsStopRunningThread();
		
		final String text = temp_text;
    	lista.getItems().add(temp_text); // add to recent files
    	// sets the parameters for ftts and calls it
    	pitch = 120.0f + (float) pitchID.getValue();
    	volume = (float) volumeID.getValue()/100;
    	ftts.generateFTTS(text,speed, pitch, volume);

	}// end handlePlay
	
	// Simple handler for Rot encoding
	@FXML
	public void handleRot() {
		Encoding rot = new Rot();
		textArea.setText(rot.encode(textArea.getText()));
	}
	
	// Simple handler for Atbash encoding
	@FXML
	public void handleAtbash() {
		Encoding atbash = new Atbash();
		textArea.setText(atbash.encode(textArea.getText()));
	}
	
	
	// Simple handler for double click on history list
	@FXML
    public void handleListPlayer(MouseEvent click) {	 
        if (click.getClickCount() == 2) {
        	handlePlay();
        }
    }
	
	// Simple handler for Speed increase
	@FXML
	private void handleSpeedup() {
		speed+=10;	
		setSpeedOnLabels();
	}//End handleSpeedup

	// Simple handler for Speed decrease
	@FXML
	private void handleSpeeddown() {
		speed-=10;
		//System.out.println(speed);
	}//End handleSpeeddown
	
	// Handler for stopping the audio
	@FXML
	private void handleStop() {
		setPauseButton(false);
		isPlaying=false;
		ftts.fttsStop();
	}//End handleStop
	
	// Handler for continue
	@FXML
	private void handleContinue() {
		setPauseButton(true);
		ftts.fttsContinue();
	}//End handleContinue
	
	// Handler for pause
	@FXML
	private void handlePause() {
		setPauseButton(false);
		ftts.fttsPause();
	}//End handlePause
	

	// Handler for Exit (button)
	@FXML
	private void handleExit() {
		System.exit(0);
	}// end handleExit
	
	// Handler for clearing 
	@FXML
	private void handleClear() {
		lista.getItems().clear();
	}// end handleClear
	
	// Handler for About
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
	
	//Handler for Recent files
	@FXML
	private void handleRecentFiles() {
		ArrayList<String> choices = new ArrayList<>();
		File prevfile=openfile; // We keep the previous file to return if the selection is invalid
		// loop runs through the list and builds a string list of paths to display on the decision window 
		for(File file: recentfiles){
			try {
				if(!choices.contains(file.getCanonicalPath())) {
					choices.add(0,file.getCanonicalPath());
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(choices.isEmpty()) { // If the list is empty, we should not put a decision window
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Recent Files Warning");
			alert.setContentText("No records found!");

			alert.showAndWait();
			return;
		}
		
		// The list is not empty, so we build a choice dialog to get another file
		ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
		dialog.setTitle("Recent Files");
		dialog.setHeaderText("Open recent file");
		dialog.setContentText("Choose your file:");

		Optional<String> result = dialog.showAndWait();
		if(!result.isPresent()) { //If the file we want is the same we have open we return
			return;
		}
		openfile=new File(result.get()); //We get the new file 
		if(!openfile.isFile()) { // Check if the file is valid, then if it is not we display an error message and return to previous file
			recentfiles.remove(openfile);
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Recent Files Warning");
			alert.setContentText("File does not exist!");
			alert.showAndWait();
			openfile=prevfile;
			return;
		}
		LoadData(); // We load the data from the file
		
	}// end handleRecentFiles
    
	// Simple Load Handler that creates a fileChooser and opens a file
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
		openfile = fileChooser.showOpenDialog(new Stage());

		if (openfile == null) return; // If file invalid then return

		LoadData(); // We load the data from the file
	}// end handleLoad
	
	
	// Simple Handler for Saving As that creates a  fileChooser, stores the file and loads it 
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
        File savefile = fileChooser.showSaveDialog(new Stage());
        
        if(openfile==null) return;

        docmanager.saveDocument(openfile, textArea.getText()); // We save the document
            
		LoadData(); // We load the data from the new file
    }// end handleSaveAs

    
    // Simple Save handler
    @FXML
    private void handleSave() {
    	if(openfile==null) { // If we have an invalid file, we need to save as a file
    		handleSaveAs();
    		if(openfile==null) return;
    		LoadData();	
    	}
    	else { // If the file is valid, we save it
    		docmanager.saveDocument(openfile, textArea.getText());
    	}
    }// end handleSave
	
	// Simple data loader
	private void LoadData() {
		ArrayList<String> data= new ArrayList <String>();
		data = docmanager.loadDocument(openfile); // load the data via the Document Manager
		
		// Set the file name label to the correct file, add the file to Recent Files then set the textArea to the data we loaded 
		filename.setText(openfile.getName());
		recentfiles.add(openfile);
		textArea.clear();
		for (int i = 0; i < data.size(); i++) {
			textArea.appendText(data.get(i));
		}	
	}
	
	// Simple setter function to update the speed label
	private void setSpeedOnLabels() {
		String text="Speed = "+speed;
		speedID.setText(text);
	}	
}

