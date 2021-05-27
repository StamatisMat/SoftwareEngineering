package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.FTTSGenerator;

public class FTTSGeneratorTest {
	FTTSGenerator ftts = new FTTSGenerator(null);
	
	@Test
	@DisplayName("Voice speed,pitch,volume tester")
	public void testVoice() {
		ftts.generateFTTS("Hello world", 120.0f, 120.0f, 1.0f);
		//System.out.println(ftts.getFTTSVoice().getVolume());
		assertEquals(22500/120.0f, ftts.getFTTSVoice().getRate());
		assertEquals(120.0f, ftts.getFTTSVoice().getPitch());
		assertEquals(1.0f, ftts.getFTTSVoice().getVolume());
		
	}	
}