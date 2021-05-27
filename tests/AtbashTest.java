package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.Atbash;

public class AtbashTest {
	@Test
	@DisplayName("Atbash tester")
	public void testAtbash() {
		Atbash atbash =new Atbash();
		assertEquals("Svool Dliow", atbash.encode("Hello World"));
	}	
}
