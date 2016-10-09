package spelling.quiz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import spelling.SpellingAidMain;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class QuizDone extends JPanel {
	private SpellingAidMain mainFrame;
	private JLabel lblResults;

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
		
		JPanel resultsPanel = new JPanel();
		resultsPanel.setBackground(Color.WHITE);
		resultsPanel.setBounds(189, 79, 373, 113);
		add(resultsPanel);
		resultsPanel.setLayout(null);
		
		lblResults = new JLabel("7 out of 8 correct !");
		lblResults.setBounds(0, 0, 373, 113);
		resultsPanel.add(lblResults);
		lblResults.setBackground(Color.LIGHT_GRAY);
		lblResults.setHorizontalAlignment(SwingConstants.CENTER);
		lblResults.setFont(new Font("Arial", Font.PLAIN, 34));
		
		JLabel lblGoodJob = new JLabel("Good job! Please claim for reward");
		lblGoodJob.setFont(new Font("Arial", Font.PLAIN, 20));
		lblGoodJob.setBounds(93, 208, 421, 37);
		add(lblGoodJob);
		
		JButton btnTryAnotherLevel = new JButton("TRY ANOTHER LEVEL\r\n");
		btnTryAnotherLevel.setFont(new Font("Arial", Font.PLAIN, 13));
		btnTryAnotherLevel.setBounds(93, 305, 421, 30);
		add(btnTryAnotherLevel);
		
		JPanel rewardPanel = new JPanel();
		rewardPanel.setBounds(93, 256, 421, 38);
		add(rewardPanel);
		rewardPanel.setLayout(null);
		
		JButton btnVideo = new JButton("Video Reward");
		btnVideo.setFont(new Font("Arial", Font.PLAIN, 13));
		btnVideo.setBounds(0, 0, 194, 28);
		rewardPanel.add(btnVideo);
		
		JButton btnAudio = new JButton("Audio Reward");
		btnAudio.setFont(new Font("Arial", Font.PLAIN, 13));
		btnAudio.setBounds(227, 0, 194, 28);
		rewardPanel.add(btnAudio);
		
		JLabel avatar = new JLabel("");
		avatar.setIcon(new ImageIcon("C:\\Users\\YaoJian\\Downloads\\avatar.png"));
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
}
