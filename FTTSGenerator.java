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

	// Constructor sets the sample controller
	public FTTSGenerator(SampleController samplecontroller) {
	   this.samplecontroller=samplecontroller;
	   System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
       VoiceManager voiceManager = VoiceManager.getInstance();
       voice = voiceManager.getVoice("kevin16");
       voice.allocate();
	}

	
	// Generator function sets the parameters of FTTS, generates a speechThread and runs it
	public void generateFTTS(final String text,float speed,float pitch,float volume) {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		VoiceManager voiceManager = VoiceManager.getInstance();
		
		// Set voice parameters
		voice = voiceManager.getVoice("kevin16");
		voice.setPitch(pitch);
	    voice.setRate(speed);
	    voice.setVolume(volume);
	    voice.allocate();
	    
		// Create speech thread	    
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
	

	public void fttsHandleVolume(float volume) {
		//System.out.println("hello from ftts: "+volume);
		voice.setVolume(volume);	
	}
	

	// Function to stop the speechThread and audio
	public void fttsStop() {
		speechThread.stop();
		if(samplecontroller.isPlaying) {
			voice.getAudioPlayer().resume(); //We resume to avoid keeping the pause for the next hearing
		}
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

	// Simple continue function

	public void fttsContinue() {
		voice.getAudioPlayer().resume();
	}

	// Simple pause function
	public void fttsPause() {
		voice.getAudioPlayer().pause();
	}
	
}