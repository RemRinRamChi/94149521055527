package spelling.quiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import spelling.Tools;
import spelling.settings.ClearStatistics;

/**
 * 
 * This class controls the logic to ask questions 
 * and generate festival commands during the spelling quiz.
 * Getter functions are also used so GUI output and statistics
 * can be linked in a proper manner.
 * @authors yyap601 
 *
 */
public class SpellList {

	// Only 2 types of quiz modes
	public enum QuizMode{New, Review,NoQuestions};
	public enum QuizState{Asking, Answering, Answered };

	// initialising variables to use during quiz TO KEEP TRACK OF QUESTIONS AND ATTEMPT COUNTS

	// There are two spelling types: new and review
	QuizMode spellType;
	// Question Number
	int questionNo; 	
	// Current Streak
	int currentStreak;
	// Longest Streak
	int longestStreak;
	// Current Level
	String currentLevel;
	// True if question has been attempted (according to current question)
	private boolean attempt = false; 
	private boolean endOfQuestion = false;

	// Current word to spell
	private String wordToSpell; 	 
	// There are three types of spelling status: ASKING, ANSWERING and ANSWERED
	QuizState status;
	// User's answer is stored here
	private String userAnswer = "0";

	// This is the SPELLING AID APP
	private Quiz spellingAidQuiz;

	// Number of correct answers
	private int correctAnsCount = 0;

	// List to ask questions from 
	ArrayList<String> currentQuizList ;
	// List to record statistics for the current level
	ArrayList<String> currentFailedList ;
	ArrayList<String> currentTriedList ;

	// Files that contains the word list and statistics
	File wordList;
	File usersList;
	File spelling_aid_tried_words;
	File spelling_aid_failed;
	File spelling_aid_statistics;
	File spelling_aid_accuracy;

	// ArrayLists for storing file contents for easier processing later according to levels
	HashMap<String, ArrayList<String>> mapOfWords;
	HashMap<String, ArrayList<String>> mapOfFailedWords;
	HashMap<String, ArrayList<String>> mapOfTriedWords;

	// Hashmaps to store accuracy related values for every level
	HashMap<String,Integer> totalAsked;
	HashMap<String,Integer> totalCorrect;

	// Hashmap to store longest streak of each level
	HashMap<String,Integer> longestLevelStreak = new HashMap<String,Integer>();

	// Hashmap to store how many times a level has been attempted
	HashMap<String,Integer> totalAttempts = new HashMap<String,Integer>();
	
	/**
	 * Constructor of spellinglist model for current session 
	 */
	public SpellList(){
		// Files that contains the word list and statistics
		wordList = new File("NZCER-spelling-lists.txt");
		usersList = new File("USER-spelling-lists.txt");
		spelling_aid_tried_words = new File(".spelling_aid_tried_words");
		spelling_aid_failed = new File(".spelling_aid_failed");
		spelling_aid_statistics = new File(".spelling_aid_statistics");
		spelling_aid_accuracy = new File(".spelling_aid_accuracy");

		// INITIALISING LISTS TO STORE VALUES
		initialiseListsToStoreValuesFromWordAndStatsList();
	}

	// List of getter methods to access state stored during spelling quiz at particular time

	// get number of questions
	public int getNoOfQuestions(){
		return currentQuizList.size();
	}

	// get number of questions
	public String getCurrentLevel(){
		return currentLevel;
	}

	//get current word 
	public String getCurrentWord(){
		return wordToSpell;
	}

	//get correct answer count
	public int getCorrectAns(){
		return correctAnsCount;
	}

