package spelling.quiz;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * This class is a swingworker class which obtains the definition for a word
 * @author yyap601
 *
 */
public class DefinitionGetter extends SwingWorker<String,Void>{
	String wordBeingDefined;
	Quiz spellQuiz;

	public DefinitionGetter(String word, Quiz quiz){
		wordBeingDefined = word;
		spellQuiz = quiz;
	}

	// define words in the background and return results one by one
	protected String doInBackground() throws Exception {
		return defineWord(wordBeingDefined);
	}

	protected void done(){
		try {
			spellQuiz.setDefinition(get());
			spellQuiz.scrollDefinitionAreaToTop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a DefinitionGetter swing worker to be called execute() on
	 * @param wordBeingDefined the word being defined
	 * @param quiz the quiz object
	 * @return a DefinitionGetter swing worker
	 */
	public static DefinitionGetter getDefinitionGetter(String wordBeingDefined, Quiz quiz){
		DefinitionGetter defGetter = new DefinitionGetter(wordBeingDefined, quiz);
		return defGetter;
	}

	/**
	 *  returns the first definition of a word from dictionary.com with jsoup 
	 * @param wordToDefine the word to define
	 * @return the definition of the word to define
	 */
	public static String defineWord(String wordToDefine){
		// to account for the only case which this might fail because we are checking fro everything before the letter colon
		if(wordToDefine.equals("colon".toLowerCase())){
			return "the sign (:) used to mark a major division in a sentence, to indicate that what follows "
					+ "is an elaboration, summation, implication, etc., of what precedes; or to separate groups "
					+ "of numbers referring to different things, as hours from minutes in 5:30; or the members of "
					+ "a ratio or proportion, as in 1 : 2 = 3 : 6.";
		}
		String definition = "definition";
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
			int colonPos = definition.length();
			for(int i = 0; i<definition.length(); i++){
				if(definition.charAt(i)==':'){
					colonPos = i;
					break;
				}
			}
			definition = definition.substring(0, colonPos);
		} catch (HttpStatusException hE){
			definition = "There are no definitions available for this word.";
		} catch (UnknownHostException hE){
			// when user doesn't have internet connection
			definition = "Please make sure you have internet connection for this to work.";
		} catch (IOException ioE){
			ioE.printStackTrace();

		}
		return definition;
	}
}
