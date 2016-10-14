package spelling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import spelling.settings.ClearStatistics;

/**
 * 
 * This class is a collection of helper functions 
 * that are used in multiple classes
 * @authors yyap601 hchu167
 *
 */
public class Tools {
	// record a word to a file
	public static void record(File file,String word){
		try{
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
			writer.println(word);
			writer.close();
		} catch(IOException e){
			System.out.println("An I/O Error Occurred");
			System.exit(0);
		}
	}

	// to run BASH commands
	public static void processStarter(String command){
		// process builder to run bash commands
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", command);
		Process process;
		try {
			process = builder.start();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Replace the contents of a file with another
	 * @param from file to get contents from
	 * @param to file to have its contents be replaced
	 */
	public static String[] addFromToAndGetTitles(File from, File to){
		ArrayList<String> returns = new ArrayList<String>();
		ClearStatistics.clearFile(to);//
		try {
			BufferedReader readFromList = new BufferedReader(new FileReader(from));
			String word = readFromList.readLine();
			while(word != null){
				if(word.charAt(0)=='%'){
					if(!returns.contains(word.substring(1))){
						returns.add(word.substring(1));
					}
				}
				record(to,word);
				word = readFromList.readLine();
			}
			readFromList.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] ws = new String[returns.size()];
		int i = 0;
		for(String w : returns){
			ws[i] = w;
			i++;
		}
		
		return ws;
	}
}