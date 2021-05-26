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
	}
	
	public void generateFTTS(final String text,float speed,float pitch,float volume) {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		VoiceManager voiceManager = VoiceManager.getInstance();
		voice = voiceManager.getVoice("kevin16");
		voice.setPitch(pitch);
	    voice.setRate(speed);
	    voice.setVolume(volume);
	    voice.allocate();
	    //System.out.println(voice.getRate());
        speechThread = new Thread(new Runnable() {
            public void run() {
        	    if(samplecontroller!=null) {
                	samplecontroller.isPlaying = true;
                    voice.speak(text);
                    samplecontroller.isPlaying = false;
                    samplecontroller.setPauseButton(false);  
        	    }
        	    else {
        	    	voice.speak(text);
        	    }
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
		if(samplecontroller.isPlaying) {
			voice.getAudioPlayer().resume(); //We Resume to avoid keeping the pause for the next hearing
		}
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