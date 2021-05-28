package application.Encodings;

/*
	Abstract Encoding class, which the encoding classes will extend
*/

public abstract class Encoding {
	String lowercase_alphabet = "abcdefghijklmnopqrstuvwxyz";
	String uppercase_alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public abstract String encode(String text);
	
}
