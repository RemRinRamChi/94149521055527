package spelling.quiz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import spelling.AudioPlayer;
import spelling.AudioPlayer.AudioReward;
import spelling.SpellingAidMain;
import spelling.VideoPlayer;
import spelling.quiz.SpellList.QuizMode;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
/**
 * This is the GUI that's displayed when the user is done with aquiz
 * @author yyap601
 *
 */
public class QuizDone extends JPanel implements ActionListener{
	private SpellingAidMain mainFrame;
	private JLabel lblResults;
	private JLabel noResults;
	private JPanel userInteractionPanel;
	private JPanel resultsPanel;
	private JPanel tryAnotherPanel;
	private JButton btnVideo;
	private JButton btnTryAnotherLevel;
	private JButton btnDone;
	private JButton btnReviewAnotherLevel;
	private JButton btnRetryLevel;
	private String level; 

	/**
	 * Set results label
	 * @param results
	 */
	public void setLblResults(String results) {
		lblResults.setText(results);
	}
	/**
	 * Set level
	 * @param lvl
	 */
	public void setLevel(String lvl){
		level = lvl;
	}
	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public QuizDone(SpellingAidMain contentFrame){
		this();
		mainFrame = contentFrame;
	}
	/**
	 * Create the panel.
	 */
	public QuizDone() {
		createAndLayoutComponents();
	}

	// perform appropriate actions based on button press
	public void actionPerformed(ActionEvent e) { // Audio Reward
		if(e.getSource()==btnVideo){ // Video Reward
			// educational youtube video
			VideoPlayer vPlayer = new VideoPlayer();
			vPlayer.play(); 
			// video reward is only received once
			btnVideo.setEnabled(false);
			btnVideo.setToolTipText("Video reward is only received once, please try another quiz to receive another.");
		} else if(e.getSource()==btnTryAnotherLevel){  // Try another level
			QuizChooser quizChooser = new QuizChooser(mainFrame,mainFrame.getQuiz(),QuizMode.New); // another new quiz
			quizChooser.setVisible(true);
		} else if(e.getSource()==btnReviewAnotherLevel){  // Review another level
			QuizChooser quizChooser = new QuizChooser(mainFrame,mainFrame.getQuiz(),QuizMode.Review); // another review quiz
			quizChooser.setVisible(true);
		} else if(e.getSource()==btnDone){ // DONE
			mainFrame.changeCardPanel("Main"); // go back to main options
		} else if(e.getSource()==btnRetryLevel){  // Review another level
			mainFrame.changeCardPanel("Quiz");
			mainFrame.getQuiz().startQuiz(level,QuizMode.New);
		}
	}

	/**
	 * Change results to display according to mode 
	 * @param mode "Results", "No Results"
	 */
	public void changeResultPanel(String mode){
		if(mode.equals("No Results")){
			noResults.setText("No words in "+level);
		}
		((CardLayout) resultsPanel.getLayout()).show(resultsPanel, mode);
	}

	/**
	 * Change user interaction to display according to mode 
	 * @param mode "No Words", "Good Try", "Good Job", "No Review"
	 */
	public void changeUserInteraction(String mode){
		if(mode.equals("Rewards")){
			AudioPlayer.getAudioPlayer(AudioReward.AllCorrect).execute();
			// reenable everytime
			btnVideo.setEnabled(true);
			btnVideo.setToolTipText("");
		} else if(mode.equals("Good Try")){
			AudioPlayer.getAudioPlayer(AudioReward.NotAllCorrect).execute();
		}
		((CardLayout) userInteractionPanel.getLayout()).show(userInteractionPanel, mode);
	}

