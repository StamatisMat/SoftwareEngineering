package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.Rot;

public class RotTest {
	@Test
	@DisplayName("Rot-13 tester")
	public void testRot() {
		Rot rot =new Rot();
		assertEquals("Uryyb Jbeyq", rot.encode("Hello World"));
	}	
}
