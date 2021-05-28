package application;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;

/*
 * FTTSGenerator class: it gets a string of text and generates a speech thread that handles the audio 
*/

public class FTTSGenerator {
	Voice voice;
	Thread speechThread;
	SampleController samplecontroller;
<<<<<<< Updated upstream
=======
	
	// Constructor sets the sample controller
>>>>>>> Stashed changes
	public FTTSGenerator(SampleController samplecontroller) {
	   this.samplecontroller=samplecontroller;
	   System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
       VoiceManager voiceManager = VoiceManager.getInstance();
       voice = voiceManager.getVoice("kevin16");
       voice.allocate();
		 //speechThread = new Thread(new Runnable() {
	     //    public void run() {            
	     //    }
	     //});
	}
<<<<<<< Updated upstream
	public void generateFTTS(final String text,float speed,float pitch) {
	    
		voice.setPitch(pitch);
	    voice.setRate(speed);
=======
	
	// Generator function sets the parameters of FTTS, generates a speechThread and runs it
	public void generateFTTS(final String text,float speed,float pitch,float volume) {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		VoiceManager voiceManager = VoiceManager.getInstance();
		
		voice = voiceManager.getVoice("kevin16");
		voice.setPitch(pitch);
	    voice.setRate(speed);
	    voice.setVolume(volume);
	    voice.allocate();
	    
>>>>>>> Stashed changes
	    
        speechThread = new Thread(new Runnable() {
            public void run() {
            	samplecontroller.isPlaying = true;
                voice.speak(text);
                samplecontroller.isPlaying = false;
                samplecontroller.setPauseButton(false);  
            }
        });
        
        speechThread.setDaemon(true);//kill all threads after main window close
        speechThread.start();
	}
	
	// Getter for the speechThread
	public Thread getFTTSThread() {
		return speechThread;
	}
	// Getter for the voice	
	public Voice getFTTSVoice() {
		return voice;
	}
	
	// Getter for the audioPlayer
	public AudioPlayer getFTTSAudioPlayer() {
		return voice.getAudioPlayer();
	}
	
<<<<<<< Updated upstream
	public void fttsHandleVolume(float volume) {
		//System.out.println("hello from ftts: "+volume);
		voice.setVolume(volume);	
	}
	
	public void fttsStop() {
		speechThread.stop();
=======
	// Function to stop the speechThread and audio
	public void fttsStop() {
		speechThread.stop();
		if(samplecontroller.isPlaying) {
			voice.getAudioPlayer().resume(); //We resume to avoid keeping the pause for the next hearing
		}
>>>>>>> Stashed changes
		voice.getAudioPlayer().cancel();
	}
	
	// Function to stop the running thread if it exists
	public void fttsStopRunningThread() {	
		if(speechThread != null) {
			if(speechThread.isAlive()){ 
				fttsStop();
			}
		}
	}
	
<<<<<<< Updated upstream
	
=======
	// Simple continue function
>>>>>>> Stashed changes
	public void fttsContinue() {
		voice.getAudioPlayer().resume();
	}

	// Simple pause function
	public void fttsPause() {
		voice.getAudioPlayer().pause();
	}
	
}