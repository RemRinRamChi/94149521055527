package spelling.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.SwingWorker;

import spelling.quiz.SpellList;


/**
 * 
 * This class controls the logic to create hidden statistics files
 * and storing results of spelling tests in data structures
 * so that mastered/faulted/failed word numbers and accuracy
 * rates for each level are correctly displayed.
 * @authors yyap601 
 *
 */
public class StatisticsModel extends SwingWorker<Void,String[]>{

	// This is the SPELLING AID STATSGUI
	StatisticsViewController spellingAidStats;

	// Files for statistics reading
	File spelling_aid_tried_words;
	File spelling_aid_statistics;
	File spelling_aid_accuracy;

	// ArrayLists for storing file contents for easier processing later according to levels
	HashMap<String, ArrayList<String>> mapOfTriedWords;	
	ArrayList<String> wordStats;
	ArrayList<String> listOfTriedWords;

	// Hashmaps to store accuracy related values for every level
	HashMap<String,Integer> totalAsked;
	HashMap<String,Integer> totalCorrect;
	HashMap<String,Integer> longestLevelStreak;
	HashMap<String,Integer> totalAttempts;

	// Store mastered, faulted, failed counts
	HashMap<String,Integer> totalMastered = new HashMap<String,Integer>();
	HashMap<String,Integer> totalFaulted = new HashMap<String,Integer>();
	HashMap<String,Integer> totalFailed = new HashMap<String,Integer>();

	int zeroWords; // counter to check if there are no words


	public StatisticsModel(StatisticsViewController spellingAidStatsGUI){

		// initialise variables appropriately
		spellingAidStats=spellingAidStatsGUI;
		zeroWords = 0; 
		spelling_aid_tried_words = new File(".spelling_aid_tried_words");
		spelling_aid_statistics = new File(".spelling_aid_statistics");
		spelling_aid_accuracy = new File(".spelling_aid_accuracy");
		mapOfTriedWords = new HashMap<String, ArrayList<String>>();
		totalAsked = new HashMap<String,Integer>();
		totalCorrect = new HashMap<String,Integer>();
		longestLevelStreak = new HashMap<String,Integer>();
		totalAttempts = new HashMap<String,Integer>();
		wordStats = new ArrayList<String>();
		listOfTriedWords = new ArrayList<String>();

		// store variables in data structures
		try {

			// LEVEL ACCURACY
			BufferedReader readAccuracyList = new BufferedReader(new FileReader(spelling_aid_accuracy));
			String accuracyLine = readAccuracyList.readLine();
			while(accuracyLine != null){
				String log = accuracyLine;
				String asked = readAccuracyList.readLine();
				String correctz = readAccuracyList.readLine();
				totalAsked.put(log, Integer.parseInt(asked));
				totalCorrect.put(log, Integer.parseInt(correctz));
				longestLevelStreak.put(log,Integer.parseInt(readAccuracyList.readLine()));
				totalAttempts.put(log, Integer.parseInt(readAccuracyList.readLine()));
				accuracyLine = readAccuracyList.readLine();
			}
			readAccuracyList.close();

			// TRIED WORDS
			BufferedReader readTriedList = new BufferedReader(new FileReader(spelling_aid_tried_words));
			String triedWord = readTriedList.readLine();
			// array to store words in a level
			ArrayList<String> triedWordsInALevel = new ArrayList<String>();
			while(triedWord != null){
				// % = level and so do appropriate things
				if(triedWord.charAt(0) == '%'){
					String levelNo = triedWord.substring(1);
					triedWordsInALevel = new ArrayList<String>();
					mapOfTriedWords.put(levelNo,triedWordsInALevel);
				} else {
					listOfTriedWords.add(triedWord);
					triedWordsInALevel.add(triedWord);
					zeroWords++; // if it is greater than 0 = there are statistics available for viewing
				}
				triedWord = readTriedList.readLine();
			}
			readTriedList.close();

			// STATS LIST
			BufferedReader getStats = new BufferedReader(new FileReader(spelling_aid_statistics));
			String word = getStats.readLine();
			while(word != null){
				wordStats.add(word);
				word = getStats.readLine();
			}
			getStats.close();
		} 
		catch(IOException e){
			e.printStackTrace();
		}
	}

	// SWINGWORKER ~~
	protected Void doInBackground(){
		spellingAidStats.clearLevelTable();
		if(zeroWords == 0){
			//publish("\n There Are NO Attempted Words !!!"); //maybe become POP UP TODO
		} else { // tried words file not empty
			// go through all the attempted levels
			for(String i : mapOfTriedWords.keySet()){
				// get a list of attempted words according to the level
				ArrayList<String> triedWordsList = mapOfTriedWords.get(i);
				// ERROR handling: if there is a level but 0 content, just skip it
				if(triedWordsList.size()==0){
					continue;
				}
				// LEVEL STATS
				String [] lvlStats= new String[]{i,""+totalAttempts.get(i),""+longestLevelStreak.get(i),""+getAccuracy(i)};
				publish(lvlStats);
				
				// SORT them
				Collections.sort(triedWordsList);
				// Check for statistic of words by getting matches and then display the results
				for(String wd : triedWordsList){
					int master = 0;
					int fault = 0;
					int fail = 0;
					for(String stat : wordStats){
						String[] wordNStat = stat.split(" ");
						if(wordNStat[0].equals(wd)){
							if(wordNStat[1].equals("Mastered")){
								master++;
							} else if(wordNStat[1].equals("Faulted")){
								fault++;
							} else {
								fail++;
							}
						}
					}
					recordWordStats(wd,master,fault,fail);
				}
			}
		}

		return null;
	}

	// this class displays data by publishing them to the JTextArea
	protected void process(List<String[]> statsData) {
		for (String[] data : statsData) {
			spellingAidStats.addToLevelTable(data);
		}
	}

	protected void done(){
		spellingAidStats.clearWordTable();
		Collections.sort(listOfTriedWords);
		for(String s : listOfTriedWords){
			spellingAidStats.addToWordTable(getWordStats(s));
		}
	}

	// calculate the accuracy for the current level
	public double getAccuracy(String level){
		double noOfQuestionsAnsweredCorrectly = totalCorrect.get(level);
		double totalQuestionsAsked = totalAsked.get(level);
		if(totalQuestionsAsked==0.0){
			return 0;
		}
		double accuracy = (noOfQuestionsAnsweredCorrectly/totalQuestionsAsked)*100.0;
		return Math.round(accuracy*10.0)/10.0;
	}

	private void recordWordStats(String word, int master, int fault, int fail){
		if(totalMastered.get(word)==null){
			totalMastered.put(word, master);
		} else {
			int mastered = totalMastered.get(word)+master;
			totalMastered.put(word, mastered);
		}

		if(totalFaulted.get(word)==null){
			totalFaulted.put(word, fault);
		} else {
			int faulted = totalFaulted.get(word)+fault;
			totalFaulted.put(word, faulted);
		}

		if(totalFailed.get(word)==null){
			totalFailed.put(word, fail);
		} else {
			int failed = totalFailed.get(word)+fail;
			totalFailed.put(word, failed);
		}
	}

	private Object[] getWordStats(String word){
		Object[] wordStats = new Object[]{word,totalMastered.get(word),totalFaulted.get(word),totalFailed.get(word)};
		return wordStats;
	}
}