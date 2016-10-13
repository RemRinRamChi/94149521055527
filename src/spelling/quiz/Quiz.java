package spelling.quiz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;

import spelling.SpellingAidMain;
import spelling.VoiceGenerator;
import spelling.quiz.SpellList.AnswerChecker;
import spelling.quiz.SpellList.QuestionAsker;
import spelling.quiz.SpellList.QuizMode;
import spelling.quiz.SpellList.QuizState;
import spelling.VoiceGenerator.Voice;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
/**
 * Thos is the GUI for the quiz questions
 * @author yyap601
 *
 */
public class Quiz extends JPanel implements KeyListener, ActionListener{
	// QuizQuestion GUI components
	private JTextField userInput;
	private SpellingAidMain mainFrame;
	private QuizDone mainQuizDone;
	private JLabel spellQuery;
	private JButton btnConfirmOrNext;
	private JButton btnStop;
	private JButton btnListenAgain;
	private JLabel resultIndicator;
	private JLabel firstAttempt;
	private JLabel secondAttempt;
	private JLabel firstAttemptResult;
	private JLabel secondAttemptResult;
	private JLabel currentQuiz;
	private JLabel currentStreak;
	private JLabel longestStreak;
	private JLabel noOfCorrectSpellings;
	private JLabel quizAccuracy;
	private JLabel lblSpellAgain;

	// spell list, question asker and answer checker to run the quiz
	private SpellList spellList;
	private QuestionAsker questionAsker;
	private AnswerChecker ansChecker;

	// voice generator for VoxSpell quiz
	private VoiceGenerator voiceGen;
	private VoiceGenerator respellGen;
	private Voice theVoice = Voice.DEFAULT;
	private double theVoiceStretch;
	private double theVoicePitch;
	private double theVoiceRange;
	private JTextArea definitionArea;

