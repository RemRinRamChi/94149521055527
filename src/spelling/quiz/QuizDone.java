package spelling.quiz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import spelling.SpellingAidMain;
import spelling.quiz.SpellList.QuizMode;
import spelling.settings.ClearStatistics;

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
public class QuizDone extends JPanel {
	private SpellingAidMain mainFrame;
	private JLabel lblResults;
	private JPanel userInteractionPanel;
	private JPanel resultsPanel;
	private JPanel tryAnotherPanel;

	public void setLblResults(String results) {
		lblResults.setText(results);
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
		setLayout(null);
		
		JLabel lblYourResults = new JLabel("Time for your results ...");
		lblYourResults.setFont(new Font("Arial", Font.PLAIN, 22));
		lblYourResults.setBounds(189, 31, 401, 37);
		add(lblYourResults);
		
		resultsPanel = new JPanel();
		resultsPanel.setBackground(Color.WHITE);
		resultsPanel.setBounds(189, 79, 373, 113);
		add(resultsPanel);
		resultsPanel.setLayout(new CardLayout(0, 0));
		
		lblResults = new JLabel("7 out of 8 correct !");
		resultsPanel.add(lblResults, "Results");
		lblResults.setBackground(Color.LIGHT_GRAY);
		lblResults.setHorizontalAlignment(SwingConstants.CENTER);
		lblResults.setFont(new Font("Arial", Font.PLAIN, 34));
		
		JLabel noResults = new JLabel("No Results ");
		noResults.setHorizontalAlignment(SwingConstants.CENTER);
		noResults.setFont(new Font("Arial", Font.PLAIN, 34));
		resultsPanel.add(noResults, "No Results");
		
		userInteractionPanel = new JPanel();
		userInteractionPanel.setBounds(93, 208, 421, 86);
		add(userInteractionPanel);
		userInteractionPanel.setLayout(new CardLayout(0, 0));
		
		JPanel rewardPanel = new JPanel();
		userInteractionPanel.add(rewardPanel, "Rewards");
		rewardPanel.setLayout(null);
		
		JButton btnVideo = new JButton("Video Reward");
		btnVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// should switch panel to be gone after one click, reward only received once
				JOptionPane.showMessageDialog(mainFrame,"The big bunny video should be playing (coming soon)", "Video Reward", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnVideo.setFont(new Font("Arial", Font.PLAIN, 13));
		btnVideo.setBounds(0, 47, 194, 28);
		rewardPanel.add(btnVideo);
		
		JButton btnAudio = new JButton("Audio Reward");
		btnAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// should switch panel to be gone after one click, reward only received once
				JOptionPane.showMessageDialog(mainFrame,"Cheering message (coming soon)", "Audio Reward", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnAudio.setFont(new Font("Arial", Font.PLAIN, 13));
		btnAudio.setBounds(227, 47, 194, 28);
		rewardPanel.add(btnAudio);
		
		JLabel lblGoodJobPlease = new JLabel("Good job! Please claim your reward");
		lblGoodJobPlease.setFont(new Font("Arial", Font.PLAIN, 20));
		lblGoodJobPlease.setBounds(0, 0, 421, 43);
		rewardPanel.add(lblGoodJobPlease);
		
		JLabel zeroQuestions = new JLabel("There are no words to spell in this quiz, try another ");
		zeroQuestions.setFont(new Font("Arial", Font.PLAIN, 18));
		zeroQuestions.setHorizontalAlignment(SwingConstants.CENTER);
		userInteractionPanel.add(zeroQuestions, "No Words");
		
		JLabel lblGoodTry = new JLabel("Good try ! You can definitely do better next time !");
		lblGoodTry.setFont(new Font("Arial", Font.PLAIN, 18));
		lblGoodTry.setHorizontalAlignment(SwingConstants.CENTER);
		userInteractionPanel.add(lblGoodTry, "Good Try");
		
		JLabel lblGoodJob = new JLabel("Good Job ! Try another level ?");
		lblGoodJob.setFont(new Font("Arial", Font.PLAIN, 20));
		lblGoodJob.setHorizontalAlignment(SwingConstants.CENTER);
		userInteractionPanel.add(lblGoodJob, "Good Job");
		
		JLabel zeroReview = new JLabel("No words to review, consider trying another ");
		userInteractionPanel.add(zeroReview, "No Review");
		zeroReview.setHorizontalAlignment(SwingConstants.CENTER);
		zeroReview.setFont(new Font("Arial", Font.PLAIN, 18));
		
		tryAnotherPanel = new JPanel();
		tryAnotherPanel.setBounds(93, 305, 421, 30);
		add(tryAnotherPanel);
		tryAnotherPanel.setLayout(new CardLayout(0, 0));
		
		JButton btnTryAnotherLevel = new JButton("TRY ANOTHER LEVEL\r\n");
		tryAnotherPanel.add(btnTryAnotherLevel, "Try");
		btnTryAnotherLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				QuizChooser quizChooser = new QuizChooser(mainFrame,mainFrame.getQuizQuestion(),QuizMode.New);
				quizChooser.setVisible(true);
			}
		});
		btnTryAnotherLevel.setFont(new Font("Arial", Font.PLAIN, 13));
		
		JButton btnReviewAnotherLevel = new JButton("REVIEW ANOTHER LEVEL\r\n");
		btnReviewAnotherLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QuizChooser quizChooser = new QuizChooser(mainFrame,mainFrame.getQuizQuestion(),QuizMode.Review);
				quizChooser.setVisible(true);
			}
		});
		btnReviewAnotherLevel.setFont(new Font("Arial", Font.PLAIN, 13));
		tryAnotherPanel.add(btnReviewAnotherLevel, "Review");
		
		JLabel avatar = new JLabel("");
		avatar.setIcon(new ImageIcon("img/avatar.png"));
		avatar.setBounds(20, 42, 159, 150);
		add(avatar);
		
		JButton btnDone = new JButton("DONE");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.changeCardPanel("Main");
			}
		});
		btnDone.setFont(new Font("Arial", Font.PLAIN, 13));
		btnDone.setBounds(93, 346, 421, 30);
		add(btnDone);

	}

	// change results to display according to mode 
	public void changeResultPanel(String mode){
		((CardLayout) resultsPanel.getLayout()).show(resultsPanel, mode);
	}

	// change user interaction to display according to mode 
	public void changeUserInteraction(String mode){
		((CardLayout) userInteractionPanel.getLayout()).show(userInteractionPanel, mode);
	}
	
	// change next level option to display according to mode 
	public void changeNextLevelPanel(String mode){
		((CardLayout) tryAnotherPanel.getLayout()).show(tryAnotherPanel, mode);
	}
}
