package spelling;

import javax.swing.JPanel;

import spelling.quiz.QuizChooser;
import spelling.quiz.SpellList.QuizMode;
import spelling.settings.ClearStatistics;
import spelling.statistics.SpellingAidStatistics;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
/**
 * This is the panel which contains the main VoxSpell options
 * @author yyap601
 *
 */
public class MainOptionsPanel extends JPanel implements ActionListener{
	private SpellingAidMain mainFrame;
	private JLabel lblHiUser;
	private JButton btnNewQuiz;
	private JButton btnReviewQuiz;
	private JButton btnViewStatistics;
	private JButton btnSettings;
	private JButton btnQuit;

	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public MainOptionsPanel(SpellingAidMain contentFrame){
		this();
		mainFrame = contentFrame;
	}
	/**
	 * Create the MainOptionsPanel and layout its components.
	 */
	public MainOptionsPanel() {
		createAndLayoutComponents();
	}

	/**
	 * Set the name to put besides "Hi" when the application is greeting the user
	 * @param name name: user's name
	 */
	public void setUserName(String name){
		lblHiUser.setText("Hi "+name);
	}

	// perform appropriate actions based on button press
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnNewQuiz){ // New Quiz
			QuizChooser quizChooser = new QuizChooser(mainFrame,mainFrame.getQuizQuestion(),QuizMode.New);
			quizChooser.setVisible(true);
		} else if(e.getSource()==btnReviewQuiz){ // Review Quiz
			QuizChooser quizChooser = new QuizChooser(mainFrame,mainFrame.getQuizQuestion(),QuizMode.Review);
			quizChooser.setVisible(true);
		} else if(e.getSource()==btnViewStatistics){ // View Statistics
			// instantiate the statistics object and execute it
			SpellingAidStatistics statsWin = new SpellingAidStatistics(mainFrame.getVoxSpellStats());
			statsWin.execute();
			mainFrame.changeCardPanel("Stats");
		} else if(e.getSource()==btnSettings){ // Settings
			mainFrame.changeCardPanel("Settings");
		} else if(e.getSource()==btnQuit){ // Quit
			mainFrame.dispose();
		}
	}

	/**
	 *  Create all components and lay them out properly
	 */
	private void createAndLayoutComponents(){
		// set absolute layout
		setLayout(null);

		// Panel to say hi to user
		JPanel hiPanel = new JPanel();
		hiPanel.setBackground(Color.WHITE);
		hiPanel.setBounds(0, 11, 450, 200);
		add(hiPanel);
		hiPanel.setLayout(null);

		// avatar
		JLabel avatar = new JLabel("");
		avatar.setIcon(new ImageIcon("img/avatar.png"));
		avatar.setBounds(24, 11, 159, 184);
		hiPanel.add(avatar);

		// greeting label
		lblHiUser = new JLabel("Hi");
		lblHiUser.setFont(new Font("Arial", Font.PLAIN, 40));
		lblHiUser.setBounds(193, 49, 235, 64);
		hiPanel.add(lblHiUser);

		// "I am here to help you"
		JLabel lblHereToHelp = new JLabel("I am here to help you \r\n");
		lblHereToHelp.setFont(new Font("Arial", Font.PLAIN, 22));
		lblHereToHelp.setBounds(193, 113, 247, 53);
		hiPanel.add(lblHereToHelp);

		// panel containing main options
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBounds(10, 222, 430, 267);
		add(optionsPanel);
		GridBagLayout gbl_optionsPanel = new GridBagLayout();
		gbl_optionsPanel.columnWidths = new int[]{100, 250, 100, 0};
		gbl_optionsPanel.rowHeights = new int[]{0, 20, 40, 40, 40, 40, 40, 40, 0};
		gbl_optionsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_optionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		optionsPanel.setLayout(gbl_optionsPanel);

		// "Please select one of the following options" label
		JLabel lblPleaseSelectOne = new JLabel("Please select one of the following options");
		lblPleaseSelectOne.setFont(new Font("Arial", Font.PLAIN, 16));
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
		optionsPanel.add(btnNewQuiz, gbc_btnNewButton);

		// New Quiz button
		btnNewQuiz = new JButton("New Quiz");
		btnNewQuiz.setFocusPainted(false);
		btnNewQuiz.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewQuiz.addActionListener(this);

		// vertical strut
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 0;
		optionsPanel.add(verticalStrut, gbc_verticalStrut);

		// Review Quiz button
		btnReviewQuiz = new JButton("Review Quiz\r\n");
		btnReviewQuiz.addActionListener(this);
		btnReviewQuiz.setFocusPainted(false);
		btnReviewQuiz.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnReviewQuiz = new GridBagConstraints();
		gbc_btnReviewQuiz.fill = GridBagConstraints.BOTH;
		gbc_btnReviewQuiz.insets = new Insets(0, 0, 5, 5);
		gbc_btnReviewQuiz.gridx = 1;
		gbc_btnReviewQuiz.gridy = 3;
		optionsPanel.add(btnReviewQuiz, gbc_btnReviewQuiz);

		// View Statistics button
		btnViewStatistics = new JButton("View Statistics");
		btnViewStatistics.addActionListener(this);
		btnViewStatistics.setFocusPainted(false);
		btnViewStatistics.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnViewStatistics = new GridBagConstraints();
		gbc_btnViewStatistics.fill = GridBagConstraints.BOTH;
		gbc_btnViewStatistics.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewStatistics.gridx = 1;
		gbc_btnViewStatistics.gridy = 4;
		optionsPanel.add(btnViewStatistics, gbc_btnViewStatistics);

		// Settings button
		btnSettings = new JButton("Options");
		btnSettings.addActionListener(this);
		btnSettings.setFocusPainted(false);
		btnSettings.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.fill = GridBagConstraints.BOTH;
		gbc_btnSettings.insets = new Insets(0, 0, 5, 5);
		gbc_btnSettings.gridx = 1;
		gbc_btnSettings.gridy = 5;
		optionsPanel.add(btnSettings, gbc_btnSettings);

		// Quit button
		btnQuit = new JButton("Quit");
		btnQuit.addActionListener(this);
		btnQuit.setFocusPainted(false);
		btnQuit.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnQuit = new GridBagConstraints();
		gbc_btnQuit.fill = GridBagConstraints.BOTH;
		gbc_btnQuit.insets = new Insets(0, 0, 5, 5);
		gbc_btnQuit.gridx = 1;
		gbc_btnQuit.gridy = 6;
		optionsPanel.add(btnQuit, gbc_btnQuit);
	}

}
