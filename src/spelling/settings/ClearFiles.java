package spelling.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * 
 * This class contains functions that clears files and also functions that clear all statistic and 
 * preferences files at the same time
 * @authors yyap601 
 *
 */
public class ClearFiles {

	/**
	 *  function to return information message and at the same time clear all the statistic files
	 * @return The information message to be displayed
	 */
	public static String clearStats(){
		String infoMsg = "All Spelling Statistics Cleared";
		clearFile(new File(".spelling_aid_tried_words"));
		clearFile(new File(".spelling_aid_failed"));
		clearFile(new File(".spelling_aid_statistics"));		
		clearFile(new File(".spelling_aid_accuracy"));		
		clearFile(new File(".spelling_aid_longest_streak"));
		return infoMsg;
	}

	/**
	 *  function to return information message and at the same time clear all the preferences files
	 * @return The information message to be displayed
	 */
	public static String clearPrefs(){
		String infoMsg = "Preferences cleared !";
		clearFile(new File(".spelling_aid_other_prefs"));
		clearFile(new File(".spelling_aid_cheer"));
		clearFile(new File(".USER-spelling-lists.txt"));
		return infoMsg;
	}
	
	

	/**
	 *  function to clear a single statistic file
	 * @param file file to clear
	 */
	public static void clearFile(File file){
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
