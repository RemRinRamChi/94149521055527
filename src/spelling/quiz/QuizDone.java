package spelling.quiz;

import javax.swing.JPanel;
import javax.swing.JLabel;

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
 * This is the GUI that's displayed when the user is done with a quiz, displaying the results 
 * and advancing options and also rewards
 * @author yyap601
 *
 */
public class QuizDone extends JPanel implements ActionListener{
	private JLabel avatar;
	private SpellingAidMain mainFrame;
	private JLabel lblResults;
	private JLabel noResults;
	private JLabel lblGoodJobPlease;
	private JLabel zeroQuestions;
	private JLabel lblGoodTry;
	private JLabel lblGoodJob;
	private JLabel zeroReview;
	private JLabel lblYourResults;
	private JPanel rewardPanel;
	private JPanel userInteractionPanel;
	private JPanel resultsPanel;
	private JPanel tryAnotherPanel;
	private JButton btnVideo;
	private JButton btnTryAnotherLevel;
	private JButton btnDone;
	private JButton btnReviewAnotherLevel;
	private JButton btnRetryLevel;
	private String level; 
	private JLabel lblLevel;
	
	/**
	 * Set results label
	 * @param results
	 */
	public void setLblResults(String results) {
		lblResults.setText(results);
	}
	/**
	 * Set level of quiz done
	 * @param lvl
	 */
	public void setLevel(String lvl){
		level = lvl;
		lblLevel.setText("Quiz: "+lvl);
	}
	/**
	 * Return the level of quiz done
	 */
	public String getLevel(){
		return level;
	}
	/**
	 * Set the avatar to be in the Good Job mode (1 less than total questions right (not 0))
	 */
	public void setRewardsAvatar(){
		avatar.setIcon(new ImageIcon("img/Rewards.png"));
	}
	
	/**
	 * Set the avatar to be in the Good Try mode (not Good Job mode)
	 */
	public void setNormalAvatar(){
		avatar.setIcon(new ImageIcon("img/LongAvatar.png"));
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
		applyTheme();
	}
	
