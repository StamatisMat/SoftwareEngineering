package application;

public class Rot extends Encoding{
	
	public String encode(String text)
	{
		String encoded_text="";
		String[] string_to_chars = text.split("");
		
		for(String character: string_to_chars) {
			if(lowercase_alphabet.contains(character)) {
				encoded_text+=lowercase_alphabet.charAt((lowercase_alphabet.indexOf(character)+13) % lowercase_alphabet.length());			
			}else if(uppercase_alphabet.contains(character)){
				encoded_text+=uppercase_alphabet.charAt((uppercase_alphabet.indexOf(character)+13) % uppercase_alphabet.length());
			}else {
				encoded_text+=character;
			}
		}
		return encoded_text;
	}
}
