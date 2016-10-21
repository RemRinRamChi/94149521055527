package spelling.quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.SwingWorker;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * This class is a swingworker class which obtains definitions for a list of words
 * @author yyap601
 *
 */
public class DefinitionGetter extends SwingWorker<Void,String>{
	ArrayList<String> words;
	HashMap<String,String> definitions;
	String wordBeingDefined;
	
	public DefinitionGetter(ArrayList<String> wordsToDefine, HashMap<String,String> theDefinitions){
		words = wordsToDefine;
		definitions = theDefinitions;
	}

	// define words in the background and return results one by one
	protected Void doInBackground() throws Exception {
		for(String w : words){
			wordBeingDefined = w;
			publish(defineWord(wordBeingDefined));
		}
		return null;
	}
	
	// store meaning of words in a dictionary
	protected void process(List<String> definition) {
		for (String def : definition) {
			definitions.put(wordBeingDefined, def);
		}
	}
	
	/**
	 *  returns the first definition of a word from dictionary.com with jsoup 
	 * @param wordToDefine the word to define
	 * @return the definition of the word to define
	 */
	public static String defineWord(String wordToDefine){
		String definition = "";
		// replace spaces between the word with "-" to be used for definition searching
		String[]wordToCompress=wordToDefine.split(" ");
		wordToDefine = "" ;
		int c = 0;
		for(String s : wordToCompress){
			c++;
			if(c != wordToCompress.length){
				wordToDefine = wordToDefine+s+"-";
			} else {
				wordToDefine = wordToDefine+s;
			}
		}
		
		try{
		// get the html script of the page
		Document doc = Jsoup.connect("http://www.dictionary.com/browse/"+wordToDefine).get();
		// get the first definition of the word
		Element def = doc.select("div.def-content").first();
		definition = def.text();
		// ':' is when an example is given after the definition, so want everything before that
		int colonPos = 0;
			for(int i = 0; i<definition.length(); i++){
				if(definition.charAt(i)==':'){
					colonPos = i;
					break;
				}
			}
		if(colonPos!=0){
			definition = definition.substring(0, colonPos);
		} else {
			definition = "There are no definitions available for this word.";
		}
		} catch (HttpStatusException hE){
			definition = "There are no definitions available for this word.";
		} catch (IOException ioE){
			ioE.printStackTrace();
		}
		return definition;
	}
}
