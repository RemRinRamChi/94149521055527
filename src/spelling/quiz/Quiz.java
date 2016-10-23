package spelling.quiz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

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
	private JLabel firstAttempt;
	private JLabel secondAttempt;
	private JLabel firstAttemptResult;
	private JLabel secondAttemptResult;
	private JLabel currentQuiz;
	private JLabel currentStreak;
	private JLabel longestStreak;
	private JLabel noOfCorrectSpellings;
	private JLabel quizAccuracy;
	private JLabel lblNewLabel;
	private JLabel lblYouOnlyHave;
	private JLabel lblCurrentQuiz;
	private JLabel lblCurrentStreak;
	private JLabel lblLongeststreak;
	private JLabel lblSpelledCorrectly;
	private JLabel lblQuizAccuracy;
	private JLabel lblstAttempt;
	private JLabel lblndAttempt;

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
	
	// flag to make quiz faster by going on to the next question immediately
	private boolean correctFlag = false;
	
	/**
	 * Apply colour to components
	 */
	public void applyTheme(){
		Color backgroundColour = new Color(255,255,255);
		Color buttonText = new Color(255,255,255);
		Color normalText = new Color(0,0,0);
		Color buttonColour = new Color(15,169,249);

		firstAttemptResult.setForeground(new Color(255,0,110));
		secondAttemptResult.setForeground(new Color(255,0,0));
		
		// background color
		this.setBackground(backgroundColour);
		
		// back text
		spellQuery.setForeground(normalText);
		definitionArea.setForeground(normalText);
		lblstAttempt.setForeground(normalText);
		lblndAttempt.setForeground(normalText);
		firstAttempt.setForeground(normalText);
		secondAttempt.setForeground(normalText);
		currentQuiz.setForeground(normalText);
		currentStreak.setForeground(normalText);
		longestStreak.setForeground(normalText);
		noOfCorrectSpellings.setForeground(normalText);
		quizAccuracy.setForeground(normalText);
		lblNewLabel.setForeground(normalText);
		lblYouOnlyHave.setForeground(normalText);
		lblCurrentQuiz.setForeground(normalText);
		lblCurrentStreak.setForeground(normalText);
		lblLongeststreak.setForeground(normalText);
		lblSpelledCorrectly.setForeground(normalText);
		lblQuizAccuracy.setForeground(normalText);
		
		// normal text
		btnConfirmOrNext.setForeground(buttonText);
		btnStop.setForeground(buttonText);
		btnListenAgain.setForeground(buttonText);
		// normal button color
		btnConfirmOrNext.setBackground(buttonColour);
		btnStop.setBackground(buttonColour);
		btnListenAgain.setBackground(buttonColour);
	}
	
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
				spellList.recordStatisticsFromLevel();
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
	 * Updates the spell list when the user changes the own list
	 */
	public void updateSpellList(SpellList sList){
		spellList=sList;
		requestInputFocus();
	}

	/**
	 * Method to call to accept user input
	 */
	private void takeInUserInput(){
		// only take in input when it is in the ANSWERING phase
		if(spellList.status == QuizState.Answering){
			spellList.setAnswer(getAndClrInput());
			spellList.status = QuizState.Answered;
			ansChecker=spellList.getAnswerChecker()
					;
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
		quizAccuracy.setText(": "+spellList.getLvlAccuracy()+"%");
		// if user got answer correct move on immediately, else let user look at the correct answer first
		if(correctFlag){
			// set it to false initially for the next question
			correctFlag = false;
			btnConfirmOrNext.doClick();
		}

	}
	
	/**
	 *  Set flag to be correct
	 */
	public void setCorrectFlag(){
		correctFlag = true;
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

		applyTheme();
		
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
		spellList.createLevelList(quizLvl, mode,this);
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
	 * Set definition for a quiz question
	 * @param definition
	 */
	public void setDefinition(String definition){
		definitionArea.setText(definition);
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
	public void quizIsDone(String results,QuizMode mode, int corrects, String quizLevel){
		// set the level that can be displayed
		mainQuizDone.setLevel(quizLevel);
		// display results
		mainFrame.getDonePanel().setLblResults(results);
		if(mode == QuizMode.NoQuestions){
			mainQuizDone.changeResultPanel("No Results");
			
		} else {
			mainQuizDone.changeResultPanel("Results");
			if(corrects >= spellList.getNoOfQuestions()-1 && corrects != 0){ // if 0 then not counted
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

	public void scrollDefinitionAreaToTop(){
		definitionArea.setCaretPosition(0); 
	}
	
	
	/**
	 *  Create all components and lay them out properly
	 */
	private void createAndLayoutComponents(){
		setLayout(null); //absolute layout

		// avatar
		JLabel avatar = new JLabel("\r\n");
		avatar.setIcon(new ImageIcon("img/LongAvatar.png"));
		avatar.setBounds(28, 36, 154, 286);
		add(avatar);

		// "Please spell word ? of ?" label
		spellQuery = new JLabel("Please spell word\r\n");
		spellQuery.setHorizontalAlignment(SwingConstants.LEFT);
		spellQuery.setFont(new Font("Arial", Font.PLAIN, 24));
		spellQuery.setBounds(213, 29, 380, 45);
		add(spellQuery);

		// "Definition:" label
		lblNewLabel = new JLabel("Definition:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel.setBounds(213, 75, 86, 14);
		add(lblNewLabel);

		// text area displaying the definition of the word
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(213, 98, 286, 64);
		add(scrollPane);
		definitionArea = new JTextArea();
		definitionArea.setForeground(new Color(0, 0, 0));
		definitionArea.setWrapStyleWord(true);
		definitionArea.setLineWrap(true);
		definitionArea.setFont(new Font("Arial", Font.PLAIN, 14));
		definitionArea.setEditable(false);
		scrollPane.setViewportView(definitionArea);
		definitionArea.setBackground(new Color(250,250,250));

		// user input field
		userInput = new JTextField();
		userInput.setFont(new Font("Arial", Font.PLAIN, 14));
		userInput.setBounds(213, 173, 286, 30);
		add(userInput);
		userInput.setColumns(10);

		// listen again button
		btnListenAgain = new JButton("Listen again");
		btnListenAgain.setFont(new Font("Arial", Font.BOLD, 14));
		btnListenAgain.addActionListener(this);
		btnListenAgain.addKeyListener(this);
		btnListenAgain.setBounds(213, 214, 144, 31);
		add(btnListenAgain);

		// confirm button or next question button
		btnConfirmOrNext = new JButton("Confirm");
		btnConfirmOrNext.setFont(new Font("Arial", Font.BOLD, 14));
		btnConfirmOrNext.addActionListener(this);
		btnConfirmOrNext.addKeyListener(this);
		btnConfirmOrNext.setBounds(365, 214, 134, 31);
		add(btnConfirmOrNext);

		// "You only have 2 attempts" label
		lblYouOnlyHave = new JLabel("You have only 2 attempts");
		lblYouOnlyHave.setFont(new Font("Arial", Font.PLAIN, 14));
		lblYouOnlyHave.setBounds(213, 256, 258, 20);
		add(lblYouOnlyHave);

		// "1st attempt" label
		lblstAttempt = new JLabel("1st attempt");
		lblstAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		lblstAttempt.setBounds(213, 281, 86, 20);
		add(lblstAttempt);

		// "2nd attempt" label
		lblndAttempt = new JLabel("2nd attempt");
		lblndAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		lblndAttempt.setBounds(213, 302, 86, 20);
		add(lblndAttempt);

		// shows the user's first attempt
		firstAttempt = new JLabel(": ");
		firstAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		firstAttempt.setBounds(309, 281, 134, 20);
		add(firstAttempt);

		// shows the user's second attempt
		secondAttempt = new JLabel(": ");
		secondAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		secondAttempt.setBounds(309, 302, 134, 20);
		add(secondAttempt);
		
		// shows how many letters off is the user's answer
		firstAttemptResult = new JLabel("");
		firstAttemptResult.setFont(new Font("Arial", Font.BOLD, 14));
		firstAttemptResult.setBounds(453, 281, 215, 20);
		add(firstAttemptResult);
		secondAttemptResult = new JLabel("");
		secondAttemptResult.setFont(new Font("Arial", Font.BOLD, 14));
		secondAttemptResult.setBounds(453, 302, 215, 20);
		add(secondAttemptResult);

		// stop quiz button
		btnStop = new JButton("Stop\r\n Quiz");
		btnStop.setFont(new Font("Arial", Font.BOLD, 14));
		btnStop.setToolTipText("Only available during answring phase.");
		btnStop.addActionListener(this);
		btnStop.addKeyListener(this);
		btnStop.setBounds(554, 214, 114, 31);
		add(btnStop);

		// show current quiz
		lblCurrentQuiz = new JLabel("Current Quiz ");
		lblCurrentQuiz.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCurrentQuiz.setBounds(517, 73, 108, 14);
		add(lblCurrentQuiz);
		currentQuiz = new JLabel(": ");
		currentQuiz.setFont(new Font("Arial", Font.PLAIN, 14));
		currentQuiz.setBounds(647, 75, 127, 14);
		add(currentQuiz);
		
		// show current streak
		lblCurrentStreak = new JLabel("Current Streak");
		lblCurrentStreak.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCurrentStreak.setBounds(517, 98, 118, 14);
		add(lblCurrentStreak);
		currentStreak = new JLabel(": ");
		currentStreak.setFont(new Font("Arial", Font.PLAIN, 14));
		currentStreak.setBounds(647, 100, 127, 14);
		add(currentStreak);
		
		// show longest streak
		lblLongeststreak = new JLabel("Longest Streak\r\n");
		lblLongeststreak.setVerticalAlignment(SwingConstants.TOP);
		lblLongeststreak.setFont(new Font("Arial", Font.PLAIN, 14));
		lblLongeststreak.setBounds(517, 122, 96, 20);
		add(lblLongeststreak);
		longestStreak = new JLabel(": ");
		longestStreak.setFont(new Font("Arial", Font.PLAIN, 14));
		longestStreak.setBounds(647, 124, 127, 14);
		add(longestStreak);
		
		// show words spelled correctly
		lblSpelledCorrectly = new JLabel("Spelled Correctly");
		lblSpelledCorrectly.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSpelledCorrectly.setBounds(517, 147, 118, 14);
		add(lblSpelledCorrectly);
		noOfCorrectSpellings = new JLabel(": ");
		noOfCorrectSpellings.setFont(new Font("Arial", Font.PLAIN, 14));
		noOfCorrectSpellings.setBounds(647, 149, 127, 14);
		add(noOfCorrectSpellings);
		
		// show quiz accuracy
		lblQuizAccuracy = new JLabel("Quiz Accuracy\r\n");
		lblQuizAccuracy.setVerticalAlignment(SwingConstants.TOP);
		lblQuizAccuracy.setFont(new Font("Arial", Font.PLAIN, 14));
		lblQuizAccuracy.setBounds(517, 172, 108, 20);
		add(lblQuizAccuracy);
		quizAccuracy = new JLabel(": ");
		quizAccuracy.setFont(new Font("Arial", Font.PLAIN, 14));
		quizAccuracy.setBounds(647, 174, 127, 14);
		add(quizAccuracy);

	}
}
