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
public class QuizQuestion extends JPanel implements KeyListener{
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

	// Method to call to accept user input
	private void takeInUserInput(){
		// only take in input when it is in the ANSWERING phase
		if(spellList.status == QuizState.Answering){
			spellList.setAnswer(getAndClrInput());
			spellList.status = QuizState.Answered;
			ansChecker=spellList.getAnswerChecker();
			ansChecker.execute();
		}	

	}

	// get the text from the input text box then clears it
	private String getAndClrInput(){
		String theReturn = userInput.getText();
		userInput.setText("");
		return theReturn;
	}

	// Method to ask next question in the quiz
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
	public QuizQuestion(SpellingAidMain contentFrame, QuizDone quizDone){
		this();
		mainFrame = contentFrame;
		mainQuizDone = quizDone;
	}
	/**
	 * Create the panel.
	 */
	public QuizQuestion() {
		setLayout(null);

		JLabel avatar = new JLabel("\r\n");
		avatar.setIcon(new ImageIcon("img\\avatar.png"));
		avatar.setBounds(28, 36, 154, 150);
		add(avatar);

		spellQuery = new JLabel("Please spell word\r\n");
		spellQuery.setHorizontalAlignment(SwingConstants.LEFT);
		spellQuery.setFont(new Font("Arial", Font.PLAIN, 24));
		spellQuery.setBounds(213, 29, 380, 45);
		add(spellQuery);

		JLabel lblNewLabel = new JLabel("Definition:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel.setBounds(213, 75, 86, 14);
		add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(213, 98, 286, 64);
		add(scrollPane);

		definitionArea = new JTextArea();
		definitionArea.setFont(new Font("Arial", Font.PLAIN, 13));
		definitionArea.setEditable(false);
		scrollPane.setViewportView(definitionArea);
		definitionArea.setBackground(Color.LIGHT_GRAY);

		userInput = new JTextField();
		userInput.setFont(new Font("Arial", Font.PLAIN, 14));
		userInput.setBounds(213, 173, 286, 30);
		add(userInput);
		userInput.setColumns(10);

		btnListenAgain = new JButton("Listen again");
		btnListenAgain.setFont(new Font("Arial", Font.PLAIN, 11));
		btnListenAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		});
		btnListenAgain.setBounds(213, 214, 168, 31);
		add(btnListenAgain);

