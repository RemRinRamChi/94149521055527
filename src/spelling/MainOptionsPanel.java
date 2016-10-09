package spelling;

import javax.swing.JPanel;

import spelling.quiz.QuizChooser;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;

public class MainOptionsPanel extends JPanel {
	private SpellingAidMain mainFrame;

	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public MainOptionsPanel(SpellingAidMain contentFrame){
		this();
		mainFrame = contentFrame;
	}
	/**
	 * Create the panel.
	 */
	public MainOptionsPanel() {
		setLayout(null);
		
		JPanel hiPanel = new JPanel();
		hiPanel.setBackground(Color.WHITE);
		hiPanel.setBounds(0, 11, 450, 200);
		add(hiPanel);
		hiPanel.setLayout(null);
		
		JLabel avatar = new JLabel("");
		avatar.setIcon(new ImageIcon("C:\\Users\\YaoJian\\Downloads\\avatar.png"));
		avatar.setBounds(24, 11, 159, 184);
		hiPanel.add(avatar);
		
		JLabel lblHiSherlock = new JLabel("Hi Sherlock");
		lblHiSherlock.setFont(new Font("Asiago", Font.PLAIN, 40));
		lblHiSherlock.setBounds(193, 49, 235, 64);
		hiPanel.add(lblHiSherlock);
		
		JLabel lblHereToHelp = new JLabel("I am here to help you \r\n");
		lblHereToHelp.setFont(new Font("Asiago", Font.PLAIN, 22));
		lblHereToHelp.setBounds(193, 113, 247, 53);
		hiPanel.add(lblHereToHelp);
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBounds(10, 222, 430, 267);
		add(optionsPanel);
		GridBagLayout gbl_optionsPanel = new GridBagLayout();
		gbl_optionsPanel.columnWidths = new int[]{100, 250, 100, 0};
		gbl_optionsPanel.rowHeights = new int[]{0, 20, 40, 40, 40, 40, 40, 40, 0};
		gbl_optionsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_optionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		optionsPanel.setLayout(gbl_optionsPanel);
		
		JButton btnNewButton = new JButton("New Quiz");
		btnNewButton.setFocusPainted(false);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				QuizChooser quizChooser = new QuizChooser(mainFrame,mainFrame.getQuizQuestion());
				quizChooser.setVisible(true);
			}
		});
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 0;
		optionsPanel.add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblPleaseSelectOne = new JLabel("Please select one of the following options");
		lblPleaseSelectOne.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPleaseSelectOne = new GridBagConstraints();
		gbc_lblPleaseSelectOne.insets = new Insets(0, 0, 5, 5);
		gbc_lblPleaseSelectOne.gridx = 1;
		gbc_lblPleaseSelectOne.gridy = 1;
		optionsPanel.add(lblPleaseSelectOne, gbc_lblPleaseSelectOne);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		optionsPanel.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnReviewQuiz = new JButton("Review Quiz\r\n");
		btnReviewQuiz.setFocusPainted(false);
		btnReviewQuiz.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnReviewQuiz = new GridBagConstraints();
		gbc_btnReviewQuiz.fill = GridBagConstraints.BOTH;
		gbc_btnReviewQuiz.insets = new Insets(0, 0, 5, 5);
		gbc_btnReviewQuiz.gridx = 1;
		gbc_btnReviewQuiz.gridy = 3;
		optionsPanel.add(btnReviewQuiz, gbc_btnReviewQuiz);
		
		JButton btnViewStatistics = new JButton("View Statistics");
		btnViewStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.changeCardPanel("Stats");
			}
		});
		btnViewStatistics.setFocusPainted(false);
		btnViewStatistics.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnViewStatistics = new GridBagConstraints();
		gbc_btnViewStatistics.fill = GridBagConstraints.BOTH;
		gbc_btnViewStatistics.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewStatistics.gridx = 1;
		gbc_btnViewStatistics.gridy = 4;
		optionsPanel.add(btnViewStatistics, gbc_btnViewStatistics);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.setFocusPainted(false);
		btnSettings.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.fill = GridBagConstraints.BOTH;
		gbc_btnSettings.insets = new Insets(0, 0, 5, 5);
		gbc_btnSettings.gridx = 1;
		gbc_btnSettings.gridy = 5;
		optionsPanel.add(btnSettings, gbc_btnSettings);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.dispose();
			}
		});
		btnQuit.setFocusPainted(false);
		btnQuit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnQuit = new GridBagConstraints();
		gbc_btnQuit.fill = GridBagConstraints.BOTH;
		gbc_btnQuit.insets = new Insets(0, 0, 5, 5);
		gbc_btnQuit.gridx = 1;
		gbc_btnQuit.gridy = 6;
		optionsPanel.add(btnQuit, gbc_btnQuit);

	}
}