	/**
	 * Change next level option to display according to mode 
	 * @param mode
	 */
	public void changeNextLevelPanel(String mode){
		((CardLayout) tryAnotherPanel.getLayout()).show(tryAnotherPanel, mode);
	}
	/**
	 *  Create all components and lay them out properly
	 */
	private void createAndLayoutComponents(){
		setLayout(null); //absolute layout
		
		// retry level
		btnRetryLevel = new JButton("RETRY LEVEL");
		btnRetryLevel.addActionListener(this);
		btnRetryLevel.setFont(new Font("Arial", Font.PLAIN, 13));
		btnRetryLevel.setBounds(93, 293, 421, 30);
		add(btnRetryLevel);

		// "Time for your results ..."
		JLabel lblYourResults = new JLabel("Time for your results ...");
		lblYourResults.setFont(new Font("Arial", Font.PLAIN, 22));
		lblYourResults.setBounds(189, 31, 401, 37);
		add(lblYourResults);

		// Panel containing the results label and a white background
		resultsPanel = new JPanel();
		resultsPanel.setBackground(Color.WHITE);
		resultsPanel.setBounds(189, 79, 373, 113);
		add(resultsPanel);
		resultsPanel.setLayout(new CardLayout(0, 0));

		// label showing the results, to be displayed only if there are results
		lblResults = new JLabel("Results");
		resultsPanel.add(lblResults, "Results");
		lblResults.setBackground(Color.LIGHT_GRAY);
		lblResults.setHorizontalAlignment(SwingConstants.CENTER);
		lblResults.setFont(new Font("Arial", Font.PLAIN, 34));

		// label to display when there are no results
		noResults = new JLabel("No Results ");
		noResults.setHorizontalAlignment(SwingConstants.CENTER);
		noResults.setFont(new Font("Arial", Font.PLAIN, 34));
		resultsPanel.add(noResults, "No Results");

		// Panel containing the type of feedback QuizDone provides according to results
		userInteractionPanel = new JPanel();
		userInteractionPanel.setBounds(93, 208, 421, 86);
		add(userInteractionPanel);
		userInteractionPanel.setLayout(new CardLayout(0, 0)); // card layout

		// rewards panel with the rewards buttons
		JPanel rewardPanel = new JPanel();
		userInteractionPanel.add(rewardPanel, "Rewards");
		rewardPanel.setLayout(null);
		// video reward button
		btnVideo = new JButton("VIDEO REWARD");
		btnVideo.addActionListener(this);
		btnVideo.setFont(new Font("Arial", Font.PLAIN, 13));
		btnVideo.setBounds(0, 47, 421, 28);
		rewardPanel.add(btnVideo);
		// claim reward label
		JLabel lblGoodJobPlease = new JLabel("Good job! Please claim your reward");
		lblGoodJobPlease.setFont(new Font("Arial", Font.PLAIN, 20));
		lblGoodJobPlease.setBounds(0, 0, 421, 43);
		rewardPanel.add(lblGoodJobPlease);

		// "No Words" interation label
		JLabel zeroQuestions = new JLabel("There are no words to spell in this quiz, try another level ");
		zeroQuestions.setFont(new Font("Arial", Font.PLAIN, 17));
		zeroQuestions.setHorizontalAlignment(SwingConstants.CENTER);
		userInteractionPanel.add(zeroQuestions, "No Words"); // added to be switched as a card

		// "Good Try" interaction label
		JLabel lblGoodTry = new JLabel("Good try ! You can definitely do better next time !");
		lblGoodTry.setFont(new Font("Arial", Font.PLAIN, 18));
		lblGoodTry.setHorizontalAlignment(SwingConstants.CENTER);
		userInteractionPanel.add(lblGoodTry, "Good Try"); // added to be switched as a card

		// "Good Job" interaction label
		JLabel lblGoodJob = new JLabel("Good Job ! Try another level ?");
		lblGoodJob.setFont(new Font("Arial", Font.PLAIN, 20));
		lblGoodJob.setHorizontalAlignment(SwingConstants.CENTER);
		userInteractionPanel.add(lblGoodJob, "Good Job"); // added to be switched as a card

		// "No Review" interaction label
		JLabel zeroReview = new JLabel("No words to review, consider trying another level");
		userInteractionPanel.add(zeroReview, "No Review"); // added to be switched as a card
		zeroReview.setHorizontalAlignment(SwingConstants.CENTER);
		zeroReview.setFont(new Font("Arial", Font.PLAIN, 18));

		// Panel to contain components for trying another level
		tryAnotherPanel = new JPanel();
		tryAnotherPanel.setBounds(93, 334, 421, 30);
		add(tryAnotherPanel);
		tryAnotherPanel.setLayout(new CardLayout(0, 0));

		// try another level button
		btnTryAnotherLevel = new JButton("TRY ANOTHER LEVEL\r\n");
		tryAnotherPanel.add(btnTryAnotherLevel, "Try");// added to be switched as a card
		btnTryAnotherLevel.addActionListener(this);
		btnTryAnotherLevel.setFont(new Font("Arial", Font.PLAIN, 13));

		// review another level button
		btnReviewAnotherLevel = new JButton("REVIEW ANOTHER LEVEL\r\n");
		btnReviewAnotherLevel.addActionListener(this);// added to be switched as a card
		btnReviewAnotherLevel.setFont(new Font("Arial", Font.PLAIN, 13));
		tryAnotherPanel.add(btnReviewAnotherLevel, "Review");

		// avatar
		JLabel avatar = new JLabel("");
		avatar.setIcon(new ImageIcon("img/avatar.png"));
		avatar.setBounds(20, 42, 159, 150);
		add(avatar);

		// done button
		btnDone = new JButton("DONE");
		btnDone.addActionListener(this);
		btnDone.setFont(new Font("Arial", Font.PLAIN, 13));
		btnDone.setBounds(93, 375, 421, 30);
		add(btnDone);
	}

}