		btnConfirmOrNext = new JButton("Confirm");
		btnConfirmOrNext.setFont(new Font("Arial", Font.PLAIN, 11));
		btnConfirmOrNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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

			}
		});
		btnConfirmOrNext.setBounds(391, 214, 108, 31);
		add(btnConfirmOrNext);

		JLabel lblYouOnlyHave = new JLabel("You only have 2 attempts");
		lblYouOnlyHave.setFont(new Font("Arial", Font.PLAIN, 13));
		lblYouOnlyHave.setBounds(213, 256, 258, 20);
		add(lblYouOnlyHave);

		JLabel lblstAttempt = new JLabel("1st attempt");
		lblstAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		lblstAttempt.setBounds(213, 281, 86, 20);
		add(lblstAttempt);

		JLabel lblndAttempt = new JLabel("2nd attempt");
		lblndAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		lblndAttempt.setBounds(213, 302, 86, 20);
		add(lblndAttempt);

		firstAttempt = new JLabel(": ");
		firstAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		firstAttempt.setBounds(309, 281, 86, 20);
		add(firstAttempt);

		secondAttempt = new JLabel(": ");
		secondAttempt.setFont(new Font("Arial", Font.PLAIN, 14));
		secondAttempt.setBounds(309, 302, 86, 20);
		add(secondAttempt);

		resultIndicator = new JLabel("");
		resultIndicator.setBackground(Color.LIGHT_GRAY);
		resultIndicator.setForeground(Color.MAGENTA);
		resultIndicator.setHorizontalAlignment(SwingConstants.CENTER);
		resultIndicator.setFont(new Font("Arial", Font.PLAIN, 22));
		resultIndicator.setBounds(21, 212, 182, 23);
		add(resultIndicator);

		firstAttemptResult = new JLabel("");
		firstAttemptResult.setFont(new Font("Arial", Font.BOLD, 14));
		firstAttemptResult.setBounds(413, 281, 255, 20);
		add(firstAttemptResult);

		secondAttemptResult = new JLabel("");
		secondAttemptResult.setFont(new Font("Arial", Font.BOLD, 14));
		secondAttemptResult.setBounds(413, 302, 255, 20);
		add(secondAttemptResult);

		btnStop = new JButton("Stop\r\n Quiz");
		btnStop.setFont(new Font("Arial", Font.PLAIN, 11));
		btnStop.setToolTipText("Only available during answring phase.");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// quiz only stoppable after a question is done asking (i.e. Answering state) or when is question is done answered
				if(spellList.status==QuizState.Answering||btnConfirmOrNext.getText().equals("Next Question")){
					// record stats even though stopped
					spellList.recordFailedAndTriedWordsFromLevel();
					// go back to main panel
					mainFrame.changeCardPanel("Main");
				}
			}
		});
		btnStop.setBounds(554, 214, 114, 31);
		add(btnStop);

		JLabel lblCurrentQuiz = new JLabel("Current Quiz ");
		lblCurrentQuiz.setFont(new Font("Arial", Font.PLAIN, 13));
		lblCurrentQuiz.setBounds(517, 73, 77, 14);
		add(lblCurrentQuiz);

		JLabel lblCurrentStreak = new JLabel("Current Streak");
		lblCurrentStreak.setFont(new Font("Arial", Font.PLAIN, 13));
		lblCurrentStreak.setBounds(517, 98, 96, 14);
		add(lblCurrentStreak);

		JLabel lblLongeststreak = new JLabel("Longest Streak\r\n");
		lblLongeststreak.setFont(new Font("Arial", Font.PLAIN, 13));
		lblLongeststreak.setBounds(517, 122, 96, 14);
		add(lblLongeststreak);

		JLabel lblSpelledCorrectly = new JLabel("Spelled Correctly");
		lblSpelledCorrectly.setFont(new Font("Arial", Font.PLAIN, 13));
		lblSpelledCorrectly.setBounds(517, 147, 108, 14);
		add(lblSpelledCorrectly);

		JLabel lblQuizAccuracy = new JLabel("Quiz Accuracy\r\n");
		lblQuizAccuracy.setFont(new Font("Arial", Font.PLAIN, 13));
		lblQuizAccuracy.setBounds(517, 172, 96, 14);
		add(lblQuizAccuracy);

		currentQuiz = new JLabel(": ");
		currentQuiz.setFont(new Font("Arial", Font.PLAIN, 13));
		currentQuiz.setBounds(623, 73, 127, 14);
		add(currentQuiz);

		currentStreak = new JLabel(": ");
		currentStreak.setFont(new Font("Arial", Font.PLAIN, 13));
		currentStreak.setBounds(623, 98, 127, 14);
		add(currentStreak);

		longestStreak = new JLabel(": (coming soon)");
		longestStreak.setFont(new Font("Arial", Font.PLAIN, 13));
		longestStreak.setBounds(623, 122, 127, 14);
		add(longestStreak);

		noOfCorrectSpellings = new JLabel(": ");
		noOfCorrectSpellings.setFont(new Font("Arial", Font.PLAIN, 13));
		noOfCorrectSpellings.setBounds(623, 147, 127, 14);
		add(noOfCorrectSpellings);

		quizAccuracy = new JLabel(": ");
		quizAccuracy.setFont(new Font("Arial", Font.PLAIN, 13));
		quizAccuracy.setBounds(623, 172, 127, 14);
		add(quizAccuracy);
		
		lblSpellAgain = new JLabel("Try one more");
		lblSpellAgain.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpellAgain.setForeground(Color.MAGENTA);
		lblSpellAgain.setFont(new Font("Arial", Font.PLAIN, 18));
		lblSpellAgain.setBackground(Color.LIGHT_GRAY);
		lblSpellAgain.setBounds(21, 246, 182, 23);
		add(lblSpellAgain);

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


		// enable input accpeting when enter button is pressed
		userInput.addKeyListener(this);
		this.setFocusable(true);
		this.addKeyListener(this);

		// Initialise spellList model which all questions will be asked from and answers will be checked against
		spellList = new SpellList();
	}

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


	public String getUserInput() {
		return userInput.getText();
	}
	public void setUserInput(String input) {
		userInput.setText(input);;
	}
	public void setSpellQuery(String query) {
		spellQuery.setText(query);
	}
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
	public void setNoOfCorrectSpellings(String correctFraction) {
		noOfCorrectSpellings.setText(correctFraction);
	}
	public void requestInputFocus(){
		userInput.requestFocus();
	}
	public void displaySpellAgainLabel(boolean yesOrNo){
		lblSpellAgain.setVisible(yesOrNo);
	}
	public void setDefinition(String definition){
		definitionArea.setText("Definition coming soon");
	}
	public void setCurrentQuiz(String quiz){
		currentQuiz.setText(quiz);
	}
	
	public void setDoneButton(){
		btnConfirmOrNext.setText("Done");
		btnStop.setVisible(false); // no more stop quiz since quiz is done
	}
	// reset screen at the start of every quiz
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
	
	// method to be called when quiz is done
	public void quizIsDone(String results,QuizMode mode, int corrects){
		// display results
		mainFrame.getDonePanel().setLblResults(results);
		if(mode == QuizMode.NoQuestions){
			mainQuizDone.changeResultPanel("No Results");
		} else {
			mainQuizDone.changeResultPanel("Results");
			if(corrects >= 9){
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
	
	
	// use voiceGen to say stuff
	public void sayText(String normal, String altered){
		voiceGen.sayText(normal, altered);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER ||e.getKeyCode() == KeyEvent.VK_DOWN){
			btnConfirmOrNext.doClick();
		} else if(e.getKeyCode() == KeyEvent.VK_UP){
			btnListenAgain.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

}
