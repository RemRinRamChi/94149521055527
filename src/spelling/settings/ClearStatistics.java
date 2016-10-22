package spelling.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * 
 * This class contains functions that clear the statistic files and displays a confirmation to the user
 * @authors yyap601 
 *
 */
public class ClearStatistics {

	/**
	 *  function to return information message and at the same time clear all the statistic files
	 * @return The information message to be displayed
	 */
	public static String clearStats(){
		String infoMsg = "All Spelling Statistics Cleared";
		clearStatsFiles();
		return infoMsg;
	}

	/**
	 *  function to clear all the statistic files
	 */
	private static void clearStatsFiles(){
		clearFile(new File(".spelling_aid_tried_words"));
		clearFile(new File(".spelling_aid_failed"));
		clearFile(new File(".spelling_aid_statistics"));		
		clearFile(new File(".spelling_aid_accuracy"));		
		clearFile(new File(".spelling_aid_longest_streak"));
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
