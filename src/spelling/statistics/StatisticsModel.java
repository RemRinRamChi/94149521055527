package spelling.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import spelling.SpellingAidMain;
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

	// mainFrame for changing cards
	SpellingAidMain mainFrame;
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


	public StatisticsModel(SpellingAidMain main){

		// initialise variables appropriately
		mainFrame = main;
		spellingAidStats=main.getVoxSpellStats();
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
					if(! listOfTriedWords.contains(triedWord)){
						listOfTriedWords.add(triedWord); // don't want to have duplicates
					}
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
			//no words
		} else { // tried words file not empty
			Object[] sortedKeys = mapOfTriedWords.keySet().toArray();
			Arrays.sort(sortedKeys);


			// go through all the attempted levels
			for(Object j : sortedKeys){
				String i = (String)j;
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
			// add to level table
			spellingAidStats.addToLevelTable(data);
		}
	}

	protected void done(){
		// add to tried words table in the end and not in process because there might be repeating words in different levels
		spellingAidStats.clearWordTable();
		Collections.sort(listOfTriedWords);
		for(String s : listOfTriedWords){
			spellingAidStats.addToWordTable(getWordStats(s));
		}
		// act accordingly to statistics state
		if(mainFrame.getVoxSpellStats().isStatsEmpty()){
			// empty so don't have to go in to stats
			JOptionPane.showMessageDialog(mainFrame, "There are no attempted quizzes !", "VoxSpell Statistics Empty", JOptionPane.INFORMATION_MESSAGE);
		} else {
			mainFrame.changeCardPanel("Stats");
		}
	}

	/**
	 * Calculate the accuracy for the current level
	 * @param level
	 * @return level accuracy
	 */
	public double getAccuracy(String level){
		double noOfQuestionsAnsweredCorrectly = totalCorrect.get(level);
		double totalQuestionsAsked = totalAsked.get(level);
		if(totalQuestionsAsked==0.0){
			return 0;
		}
		double accuracy = (noOfQuestionsAnsweredCorrectly/totalQuestionsAsked)*100.0;
		return Math.round(accuracy*10.0)/10.0;
	}

	/**
	 * Record stats of each word
	 * @param word word
	 * @param master mastered counts
	 * @param fault faulted counts
	 * @param fail failed counts
	 */
	private void recordWordStats(String word, int master, int fault, int fail){
		totalMastered.put(word, master);
		totalFaulted.put(word, fault);
		totalFailed.put(word, fail);
	}

	/**
	 * Return word stats in the form of an Object array to be appended to a table
	 * @param word the word
	 * @return Object array to be appended to a table
	 */
	private Object[] getWordStats(String word){
		Object[] wordStats = new Object[]{word,totalMastered.get(word),totalFaulted.get(word),totalFailed.get(word)};
		return wordStats;
	}
}