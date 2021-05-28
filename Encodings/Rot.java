package application.Encodings;

/*
 	Rot class, implements rot encoding and returns the encoded text
*/
public class Rot extends Encoding{
	
	public String encode(String text)
	{
		String encoded_text="";
		String[] string_to_chars = text.split("");
		
		//if text is not empty, use simple character swap to encode the text 
		if(text!="") {
			for(String character: string_to_chars) {
				if(lowercase_alphabet.contains(character)) {
					encoded_text+=lowercase_alphabet.charAt((lowercase_alphabet.indexOf(character)+13) % lowercase_alphabet.length());			
				}else if(uppercase_alphabet.contains(character)){
					encoded_text+=uppercase_alphabet.charAt((uppercase_alphabet.indexOf(character)+13) % uppercase_alphabet.length());
				}else { // If not supported character, we add it as is
					encoded_text+=character;
				}
			}
		}

		return encoded_text;
	}
}
