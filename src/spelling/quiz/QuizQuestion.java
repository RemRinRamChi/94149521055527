package spelling.quiz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;

import spelling.SpellingAidMain;
import spelling.SpellingList;
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
import java.awt.event.ActionEvent;

public class QuizQuestion extends JPanel {
	// QuizQuestion GUI components
	private JTextField userInput;
	private SpellingAidMain mainFrame;
	private JLabel spellQuery;
	private JButton btnConfirmOrNext;
	private JButton btnStop;
	private JLabel resultIndicator;
	private JLabel firstAttempt;
	private JLabel secondAttempt;
	private JLabel firstAttemptResult;
	private JLabel secondAttemptResult;
	private JLabel currentQuiz;
	private JLabel currentStreak;
	private JLabel longestStreak;
	private JLabel noOfCorrectSpellings;

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

	// Action object is created to be added as a listener for userInput
	// so that when the enter key is pressed, input is accepted
	Action enterAction = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			takeInUserInput();
		}
	};
	private JLabel quizAccuracy;

	// Method to call to accept user input
	private void takeInUserInput(){
		// only take in input when it is in the ANSWERING phase
		if(spellList.status == QuizState.Answering){
			//spellList.setAnswer(clearTxtBox());
			spellList.status = QuizState.Answered;
			ansChecker=spellList.getAnswerChecker();
			ansChecker.execute();
		}	

	}

	// Method to ask next question in the quiz
	public void goOnToNextQuestion(){
		// take note here coz maybe something wrong with accuracy recording in review quiz
		//if(spellList.spellType.equals("new")){
		quizAccuracy.setText(": "+spellList.getLvlAccuracy()+"%");
		//}
		if(spellList.status == QuizState.Asking){
			questionAsker=spellList.getQuestionAsker();
			questionAsker.execute();
		}
	}

	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public QuizQuestion(SpellingAidMain contentFrame){
		this();
		mainFrame = contentFrame;
	}
	/**
	 * Create the panel.
	 */
	public QuizQuestion() {
		setLayout(null);

		JLabel avatar = new JLabel("\r\n");
		avatar.setIcon(new ImageIcon("C:\\Users\\YaoJian\\Downloads\\avatar.png"));
		avatar.setBounds(28, 36, 154, 150);
		add(avatar);

		spellQuery = new JLabel("Please spell word 1 of 8\r\n");
		spellQuery.setHorizontalAlignment(SwingConstants.LEFT);
		spellQuery.setFont(new Font("Arial Narrow", Font.PLAIN, 24));
		spellQuery.setBounds(213, 29, 286, 45);
		add(spellQuery);

		JLabel lblNewLabel = new JLabel("Definition:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(213, 75, 86, 14);
		add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(213, 98, 286, 64);
		add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setBackground(Color.LIGHT_GRAY);

		userInput = new JTextField();
		userInput.setBounds(213, 173, 286, 20);
		add(userInput);
		userInput.setColumns(10);

		JButton btnListenAgain = new JButton("Listen again");
		btnListenAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// this button only works when the voice generator is not generating any voice
				if((!(spellList.status==QuizState.Asking))&&respellGen.isDone()){
					// respell word
					respellGen = new VoiceGenerator(theVoice,theVoiceStretch,theVoicePitch,theVoiceRange);
					respellGen.setTextForSwingWorker("", spellList.getCurrentWord());
					respellGen.execute();
					// rerequest focus on user input
					userInput.requestFocus();
				}
			}
		});
		btnListenAgain.setBounds(213, 204, 168, 31);
		add(btnListenAgain);

		btnConfirmOrNext = new JButton("Confirm");
		btnConfirmOrNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnConfirmOrNext.getText().equals("Confirm")){
					takeInUserInput();
				} else if (btnConfirmOrNext.getText().equals("Next Question")){
					// DO SOMETHING NEXT
				}

			}
		});
		btnConfirmOrNext.setBounds(391, 204, 108, 31);
		add(btnConfirmOrNext);

		JLabel lblYouOnlyHave = new JLabel("You only have 2 attempts");
		lblYouOnlyHave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblYouOnlyHave.setBounds(213, 256, 258, 14);
		add(lblYouOnlyHave);

		JLabel lblstAttempt = new JLabel("1st attempt");
		lblstAttempt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblstAttempt.setBounds(213, 281, 86, 20);
		add(lblstAttempt);

		JLabel lblndAttempt = new JLabel("2nd attempt");
		lblndAttempt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblndAttempt.setBounds(213, 302, 86, 20);
		add(lblndAttempt);

		firstAttempt = new JLabel(": placeholder");
		firstAttempt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		firstAttempt.setBounds(309, 281, 86, 20);
		add(firstAttempt);

		secondAttempt = new JLabel(": placeholder");
		secondAttempt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		secondAttempt.setBounds(309, 302, 86, 20);
		add(secondAttempt);

		resultIndicator = new JLabel("Correct !");
		resultIndicator.setBackground(Color.LIGHT_GRAY);
		resultIndicator.setForeground(Color.MAGENTA);
		resultIndicator.setHorizontalAlignment(SwingConstants.CENTER);
		resultIndicator.setFont(new Font("Arial Narrow", Font.PLAIN, 22));
		resultIndicator.setBounds(21, 212, 182, 23);
		add(resultIndicator);

		firstAttemptResult = new JLabel("Incorrect");
		firstAttemptResult.setFont(new Font("Tahoma", Font.BOLD, 14));
		firstAttemptResult.setBounds(405, 281, 86, 20);
		add(firstAttemptResult);

		secondAttemptResult = new JLabel("Correct\r\n");
		secondAttemptResult.setFont(new Font("Tahoma", Font.BOLD, 14));
		secondAttemptResult.setBounds(405, 302, 86, 20);
		add(secondAttemptResult);

		btnStop = new JButton("Stop\r\n");
		btnStop.setToolTipText("Only available during answring phase.");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// quiz only stoppable after a question is done asking (i.e. Answering state)
				if(spellList.status==QuizState.Answering){
					// record stats even though stopped
					spellList.recordFailedAndTriedWordsFromLevel();
					// go back to main panel
					mainFrame.changeCardPanel("Main");
				}
			}
		});
		btnStop.setBounds(58, 253, 108, 31);
		add(btnStop);

		JLabel lblCurrentQuiz = new JLabel("Current Quiz ");
		lblCurrentQuiz.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCurrentQuiz.setBounds(517, 73, 77, 14);
		add(lblCurrentQuiz);

		JLabel lblCurrentStreak = new JLabel("Current Streak");
		lblCurrentStreak.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCurrentStreak.setBounds(517, 98, 96, 14);
		add(lblCurrentStreak);

		JLabel lblLongeststreak = new JLabel("Longest Streak\r\n");
		lblLongeststreak.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLongeststreak.setBounds(517, 122, 96, 14);
		add(lblLongeststreak);

		JLabel lblSpelledCorrectly = new JLabel("Spelled Correctly");
		lblSpelledCorrectly.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSpelledCorrectly.setBounds(517, 147, 108, 14);
		add(lblSpelledCorrectly);

		JLabel lblQuizAccuracy = new JLabel("Quiz Accuracy\r\n");
		lblQuizAccuracy.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQuizAccuracy.setBounds(517, 172, 96, 14);
		add(lblQuizAccuracy);

		currentQuiz = new JLabel(": NZCER Lvl 1");
		currentQuiz.setFont(new Font("Tahoma", Font.PLAIN, 13));
		currentQuiz.setBounds(623, 73, 127, 14);
		add(currentQuiz);

		currentStreak = new JLabel(": 2");
		currentStreak.setFont(new Font("Tahoma", Font.PLAIN, 13));
		currentStreak.setBounds(623, 98, 127, 14);
		add(currentStreak);

		longestStreak = new JLabel(": 5");
		longestStreak.setFont(new Font("Tahoma", Font.PLAIN, 13));
		longestStreak.setBounds(623, 122, 127, 14);
		add(longestStreak);

		noOfCorrectSpellings = new JLabel(": 7/8");
		noOfCorrectSpellings.setFont(new Font("Tahoma", Font.PLAIN, 13));
		noOfCorrectSpellings.setBounds(623, 147, 127, 14);
		add(noOfCorrectSpellings);

		quizAccuracy = new JLabel(": 77%");
		quizAccuracy.setFont(new Font("Tahoma", Font.PLAIN, 13));
		quizAccuracy.setBounds(623, 172, 127, 14);
		add(quizAccuracy);

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
		userInput.addActionListener(enterAction);
		
		// Initialise spellList model which all questions will be asked from and answers will be checked against
		spellList = new SpellList();
	}
	
	public void startQuiz(int quizLvl){
		// CLEAR THE COMPONENTS here
		//Start asking questions for the current quiz
		spellList.createLevelList(quizLvl, QuizMode.New,this);
		quizAccuracy.setText(": "+ spellList.getLvlAccuracy()+"%");
		questionAsker = spellList.getQuestionAsker();
		questionAsker.execute();
		
	}
	
	
	public JTextField getUserInput() {
		return userInput;
	}
	public void setUserInput(JTextField userInput) {
		this.userInput = userInput;
	}
	public JLabel getSpellQuery() {
		return spellQuery;
	}
	public void setSpellQuery(JLabel spellQuery) {
		this.spellQuery = spellQuery;
	}
	public JLabel getResultIndicator() {
		return resultIndicator;
	}
	public void setResultIndicator(JLabel resultIndicator) {
		this.resultIndicator = resultIndicator;
	}
	public JLabel getFirstAttemptResult() {
		return firstAttemptResult;
	}
	public void setFirstAttemptResult(JLabel firstAttemptResult) {
		this.firstAttemptResult = firstAttemptResult;
	}
	public JLabel getSecondAttemptResult() {
		return secondAttemptResult;
	}
	public void setSecondAttemptResult(JLabel secondAttemptResult) {
		this.secondAttemptResult = secondAttemptResult;
	}
	public JLabel getCurrentStreak() {
		return currentStreak;
	}
	public void setCurrentStreak(JLabel currentStreak) {
		this.currentStreak = currentStreak;
	}
	public JLabel getLongestStreak() {
		return longestStreak;
	}
	public void setLongestStreak(JLabel longestStreak) {
		this.longestStreak = longestStreak;
	}
	public JLabel getNoOfCorrectSpellings() {
		return noOfCorrectSpellings;
	}
	public void setNoOfCorrectSpellings(JLabel noOfCorrectSpellings) {
		this.noOfCorrectSpellings = noOfCorrectSpellings;
	}
}