	/**
	 * Creates a list of words to test according to level and mode
	 * @param level
	 * @param spellingType New or Review
	 * @param quiz
	 */
	public void createLevelList(String level, QuizMode spellingType, Quiz quiz){

		// For every level these following variables start as follows
		spellingAidQuiz = quiz;
		questionNo = 0;
		correctAnsCount = 0;
		resetCurrentStreak();
		
		if(longestLevelStreak.get(level)==null){
			longestStreak = currentStreak;
			longestLevelStreak.put(level, longestStreak);
			
		} else {
			longestStreak = longestLevelStreak.get(level);
		}
		
		currentLevel = level;
		spellType=spellingType;
		status = QuizState.Asking;

		// update quiz field with level
		spellingAidQuiz.setCurrentQuiz(": "+level);
		
		// update longest streak label
		spellingAidQuiz.setLongestStreak(": " + longestStreak);

		// size of question list, might change depending on size of word list
		int questionListSize = 10;

		// choose list to read from according to mode
		HashMap<String, ArrayList<String>> wordMap;
		if(spellingType==QuizMode.New){
			wordMap = mapOfWords;
		} else {
			wordMap = mapOfFailedWords; 
		}

		// if level has not been attempted, create a failed and tried list for that level since it won't exist
		if(mapOfFailedWords.get(currentLevel)==null){
			mapOfFailedWords.put(currentLevel, new ArrayList<String>());
		}
		if(mapOfTriedWords.get(currentLevel)==null){
			mapOfTriedWords.put(currentLevel, new ArrayList<String>());
		}
		// if level has not been attempted, total questions asked is 0  
		if(totalAsked.get(currentLevel)==null){
			totalAsked.put(currentLevel, 0);
			totalCorrect.put(currentLevel, 0);
		}

		// produce 10 random words from the correct list of words
		ArrayList<String> listOfWordsToChooseFrom = wordMap.get(level);
		ArrayList<String> listOfWordsToTest = new ArrayList<String>();
		HashMap<String,Integer> uniqueWordsToTest = new HashMap<String,Integer>();

		// the list size should be the size of the list if the size is less than 10
		if(listOfWordsToChooseFrom.size()<10){
			questionListSize = listOfWordsToChooseFrom.size();

		}

		// randomisation process
		while(uniqueWordsToTest.keySet().size() != questionListSize){
			int positionToChoose = (int) Math.floor(Math.random() * listOfWordsToChooseFrom.size());
			if(uniqueWordsToTest.get(listOfWordsToChooseFrom.get(positionToChoose)) == null){
				uniqueWordsToTest.put(listOfWordsToChooseFrom.get(positionToChoose),1);
				listOfWordsToTest.add(listOfWordsToChooseFrom.get(positionToChoose));
			}

		}

		// initialise lists to quiz and also change statistics
		currentQuizList = listOfWordsToTest;
		currentFailedList = mapOfFailedWords.get(currentLevel);
		currentTriedList = mapOfTriedWords.get(currentLevel);
		
		// reset list of definitions
		//definitions = new HashMap<String,String>();
		//definitionGen = new DefinitionGetter(currentQuizList,definitions);
		//definitionGen.execute();
	}

	/**
	 * QuestionAsker is a swing worker class which asks the next question on the list.
	 * The swing worker terminates when the whole list is covered
	 *
	 */
	class QuestionAsker extends SwingWorker<Void, Void>{
		protected Void doInBackground() throws Exception {
			if(getNoOfQuestions()!=0){
				// clear texts from previous question
				spellingAidQuiz.resetScreen();
				askNextQuestion();
			} 
			return null;
		}		
		protected void done(){
			// if noOfQuestions = 0, possible in review quiz mode
			if(getNoOfQuestions()==0){
				int specialKeyToDetermineMode = -1;
				if(spellType==QuizMode.New){
					specialKeyToDetermineMode = -1;
				} else if (spellType==QuizMode.Review){
					specialKeyToDetermineMode = -2;
				}
				spellingAidQuiz.quizIsDone("No questions in this quiz !",QuizMode.NoQuestions,specialKeyToDetermineMode,currentLevel);
				spellingAidQuiz.enableQuitButton();
			}
			// stop the quiz and record progress when the whole quiz list has been covered
			if(questionNo > getNoOfQuestions()){
				recordStatisticsFromLevel();
				// quiz is done, display results
				spellingAidQuiz.quizIsDone(correctAnsCount +" out of "+ getNoOfQuestions() + " Correct !",spellType,correctAnsCount,currentLevel);
				spellingAidQuiz.enableQuitButton();
			}
		}

	}

	/**
	 * Method to return the QuestionAsker swing worker object
	 * @return
	 */
	public QuestionAsker getQuestionAsker(){
		return new QuestionAsker();
	}

	/**
	 * AnswerChecker is a swing worker class which checks the user's answer against the expected answer and acts accordingly
	 *
	 */
	class AnswerChecker extends SwingWorker<Void, Void>{
		protected Void doInBackground() throws Exception {
			checkAnswer();
			return null;
		}

