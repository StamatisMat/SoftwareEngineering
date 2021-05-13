package application;

public class Rot {
	String lowercase_alphabet = "abcdefghijklmnopqrstuvwxyz";
	String uppercase_alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public String rot_encoding(String text)
	{
		String encoded_text="";
		String[] string_to_chars = text.split("");
		
		for(String character: string_to_chars) {
			if(lowercase_alphabet.contains(character)) {
				encoded_text+=lowercase_alphabet.charAt((lowercase_alphabet.indexOf(character)+13) % lowercase_alphabet.length());
				//encoded_text+=lowercase_alphabet.charAt(lowercase_alphabet.length()-lowercase_alphabet.indexOf(character)-1);
			}else if(uppercase_alphabet.contains(character)){
				encoded_text+=uppercase_alphabet.charAt((uppercase_alphabet.indexOf(character)+13) % uppercase_alphabet.length());
				//encoded_text+=uppercase_alphabet.charAt(uppercase_alphabet.length()-uppercase_alphabet.indexOf(character)-1);
			}else {
				encoded_text+=character;
			}
			
			
		}
		System.out.println(encoded_text);
		return encoded_text;
	}
}
