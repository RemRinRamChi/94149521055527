package spelling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * 
 * This class is a collection of helper functions that are used in multiple classes
 * @authors yyap601
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
			//e.printStackTrace(); //Commented out for testing on Windows
		}
	}
	
	/**
	 * Get the list of level names in a word list
	 */
	public static String[] getLevelValues(File lists){
		ArrayList<String> returns = new ArrayList<String>();
		try {
			BufferedReader readFromList = new BufferedReader(new FileReader(lists));
			String word = readFromList.readLine();
			while(word != null){
				if(word.charAt(0)=='%'){
					if(!returns.contains(word.substring(1))){
						returns.add(word.substring(1));
					}
				}
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