		protected void done(){
			// quit button is clicked <- not sure what this comment is doing here
			if (status==QuizState.Asking){
				// when a question is over and it is time to ask the next question
				spellingAidQuiz.goOnToNextQuestion();
				if(questionNo == getNoOfQuestions()){
					spellingAidQuiz.setDoneButton();
				}
			}
		}
	}

	/**
	 * Method to return the AnswerChecker swing worker object
	 * @return
	 */
	public AnswerChecker getAnswerChecker(){
		return new AnswerChecker();
	}

	/**
	 * Start asking the new question
	 */
	private void askNextQuestion(){
		// make sure user input field is cleared everytime a question is asked
		spellingAidQuiz.setUserInput("");
		// < NoOfQuestion because questionNo is used to access the current quiz list's question which starts at 0 
		// ie question 10 -> questionNo = 9
		if(questionNo < getNoOfQuestions()){

			// focus the answering area
			spellingAidQuiz.requestInputFocus();
			// attempt is true only when the question has been attempted, so it starts as false
			attempt = false;
			// endOfQuestion is true when it is time to move on to the next question
			endOfQuestion = false;

			// starts at 0
			wordToSpell = currentQuizList.get(questionNo);
			//CHEATING//
			System.out.println(wordToSpell);
			// then increment the question no to represent the real question number
			questionNo++;
			
			spellingAidQuiz.setDefinition("Loading definition ..."); // while loading definitions
			spellingAidQuiz.setSpellQuery("Please spell word " + questionNo + " of " + currentQuizList.size() + ": ");
			DefinitionGetter.getDefinitionGetter(wordToSpell,spellingAidQuiz).execute();
			spellingAidQuiz.sayText("Please spell ",wordToSpell+",");

			// after ASKING, it is time for ANSWERING
			status = QuizState.Answering;
		} else {
			questionNo++;
		}

	}


	/**
	 * This method checks if the answer is right and act accordingly
	 */
	private void checkAnswer(){

		// ensure that the answer is valid
		if (!validInput(userAnswer)){
			// warning dialog for invalid user input
			JOptionPane.showMessageDialog(spellingAidQuiz, "Oops! The answer field is blank, please enter something.", "Input Warning",JOptionPane.WARNING_MESSAGE);
			// go back to ANSWERING since current answer is invalid
			status = QuizState.Answering;
			spellingAidQuiz.requestInputFocus();
			return;
		} 

		// if it is valid, start the checking 

		// set the attempted word to show user
		if(!attempt){
			spellingAidQuiz.setFirstAttempt(": "+userAnswer);
		} else {
			spellingAidQuiz.setSecondAttempt(": "+userAnswer);
		}


		// turn to lower case for BOTH and then compare
		if(userAnswer.toLowerCase().equals(wordToSpell.toLowerCase())){
			// Correct echoed if correct
			spellingAidQuiz.sayText("Correct","");
			if(!attempt){
				Tools.record(spelling_aid_statistics,wordToSpell+" Mastered"); // store as mastered
			} else {
				Tools.record(spelling_aid_statistics,wordToSpell+" Faulted"); // store as faulted
			}
			correctAnsCount++; //question answered correctly in the end

			// increment the counter which stores the total number of correct answers in the current level
			int totalNumberOfCorrectsInLevel = totalCorrect.get(currentLevel)+1;
			totalCorrect.put(currentLevel, totalNumberOfCorrectsInLevel);

			if(currentFailedList.contains(wordToSpell)){ // remove from failed list if exists
				currentFailedList.remove(wordToSpell);
			}
			attempt = true; // question has been attempted
			endOfQuestion = true;
			// answer is correct and so proceed to ASKING the next question
			status = QuizState.Asking;
			// correct so dont have to show user the wrong answer
			spellingAidQuiz.setCorrectFlag();
			incrementCurrentStreak();
		} else {
			if(!attempt){
				spellingAidQuiz.sayText("Incorrect, try once more: "+",",wordToSpell+","+wordToSpell+",");
				spellingAidQuiz.requestInputFocus();
				// answer is wrong and a second chance is given and so back to ANSWERING
				status = QuizState.Answering;
			} else {
				spellingAidQuiz.sayText("Incorrect.",",");
				Tools.record(spelling_aid_statistics,wordToSpell+" Failed"); // store as failed
				if(!currentFailedList.contains(wordToSpell)){ //add to failed list if it doesn't exist
					currentFailedList.add(wordToSpell);
				}
				// answer is wrong on second attempt and so back to ASKING
				status = QuizState.Asking;
				endOfQuestion = true;
			}
			if(!attempt){
				spellingAidQuiz.setFirstAttemptResult(checkLetterDiff(userAnswer,wordToSpell) +" letter(s) off");
			} else {
				spellingAidQuiz.setSecondAttemptResult("X "+wordToSpell);
			}
			attempt = true; // question has been attempted

			resetCurrentStreak();
		}

		// increment the counter which stores the total number of questions asked in the current level
		if(endOfQuestion){
			int totalNumberOfQuestionsAskedInLevel = totalAsked.get(currentLevel)+ 1;
			totalAsked.put(currentLevel, totalNumberOfQuestionsAskedInLevel);

			// store as an attempted word after checking to make sure that there are no duplicates in the tried_words list
			if(!currentTriedList.contains(wordToSpell)){
				currentTriedList.add(wordToSpell);
			}
			updateCorrectQuestionsCountLabel();
		}

	}