	/**
	 * Apply colour to components
	 */
	public void applyTheme(){
		Color backgroundColour = new Color(255,255,255);
		Color buttonText = new Color(255,255,255);
		Color normalText = new Color(0,0,0);
		Color buttonColour = new Color(15,169,249);
		
		resultsPanel.setBackground(new Color(250,250,250));
		// background color
		this.setBackground(backgroundColour);
		userInteractionPanel.setBackground(backgroundColour);
		rewardPanel.setBackground(backgroundColour);
		tryAnotherPanel.setBackground(backgroundColour);

		// normal text
		lblResults.setForeground(normalText);
		noResults.setForeground(normalText);
		lblGoodJobPlease.setForeground(normalText);
		zeroQuestions.setForeground(normalText);
		lblGoodTry.setForeground(normalText);
		lblGoodJob.setForeground(normalText);
		zeroReview.setForeground(normalText);
		lblYourResults.setForeground(normalText);
		lblLevel.setForeground(normalText);
		
		// button text
		btnVideo.setForeground(buttonText);
		btnTryAnotherLevel.setForeground(buttonText);
		btnDone.setForeground(buttonText);
		btnReviewAnotherLevel.setForeground(buttonText);
		btnRetryLevel.setForeground(buttonText);
		
		// normal button color
		btnVideo.setBackground(buttonColour);
		btnTryAnotherLevel.setBackground(buttonColour);
		btnDone.setBackground(buttonColour);
		btnReviewAnotherLevel.setBackground(buttonColour);
		btnRetryLevel.setBackground(buttonColour);
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
		
				// avatar
				avatar = new JLabel("");
				avatar.setIcon(new ImageIcon("img/GoodJob.png"));
				avatar.setBounds(39, 32, 142, 231);
				add(avatar);
		btnRetryLevel.setFont(new Font("Arial", Font.BOLD, 14));
		btnRetryLevel.setBounds(86, 339, 421, 30);
		add(btnRetryLevel);

		// "Time for your results ..."
		lblYourResults = new JLabel("Time for your results ...");
		lblYourResults.setFont(new Font("Arial", Font.PLAIN, 24));
		lblYourResults.setBounds(182, 22, 391, 56);
		add(lblYourResults);

		// Panel containing the results label and a white background
		resultsPanel = new JPanel();
		resultsPanel.setBackground(Color.WHITE);
		resultsPanel.setBounds(182, 76, 354, 156);
		add(resultsPanel);
		resultsPanel.setLayout(new CardLayout(0, 0));

		// label showing the results, to be displayed only if there are results
		lblResults = new JLabel("Results");
		resultsPanel.add(lblResults, "Results");
		lblResults.setBackground(Color.LIGHT_GRAY);
		lblResults.setHorizontalAlignment(SwingConstants.CENTER);
		lblResults.setFont(new Font("Arial", Font.PLAIN, 30));

		// label to display when there are no results
		noResults = new JLabel("No Results ");
		noResults.setHorizontalAlignment(SwingConstants.CENTER);
		noResults.setFont(new Font("Arial", Font.PLAIN, 34));
		resultsPanel.add(noResults, "No Results");

		// Panel containing the type of feedback QuizDone provides according to results
		userInteractionPanel = new JPanel();
		userInteractionPanel.setBounds(86, 254, 421, 86);
		add(userInteractionPanel);
		userInteractionPanel.setLayout(new CardLayout(0, 0)); // card layout

		// rewards panel with the rewards buttons
		rewardPanel = new JPanel();
		userInteractionPanel.add(rewardPanel, "Rewards");
		rewardPanel.setLayout(null);
		// video reward button
		btnVideo = new JButton("VIDEO REWARD");
		btnVideo.addActionListener(this);
		btnVideo.setFont(new Font("Arial", Font.BOLD, 14));
		btnVideo.setBounds(0, 47, 421, 28);
		rewardPanel.add(btnVideo);
		// claim reward label
		lblGoodJobPlease = new JLabel("Good job! Please claim your reward");
		lblGoodJobPlease.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoodJobPlease.setFont(new Font("Arial", Font.PLAIN, 18));
		lblGoodJobPlease.setBounds(0, 0, 421, 43);
		rewardPanel.add(lblGoodJobPlease);

		// "No Words" interation label
		zeroQuestions = new JLabel("There are no words in this level, try another level ");
		zeroQuestions.setFont(new Font("Arial", Font.PLAIN, 18));
		zeroQuestions.setHorizontalAlignment(SwingConstants.CENTER);
		userInteractionPanel.add(zeroQuestions, "No Words"); // added to be switched as a card

		// "Good Try" interaction label
		lblGoodTry = new JLabel("Good try ! You can definitely do better next time !");
		lblGoodTry.setFont(new Font("Arial", Font.PLAIN, 18));
		lblGoodTry.setHorizontalAlignment(SwingConstants.CENTER);
		userInteractionPanel.add(lblGoodTry, "Good Try"); // added to be switched as a card

		// "Good Job" interaction label
		lblGoodJob = new JLabel("Good Job ! Try another level ?");
		lblGoodJob.setFont(new Font("Arial", Font.PLAIN, 18));
		lblGoodJob.setHorizontalAlignment(SwingConstants.CENTER);
		userInteractionPanel.add(lblGoodJob, "Good Job"); // added to be switched as a card

		// "No Review" interaction label
		zeroReview = new JLabel("No words to review, consider trying another level");
		userInteractionPanel.add(zeroReview, "No Review"); // added to be switched as a card
		zeroReview.setHorizontalAlignment(SwingConstants.CENTER);
		zeroReview.setFont(new Font("Arial", Font.PLAIN, 18));

		// Panel to contain components for trying another level
		tryAnotherPanel = new JPanel();
		tryAnotherPanel.setBounds(86, 380, 421, 30);
		add(tryAnotherPanel);
		tryAnotherPanel.setLayout(new CardLayout(0, 0));

		// try another level button
		btnTryAnotherLevel = new JButton("TRY ANOTHER LEVEL\r\n");
		tryAnotherPanel.add(btnTryAnotherLevel, "Try");// added to be switched as a card
		btnTryAnotherLevel.addActionListener(this);
		btnTryAnotherLevel.setFont(new Font("Arial", Font.BOLD, 14));

		// review another level button
		btnReviewAnotherLevel = new JButton("REVIEW ANOTHER LEVEL\r\n");
		btnReviewAnotherLevel.addActionListener(this);// added to be switched as a card
		btnReviewAnotherLevel.setFont(new Font("Arial", Font.BOLD, 14));
		tryAnotherPanel.add(btnReviewAnotherLevel, "Review");

		// done button
		btnDone = new JButton("DONE");
		btnDone.addActionListener(this);
		btnDone.setFont(new Font("Arial", Font.BOLD, 14));
		btnDone.setBounds(86, 421, 421, 30);
		add(btnDone);
		
		// level label at the bottom
		lblLevel = new JLabel("Level");
		lblLevel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLevel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLevel.setBounds(304, 463, 203, 16);
		add(lblLevel);
	}
}