	// perform appropriate actions based on button press
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnConfirmOrNext){
			if(btnConfirmOrNext.getText().equals("Confirm")){
				takeInUserInput();
			} else if (btnConfirmOrNext.getText().equals("Next Question")||btnConfirmOrNext.getText().equals("Done")){
				// ask question when it is supposed to
				if(spellList.status == QuizState.Asking){
					btnConfirmOrNext.setText("Confirm");
					questionAsker=spellList.getQuestionAsker();
					questionAsker.execute();
				}
			}
		} else if(e.getSource()==btnStop){
			// quiz only stoppable after a question is done asking (i.e. Answering state) or when is question is done answered
			if(spellList.status==QuizState.Answering||btnConfirmOrNext.getText().equals("Next Question")){
				// record stats even though stopped
				spellList.recordFailedAndTriedWordsFromLevel();
				// go back to main panel
				mainFrame.changeCardPanel("Main");
			}
		} else if(e.getSource()==btnListenAgain){
			// this button only works when the voice generator is not generating any voice and when a question is not being asked
			if((!(spellList.status==QuizState.Asking)||(btnConfirmOrNext.getText().equals("Next Question")))&&respellGen.isDone()){
				// respell word
				respellGen = new VoiceGenerator(theVoice,theVoiceStretch,theVoicePitch,theVoiceRange);
				respellGen.setTextForSwingWorker("", spellList.getCurrentWord());
				respellGen.execute();
				// rerequest focus on user input
				userInput.requestFocus();
			}
		}
	}

	/**
	 * Method to call to accept user input
	 */
	private void takeInUserInput(){
		// only take in input when it is in the ANSWERING phase
		if(spellList.status == QuizState.Answering){
			spellList.setAnswer(getAndClrInput());
			spellList.status = QuizState.Answered;
			ansChecker=spellList.getAnswerChecker();
			ansChecker.execute();
		}	

	}

	/**
	 * Get the text from the input text box then clears it
	 * @return user's answer
	 */
	private String getAndClrInput(){
		String theReturn = userInput.getText();
		userInput.setText("");
		return theReturn;
	}

	/**
	 *  Method to ask next question in the quiz
	 */
	public void goOnToNextQuestion(){
		btnConfirmOrNext.setText("Next Question");
		// take note here coz maybe something wrong with accuracy recording in review quiz
		//if(spellList.spellType.equals("new")){
		quizAccuracy.setText(": "+spellList.getLvlAccuracy()+"%");
		//}

	}

	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public Quiz(SpellingAidMain contentFrame, QuizDone quizDone){
		this();
		mainFrame = contentFrame;
		mainQuizDone = quizDone;
	}
	/**
	 * Create the QuizQuestion panel and layout all its components.
	 */
	public Quiz() {
		createAndLayoutComponents();

		// stretch spelling on words to let 2nd language learners have more time to process an unfamiliar language
		theVoiceStretch = 1.2;
		theVoicePitch = 95;
		theVoiceRange = 15;

		// initialise voice generator for VoxSpell
		voiceGen = new VoiceGenerator(theVoice,theVoiceStretch,theVoicePitch,theVoiceRange);

		// initialise voice generator for the respell button
		respellGen = new VoiceGenerator(theVoice,theVoiceStretch,theVoicePitch,theVoiceRange);
		// immediately cancel it to allow the respell button to work on the first try to only work when 
		// none of the voice generators are in action
		respellGen.cancel(true); 


		// enable input accepting when enter button is pressed
		userInput.addKeyListener(this);
		this.setFocusable(true);
		this.addKeyListener(this);

		// Initialise spellList model which all questions will be asked from and answers will be checked against
		spellList = new SpellList();
	}

	/**
	 * Start a spelling quiz
	 * @param quizLvl quiz level selected by user
	 * @param mode normal or review mode
	 */
	public void startQuiz(String quizLvl,QuizMode mode){
		// clear texts from previous session if exists
		resetScreen();
		setCurrentStreak(":");
		setNoOfCorrectSpellings(":");
		//Start asking questions for the current quiz // NEED TO CHANGE THIS TO ACCEPT STRING
		spellList.createLevelList(Integer.parseInt(quizLvl), mode,this);
		quizAccuracy.setText(": "+ spellList.getLvlAccuracy()+"%");
		questionAsker = spellList.getQuestionAsker();
		questionAsker.execute();

	}

	/**
	 * Get user's input
	 * @return user's input
	 */
	public String getUserInput() {
		return userInput.getText();
	}
	/**
	 * Set user's input
	 */
	public void setUserInput(String input) {
		userInput.setText(input);;
	}
	/**
	 * Set "Please spell ..." query
	 * @param query label to display as query
	 */
	public void setSpellQuery(String query) {
		spellQuery.setText(query);
	}
	/**
	 * Set the text in the result indicator {Correct,Incorrect}
	 * @param result whether the user is right or wrong
	 */
	public void setResultIndicator(String result) {
		resultIndicator.setText(result);
	}
	public String getFirstAttempt() {
		return firstAttempt.getText();
	}
	public void setFirstAttempt(String attempt) {
		firstAttempt.setText(attempt);
	}
	public String getSecondAttempt() {
		return secondAttempt.getText();
	}	
	public void setSecondAttempt(String attempt) {
		secondAttempt.setText(attempt);
	}
	public String getFirstAttemptResult() {
		return firstAttemptResult.getText();
	}
	public void setFirstAttemptResult(String result) {
		firstAttemptResult.setText(result);
	}
	public String getSecondAttemptResult() {
		return secondAttemptResult.getText();
	}
	public void setSecondAttemptResult(String result) {
		secondAttemptResult.setText(result);
	}
	public String getCurrentStreak() {
		return currentStreak.getText();
	}
	public void setCurrentStreak(String streak) {
		currentStreak.setText(streak);
	}
	public String getLongestStreak() {
		return longestStreak.getText();
	}
	public void setLongestStreak(String streak) {
		longestStreak.setText(streak);
	}
	public int getNoOfCorrectSpellings() {
		return Integer.parseInt(noOfCorrectSpellings.getText());
	}
	/**
	 * @param correctFraction corrects/total asked
	 */
	public void setNoOfCorrectSpellings(String correctFraction) {
		noOfCorrectSpellings.setText(correctFraction);
	}
	/**
	 * Request focus on QuizQuestion user input field
	 */
	public void requestInputFocus(){
		userInput.requestFocus();
	}
	/**
	 * Whether to display the "Incorrect spell again"
	 * @param yesOrNo
	 */
	public void displaySpellAgainLabel(boolean yesOrNo){
		lblSpellAgain.setVisible(yesOrNo);
	}
	/**
	 * Set definition for a quiz question
	 * @param definition
	 */
	public void setDefinition(String definition){
		definitionArea.setText("Definition coming soon");
	}
	public void setCurrentQuiz(String quiz){
		currentQuiz.setText(quiz);
	}
	/**
	 * Change the text on the Confirm button to Done when the user is done with the quiz
	 */
	public void setDoneButton(){
		btnConfirmOrNext.setText("Done");
		btnStop.setVisible(false); // no more stop quiz since quiz is done
	}
	/**
	 * Enable the stop quiz button
	 */
	public void enableQuitButton(){
		btnStop.setVisible(true);
	}
	/**
	 * Reset screen at the start of every quiz to clear up anything from previous sessions
	 */
	public void resetScreen(){
		setResultIndicator("");
		displaySpellAgainLabel(false);
		setFirstAttempt("");
		setSecondAttempt(":");
		setFirstAttempt(":");
		setFirstAttemptResult("");
		setSecondAttemptResult("");
		setDefinition("");
		btnConfirmOrNext.setText("Confirm");
	}

	/**
	 * Method to be called when quiz is done
	 * @param results results to be displayed
	 * @param mode the quiz mode
	 * @param corrects number of correct quesstions answered
	 */
	public void quizIsDone(String results,QuizMode mode, int corrects){
		// display results
		mainFrame.getDonePanel().setLblResults(results);
		if(mode == QuizMode.NoQuestions){
			mainQuizDone.changeResultPanel("No Results");
		} else {
			mainQuizDone.changeResultPanel("Results");
			if(corrects >= 9){ // have to change
				if(mode == QuizMode.New){
					mainQuizDone.changeUserInteraction("Rewards");
				} else if(mode == QuizMode.Review){
					mainQuizDone.changeUserInteraction("Good Job");
				}
			} else {
				mainQuizDone.changeUserInteraction("Good Try");
			}

		}
		if(mode == QuizMode.New){
			mainQuizDone.changeNextLevelPanel("Try");
		} else if(mode == QuizMode.Review){
			mainQuizDone.changeNextLevelPanel("Review");
		} else if(mode == QuizMode.NoQuestions){
			if(corrects == -1){
				mainQuizDone.changeNextLevelPanel("Try");
				mainQuizDone.changeUserInteraction("No Words");
			} else if (corrects == -2){
				mainQuizDone.changeNextLevelPanel("Review");
				mainQuizDone.changeUserInteraction("No Review");
			}
		}


		// switch panel in card layout
		mainFrame.changeCardPanel("Done");
	}


	/**
	 * Use voiceGen to say texts
	 * @param normal text said at normal speed
	 * @param altered text said at altered speed
	 */
	public void sayText(String normal, String altered){
		voiceGen.sayText(normal, altered);
	}

	// track keys to proceed in a quiz
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER ||e.getKeyCode() == KeyEvent.VK_DOWN){
			btnConfirmOrNext.doClick();
		} else if(e.getKeyCode() == KeyEvent.VK_UP){
			btnListenAgain.doClick();
		}
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
	
	/**
	 * Set festival's voice
	 * @param voice voiceGen's voice
	 */
	public void setFestivalVoice(String voice) {
		if(voice.equals("Default")){
			theVoice = Voice.DEFAULT;
		} else if(voice.equals("Auckland")){ 
			theVoice = Voice.AUCKLAND;
		}
		voiceGen.setVoice(theVoice);
	}

	/**
	 *  Create all components and lay them out properly
	 */
	private void createAndLayoutComponents(){
		setLayout(null); //absolute layout

		// avatar
		JLabel avatar = new JLabel("\r\n");
		avatar.setIcon(new ImageIcon("img/avatar.png"));
		avatar.setBounds(28, 36, 154, 150);
		add(avatar);

		// "Please spell word ? of ?" label
		spellQuery = new JLabel("Please spell word\r\n");
		spellQuery.setHorizontalAlignment(SwingConstants.LEFT);
		spellQuery.setFont(new Font("Arial", Font.PLAIN, 24));
		spellQuery.setBounds(213, 29, 380, 45);
		add(spellQuery);

		// "Definition:" label
		JLabel lblNewLabel = new JLabel("Definition:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel.setBounds(213, 75, 86, 14);
		add(lblNewLabel);

		// text area displaying the definition of the word
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(213, 98, 286, 64);
		add(scrollPane);
		definitionArea = new JTextArea();
		definitionArea.setFont(new Font("Arial", Font.PLAIN, 13));
		definitionArea.setEditable(false);
		scrollPane.setViewportView(definitionArea);
		definitionArea.setBackground(Color.LIGHT_GRAY);

		// user input field
		userInput = new JTextField();
		userInput.setFont(new Font("Arial", Font.PLAIN, 14));
		userInput.setBounds(213, 173, 286, 30);
		add(userInput);
		userInput.setColumns(10);

		// listen again button
		btnListenAgain = new JButton("Listen again");
		btnListenAgain.setFont(new Font("Arial", Font.PLAIN, 11));
		btnListenAgain.addActionListener(this);
		btnListenAgain.setBounds(213, 214, 168, 31);
		add(btnListenAgain);

		// confirm button or next question button
		btnConfirmOrNext = new JButton("Confirm");
		btnConfirmOrNext.setFont(new Font("Arial", Font.PLAIN, 11));
		btnConfirmOrNext.addActionListener(this);
		btnConfirmOrNext.setBounds(391, 214, 108, 31);
		add(btnConfirmOrNext);

		// "You only have 2 attempts" label
		JLabel lblYouOnlyHave = new JLabel("You only have 2 attempts");
		lblYouOnlyHave.setFont(new Font("Arial", Font.PLAIN, 13));
		lblYouOnlyHave.setBounds(213, 256, 258, 20);
		add(lblYouOnlyHave);

		// "1st attempt" label
		JLabel lblstAttempt = new JLabel("1st attempt");
		lblstAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		lblstAttempt.setBounds(213, 281, 86, 20);
		add(lblstAttempt);

		// "2nd attempt" label
		JLabel lblndAttempt = new JLabel("2nd attempt");
		lblndAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		lblndAttempt.setBounds(213, 302, 86, 20);
		add(lblndAttempt);

		// shows the user's first attempt
		firstAttempt = new JLabel(": ");
		firstAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		firstAttempt.setBounds(309, 281, 86, 20);
		add(firstAttempt);

		// shows the user's second attempt
		secondAttempt = new JLabel(": ");
		secondAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		secondAttempt.setBounds(309, 302, 86, 20);
		add(secondAttempt);

		// result indicator indicating what the result of the user's attempt is
		resultIndicator = new JLabel("");
		resultIndicator.setBackground(Color.LIGHT_GRAY);
		resultIndicator.setForeground(Color.MAGENTA);
		resultIndicator.setHorizontalAlignment(SwingConstants.CENTER);
		resultIndicator.setFont(new Font("Arial", Font.PLAIN, 22));
		resultIndicator.setBounds(21, 212, 182, 23);
		add(resultIndicator);
		// "Try once more" label
		lblSpellAgain = new JLabel("Try one more");
		lblSpellAgain.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpellAgain.setForeground(Color.MAGENTA);
		lblSpellAgain.setFont(new Font("Arial", Font.PLAIN, 18));
		lblSpellAgain.setBackground(Color.LIGHT_GRAY);
		lblSpellAgain.setBounds(21, 246, 182, 23);
		add(lblSpellAgain);
		
		// shows how many letters off is the user's answer
		firstAttemptResult = new JLabel("");
		firstAttemptResult.setFont(new Font("Arial", Font.BOLD, 14));
		firstAttemptResult.setBounds(413, 281, 255, 20);
		add(firstAttemptResult);
		secondAttemptResult = new JLabel("");
		secondAttemptResult.setFont(new Font("Arial", Font.BOLD, 14));
		secondAttemptResult.setBounds(413, 302, 255, 20);
		add(secondAttemptResult);

		// stop quiz button
		btnStop = new JButton("Stop\r\n Quiz");
		btnStop.setFont(new Font("Arial", Font.PLAIN, 11));
		btnStop.setToolTipText("Only available during answring phase.");
		btnStop.addActionListener(this);
		btnStop.setBounds(554, 214, 114, 31);
		add(btnStop);

		// show current quiz
		JLabel lblCurrentQuiz = new JLabel("Current Quiz ");
		lblCurrentQuiz.setFont(new Font("Arial", Font.PLAIN, 13));
		lblCurrentQuiz.setBounds(517, 73, 77, 14);
		add(lblCurrentQuiz);
		currentQuiz = new JLabel(": ");
		currentQuiz.setFont(new Font("Arial", Font.PLAIN, 13));
		currentQuiz.setBounds(623, 73, 127, 14);
		add(currentQuiz);
		
		// show current streak
		JLabel lblCurrentStreak = new JLabel("Current Streak");
		lblCurrentStreak.setFont(new Font("Arial", Font.PLAIN, 13));
		lblCurrentStreak.setBounds(517, 98, 96, 14);
		add(lblCurrentStreak);
		currentStreak = new JLabel(": ");
		currentStreak.setFont(new Font("Arial", Font.PLAIN, 13));
		currentStreak.setBounds(623, 98, 127, 14);
		add(currentStreak);
		
		// show longest streak
		JLabel lblLongeststreak = new JLabel("Longest Streak\r\n");
		lblLongeststreak.setFont(new Font("Arial", Font.PLAIN, 13));
		lblLongeststreak.setBounds(517, 122, 96, 14);
		add(lblLongeststreak);
		longestStreak = new JLabel(": (coming soon)");
		longestStreak.setFont(new Font("Arial", Font.PLAIN, 13));
		longestStreak.setBounds(623, 122, 127, 14);
		add(longestStreak);
		
		// show words spelled correctly
		JLabel lblSpelledCorrectly = new JLabel("Spelled Correctly");
		lblSpelledCorrectly.setFont(new Font("Arial", Font.PLAIN, 13));
		lblSpelledCorrectly.setBounds(517, 147, 108, 14);
		add(lblSpelledCorrectly);
		noOfCorrectSpellings = new JLabel(": ");
		noOfCorrectSpellings.setFont(new Font("Arial", Font.PLAIN, 13));
		noOfCorrectSpellings.setBounds(623, 147, 127, 14);
		add(noOfCorrectSpellings);
		
		// show quiz accuracy
		JLabel lblQuizAccuracy = new JLabel("Quiz Accuracy\r\n");
		lblQuizAccuracy.setFont(new Font("Arial", Font.PLAIN, 13));
		lblQuizAccuracy.setBounds(517, 172, 96, 14);
		add(lblQuizAccuracy);
		quizAccuracy = new JLabel(": ");
		quizAccuracy.setFont(new Font("Arial", Font.PLAIN, 13));
		quizAccuracy.setBounds(623, 172, 127, 14);
		add(quizAccuracy);

	}

}