	/**
	 * This method records everything related to the current level to the file
	 */
	public void recordStatisticsFromLevel(){
		Object[] failedKeys = mapOfFailedWords.keySet().toArray();
		Object[] triedKeys = mapOfFailedWords.keySet().toArray();
		Object[] accuracyKeys = totalAsked.keySet().toArray();

		ClearStatistics.clearFile(spelling_aid_failed);
		ClearStatistics.clearFile(spelling_aid_tried_words);
		ClearStatistics.clearFile(spelling_aid_accuracy);
		
		Arrays.sort(failedKeys);
		Arrays.sort(triedKeys);

		for(Object key : failedKeys){
			String dKey = (String)key;
			Tools.record(spelling_aid_failed,"%"+dKey);
			for(String wordToRecord : mapOfFailedWords.get(dKey)){
				Tools.record(spelling_aid_failed,wordToRecord);
			}
		}	
		for(Object key : triedKeys){
			String dKey = (String)key;
			Tools.record(spelling_aid_tried_words,"%"+dKey);
			for(String wordToRecord : mapOfTriedWords.get(dKey)){
				Tools.record(spelling_aid_tried_words,wordToRecord);
			}
		}	

		for(Object key : accuracyKeys){
			String dKey = (String)key;
			Tools.record(spelling_aid_accuracy,dKey);
			Tools.record(spelling_aid_accuracy, ""+totalAsked.get(dKey));
			Tools.record(spelling_aid_accuracy, ""+totalCorrect.get(dKey));
			Tools.record(spelling_aid_accuracy,longestLevelStreak.get(dKey)+"");
			// if level is on first attempt
			if(totalAttempts.get(dKey)==null){
				Tools.record(spelling_aid_accuracy,1+"");
			} else {
				int tA = totalAttempts.get(dKey) + 1; // increment
				Tools.record(spelling_aid_accuracy,tA+"");
			}
		}

	}


	/**
	 * function to ensure that the answer the user inputted is valid (in the format that can be accepted)
	 * @param answer
	 * @return
	 */
	private boolean validInput(String answer) {
		// blank = unacceptable
		if(answer.equals("")){
			return false;
		}
		return true;	
	}

	/**
	 * calculate the accuracy for the current level
	 * @return
	 */
	public double getLvlAccuracy(){
		double noOfQuestionsAnsweredCorrectly = totalCorrect.get(currentLevel);
		double totalQuestionsAsked = totalAsked.get(currentLevel);
		if(totalQuestionsAsked==0.0){
			return 0;
		}
		double accuracy = (noOfQuestionsAnsweredCorrectly/totalQuestionsAsked)*100.0;
		return Math.round(accuracy*10.0)/10.0;
	}

