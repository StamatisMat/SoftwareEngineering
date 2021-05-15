package application;

public abstract class Encoding {
	String lowercase_alphabet = "abcdefghijklmnopqrstuvwxyz";
	String uppercase_alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public abstract String encode(String text);
	
}
