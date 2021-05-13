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
	VolumeControl volumeController;
	boolean isPlaying=false;
	FTTSGenerator ftts = new FTTSGenerator(this);
	
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
	
	
	
    public void setPauseButton(boolean var) {
        playbutton.setDisable(var);
        playbutton.setVisible(!var);
        pausebutton.setDisable(!var);
        pausebutton.setVisible(var);
    }
	
	@FXML
	private void handlePlay() {
		//TODO just for testing
		Rot rot = new Rot();
		rot.rot_encoding("Dokimastiko Sthn Grammh 74 tou SampleController");
		rot.rot_encoding("Qbxvznfgvxb Fgua Tenzzu 75 gbh FnzcyrPbagebyyre");
		
		//TODO just for testing
		//Atbash atb = new Atbash();
		//atb.atbash_encoding("Dokimastiko Sthn Grammh 79 tou SampleController");
		//atb.atbash_encoding("Wlprnzhgrpl Hgsm Tiznns 80 glf HznkovXlmgiloovi");
		
		//TODO just for testing
		//SaveData(textArea.getText());
		
		setPauseButton(true);

    	String temp_text = textArea.getSelectedText();
    	if(temp_text.equals("")) {
            temp_text = textArea.getText();
            //System.out.println(temp_text);
            if(temp_text.equals("")) {
                setPauseButton(false);
                return;
            }   
        }
    	
    	int listsize=lista.getItems().size();
    	//System.out.println(temp_text);
    	if(listsize>0) {
    		if(isPlaying&&(temp_text.equals(lista.getItems().get(listsize-1)) || temp_text.equals(""))) {
    			//System.out.println(temp_text);
    			//System.out.println("prepei na paw parakatw");
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
    	//System.out.println("Volume: "+volumeID.getValue());
    	float pitch = 120.0f + (float) pitchID.getValue();
    	ftts.generateFTTS(text,speed, pitch);
	}// end handlePlay
	
	@FXML
    public void handleListPlayer(MouseEvent click) {	 
        if (click.getClickCount() == 2) {
        	handlePlay();
        }
    }//End handleListPlayer
	
	@FXML
	private void handleSpeedup() {
		speed+=10;
		//System.out.println(speed);
	}//End handleSpeedup
	
	@FXML
	private void handleSpeeddown() {
		speed-=10;
		//System.out.println(speed);
	}//End handleSpeeddown
	
	@FXML
	private void handleStop() {
		setPauseButton(false);
		isPlaying=false;
		ftts.fttsStop();
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
		//System.out.println("VOLUMEEEE: "+volumeID.getValue());
		ftts.fttsHandleVolume((float) volumeID.getValue()/100);
		//volumeController.setVolume((float) volumeID.getValue()/100);
		
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
	}// end handleAbout
	
	@FXML
	private void handleRecentFiles() {
		ArrayList<String> choices = new ArrayList<>();
		for(File file: recentfiles){
			try {
				if(!choices.contains(file.getCanonicalPath())) {
					choices.add(0,file.getCanonicalPath());
				}	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(choices.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Recent Files Warning");
			alert.setContentText("Íot recently found records");

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

        
        //TODO 
        try {
            FileWriter fileWriter = new FileWriter(openfile);
            fileWriter.write(textArea.getText());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		LoadData();
    }// end handleSaveAs
    
    @FXML
    private void handleSave() {
    	if(openfile==null) {
    		handleSaveAs();
    		LoadData();	
    	}
    	else {
    		//TODO 
            try {
                FileWriter fileWriter = new FileWriter(openfile);
                fileWriter.write(textArea.getText());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}
    }// end handleSave

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}
	
	//TODO
	private void LoadData() {
		String fileextension = getFileExtension(openfile);
		
		ArrayList<String> data = new ArrayList<String>();
		
		if (fileextension.equals("doc") || fileextension.equals("docx")) {
			WordReader wordfile = new WordReader();
			data = wordfile.fileReader(openfile);
		}
		
		else if (fileextension.equals("xls") || fileextension.equals("xlsx")) {
			ExcelReader wordfile = new ExcelReader();
			data = wordfile.fileReader(openfile);
		}
		
		else if (fileextension.equals("txt")) {
			TxtReader txtfile = new TxtReader();
			data = txtfile.fileReader(openfile);
		}
		
		textArea.clear();
		filename.setText(openfile.getName());
		recentfiles.add(openfile);
		for (int i = 0; i < data.size(); i++) {
			textArea.appendText(data.get(i));
		}
	}
	
	//TODO
	private void SaveData(String text) {
		//WordSaver wordfile = new WordSaver();
		ExcelSaver excelfile = new ExcelSaver();
		try {
			//wordfile.SaveWordfile(text,openfile);
			excelfile.SaveExcelfile(text,openfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}