	/**
	 * Go through all the statistic files and also the file containing the word list 
	 */
	private void initialiseListsToStoreValuesFromWordAndStatsList(){
		mapOfWords = new HashMap<String, ArrayList<String>>();
		mapOfFailedWords = new HashMap<String, ArrayList<String>>();
		mapOfTriedWords = new HashMap<String, ArrayList<String>>();
		totalAsked = new HashMap<String,Integer>();
		totalCorrect = new HashMap<String,Integer>();

		try {
			// start adding file contents to the appropriate array list

			// WORDLIST
			BufferedReader readWordList = new BufferedReader(new FileReader(wordList));
			String word = readWordList.readLine();
			// array to store words in a level
			ArrayList<String> wordsInALevel = new ArrayList<String>();
			while(word != null){
				// % = level and so do appropriate things
				if(word.charAt(0) == '%'){
					String levelNo = word.substring(1);
					wordsInALevel = new ArrayList<String>();
					mapOfWords.put(levelNo,wordsInALevel);
				} else {
					// prevent duplicates in a list
					if(!wordsInALevel.contains(word)){
						wordsInALevel.add(word);
					}
				}
				word = readWordList.readLine();
			}
			readWordList.close();

			// USER's WORDLIST
			BufferedReader readUserList = new BufferedReader(new FileReader(usersList));
			String userWord = readUserList.readLine();
			// array to store words in a level
			wordsInALevel = new ArrayList<String>();
			String levelName = "";
			while(userWord != null){
				// % = level and so do appropriate things
				if(userWord.charAt(0) == '%'){
					levelName = userWord.substring(1);
					if(mapOfWords.get(levelName)==null){
						wordsInALevel = new ArrayList<String>();
						mapOfWords.put(levelName,wordsInALevel);
					} else {
						wordsInALevel = mapOfWords.get(levelName);
					}
				} else {
					// prevent duplicates in a list
					if(!wordsInALevel.contains(userWord)){
						wordsInALevel.add(userWord);
					}
				}
				userWord = readUserList.readLine();
			}
			readUserList.close();

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
					triedWordsInALevel.add(triedWord);
				}
				triedWord = readTriedList.readLine();
			}
			readTriedList.close();

			// FAILED WORDS
			BufferedReader readFailList = new BufferedReader(new FileReader(spelling_aid_failed));
			String failedWord = readFailList.readLine();
			// array to store words to review in a level
			ArrayList<String> wordsToReviewInALevel = new ArrayList<String>();
			while(failedWord != null){
				// % = level and so do appropriate things
				if(failedWord.charAt(0) == '%'){
					String levelNo = failedWord.substring(1);
					wordsToReviewInALevel = new ArrayList<String>();
					mapOfFailedWords.put(levelNo,wordsToReviewInALevel);
				} else {
					wordsToReviewInALevel.add(failedWord);
				}
				failedWord = readFailList.readLine();
			}
			readFailList.close();

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

		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * For the view controller to set the answer
	 * @param theUserAnswer user's answer from the view controller
	 */
	public void setAnswer(String theUserAnswer){
		userAnswer=theUserAnswer;
	}
	/**
	 * Check word length difference
	 * @param w1 word 1
	 * @param w2 word 2
	 * @return length difference
	 */
	private int checkLetterDiff(String w1, String w2){
		return Math.abs(w1.length()-w2.length());
	}
	/**
	 * Increment the current streak count and update the label
	 */
	private void incrementCurrentStreak(){
		currentStreak++;
		spellingAidQuiz.setCurrentStreak(": "+currentStreak);
		if(currentStreak > longestStreak){
			longestStreak = currentStreak;
			longestLevelStreak.put(currentLevel, longestStreak);
			spellingAidQuiz.setLongestStreak(": "+longestStreak);
		}
	}
	/**
	 * Resent current streak count and update the label
	 */
	private void resetCurrentStreak(){
		currentStreak = 0;
		spellingAidQuiz.setCurrentStreak(": "+currentStreak);
	}
	/**
	 * Update the label showing the correct questions count
	 */
	private void updateCorrectQuestionsCountLabel(){
		spellingAidQuiz.setNoOfCorrectSpellings(": "+correctAnsCount+"/"+questionNo);
	}
	/**
	 * Check if a String is a number
	 */
	public static boolean checkIfNumber(String s){
		try  
		{  
			@SuppressWarnings("unused")
			double d = Double.parseDouble(s);  
		}  
		catch(NumberFormatException e)  
		{  
			return false;  
		}  
		return true; 
	}
	
}