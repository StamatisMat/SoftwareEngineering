package application;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;

public class FTTSGenerator {
	Voice voice;
	Thread speechThread;
	SampleController samplecontroller;
	public FTTSGenerator(SampleController samplecontroller) {
	   this.samplecontroller=samplecontroller;
		 //speechThread = new Thread(new Runnable() {
	     //    public void run() {            
	     //    }
	     //});
	}
	public void generateFTTS(String text,float speed,float pitch) {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin16");
        voice.setPitch(pitch);
        voice.setRate(speed);
        voice.allocate();
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
	
	public Thread getFTTSThread() {
		return speechThread;
	}
	
	public Voice getFTTSVoice() {
		return voice;
	}
	
	public AudioPlayer getFTTSAudioPlayer() {
		return voice.getAudioPlayer();
	}
	
	public void fttsStop() {
		speechThread.stop();
		voice.getAudioPlayer().cancel();
	}
	
	public void fttsStopRunningThread() {	
		if(speechThread != null) {
			if(speechThread.isAlive()){ 
				fttsStop();
			}
		}
	}
	
	public void fttsContinue() {
		voice.getAudioPlayer().resume();
	}
	
	public void fttsPause() {
		voice.getAudioPlayer().pause();
	}
	
}