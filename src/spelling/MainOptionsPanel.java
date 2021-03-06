package spelling;

import javax.swing.JPanel;

import spelling.quiz.QuizChooser;
import spelling.quiz.SpellList.QuizMode;
import spelling.statistics.StatisticsModel;

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
import javax.swing.SwingConstants;
/**
 * This is the panel which contains the main VoxSpell options 
 * {New Quiz, Review Quiz, View Statistics, Options and Quit}
 * @author yyap601
 *
 */
public class MainOptionsPanel extends JPanel implements ActionListener{
	private SpellingAidMain mainFrame;
	private JPanel hiPanel;
	private JPanel optionsPanel;
	private JLabel lblHiUser;
	private JLabel lblHereToHelp;
	private JLabel lblPleaseSelectOne;
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
		applyTheme();
	}

	/**
	 * Set the name to put besides "Hi" when the application is greeting the user
	 * @param name user's name
	 */
	public void setUserName(String name){
		lblHiUser.setText("Hi "+name);
	}

	// perform appropriate actions based on button press
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnNewQuiz){ // New Quiz
			QuizChooser quizChooser = new QuizChooser(mainFrame,mainFrame.getQuiz(),QuizMode.New);
			quizChooser.setVisible(true);
		} else if(e.getSource()==btnReviewQuiz){ // Review Quiz
			QuizChooser quizChooser = new QuizChooser(mainFrame,mainFrame.getQuiz(),QuizMode.Review);
			quizChooser.setVisible(true);
		} else if(e.getSource()==btnViewStatistics){ // View Statistics
			// instantiate the statistics object and execute it
			StatisticsModel statsWin = new StatisticsModel(mainFrame);
			statsWin.execute();
		} else if(e.getSource()==btnSettings){ // Settings
			mainFrame.changeCardPanel("Settings");
		} else if(e.getSource()==btnQuit){ // Quit
			mainFrame.dispose();
		}
	}

	/**
	 * Apply colour to components
	 */
	public void applyTheme(){
		Color backgroundColour = new Color(255,255,255);
		Color bannerColour = new Color(250,250,250);
		Color buttonText = new Color(255,255,255);
		Color normalText = new Color(0,0,0);
		Color buttonColour = new Color(15,169,249);
		
		// background color
		this.setBackground(backgroundColour);
		// option pane color
		optionsPanel.setBackground(backgroundColour);
		
		// banner color
		hiPanel.setBackground(bannerColour);
		
		// normal text
		lblHiUser.setForeground(normalText);
		lblHereToHelp.setForeground(normalText);
		lblPleaseSelectOne.setForeground(normalText);
		// button text
		btnNewQuiz.setForeground(buttonText);
		btnReviewQuiz.setForeground(buttonText);
		btnViewStatistics.setForeground(buttonText);
		btnSettings.setForeground(buttonText);
		btnQuit.setForeground(buttonText);
		// normal button color
		btnNewQuiz.setBackground(buttonColour);
		btnReviewQuiz.setBackground(buttonColour);
		btnViewStatistics.setBackground(buttonColour);
		btnSettings.setBackground(buttonColour);
		btnQuit.setBackground(buttonColour);
	}

	/**
	 *  Create all components and lay them out properly
	 */
	private void createAndLayoutComponents(){
		// set absolute layout
		setLayout(null);

		// Panel to say hi to user
		hiPanel = new JPanel();
		hiPanel.setBackground(Color.WHITE);
		hiPanel.setBounds(0, 11, 450, 180);
		add(hiPanel);
		hiPanel.setLayout(null);

		// avatar
		JLabel avatar = new JLabel("");
		avatar.setVerticalAlignment(SwingConstants.BOTTOM);
		avatar.setIcon(new ImageIcon("img/avatar.png"));
		avatar.setBounds(26, 6, 146, 174);
		hiPanel.add(avatar);

		// greeting label
		lblHiUser = new JLabel("Hi");
		lblHiUser.setFont(new Font("Arial", Font.PLAIN, 36));
		lblHiUser.setBounds(197, 37, 235, 64);
		hiPanel.add(lblHiUser);

		// "I am here to help you"
		lblHereToHelp = new JLabel("I am here to help you \r\n");
		lblHereToHelp.setFont(new Font("Arial", Font.PLAIN, 22));
		lblHereToHelp.setBounds(197, 100, 247, 53);
		hiPanel.add(lblHereToHelp);

		// panel containing main options
		optionsPanel = new JPanel();
		optionsPanel.setBackground(new Color(245, 245, 245));
		optionsPanel.setBounds(10, 214, 430, 267);
		add(optionsPanel);
		GridBagLayout gbl_optionsPanel = new GridBagLayout();
		gbl_optionsPanel.columnWidths = new int[]{100, 250, 100, 0};
		gbl_optionsPanel.rowHeights = new int[]{0, 20, 40, 40, 40, 40, 40, 40, 0};
		gbl_optionsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_optionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		optionsPanel.setLayout(gbl_optionsPanel);

		// "Please select one of the following options" label
		lblPleaseSelectOne = new JLabel("Please select one of the following options");
		lblPleaseSelectOne.setFont(new Font("Arial", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPleaseSelectOne = new GridBagConstraints();
		gbc_lblPleaseSelectOne.insets = new Insets(0, 0, 5, 5);
		gbc_lblPleaseSelectOne.gridx = 1;
		gbc_lblPleaseSelectOne.gridy = 1;
		optionsPanel.add(lblPleaseSelectOne, gbc_lblPleaseSelectOne);

		// New Quiz button
		btnNewQuiz = new JButton("New Quiz");
		btnNewQuiz.setToolTipText("Start a new spelling quiz");
		btnNewQuiz.setFocusPainted(false);
		btnNewQuiz.setFont(new Font("Arial", Font.BOLD, 16));
		btnNewQuiz.addActionListener(this);

		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		optionsPanel.add(btnNewQuiz, gbc_btnNewButton);

		// vertical strut.substring(1)
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 0;
		optionsPanel.add(verticalStrut, gbc_verticalStrut);

		// Review Quiz button
		btnReviewQuiz = new JButton("Review Quiz\r\n");
		btnReviewQuiz.setToolTipText("Review a spelling quiz");
		btnReviewQuiz.addActionListener(this);
		btnReviewQuiz.setFocusPainted(false);
		btnReviewQuiz.setFont(new Font("Arial", Font.BOLD, 16));
		GridBagConstraints gbc_btnReviewQuiz = new GridBagConstraints();
		gbc_btnReviewQuiz.fill = GridBagConstraints.BOTH;
		gbc_btnReviewQuiz.insets = new Insets(0, 0, 5, 5);
		gbc_btnReviewQuiz.gridx = 1;
		gbc_btnReviewQuiz.gridy = 3;
		optionsPanel.add(btnReviewQuiz, gbc_btnReviewQuiz);

		// View Statistics button
		btnViewStatistics = new JButton("View Statistics");
		btnViewStatistics.setToolTipText("Look at quiz results");
		btnViewStatistics.addActionListener(this);
		btnViewStatistics.setFocusPainted(false);
		btnViewStatistics.setFont(new Font("Arial", Font.BOLD, 16));
		GridBagConstraints gbc_btnViewStatistics = new GridBagConstraints();
		gbc_btnViewStatistics.fill = GridBagConstraints.BOTH;
		gbc_btnViewStatistics.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewStatistics.gridx = 1;
		gbc_btnViewStatistics.gridy = 4;
		optionsPanel.add(btnViewStatistics, gbc_btnViewStatistics);

		// Settings button
		btnSettings = new JButton("Options");
		btnSettings.setToolTipText("Change settings");
		btnSettings.addActionListener(this);
		btnSettings.setFocusPainted(false);
		btnSettings.setFont(new Font("Arial", Font.BOLD, 16));
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.fill = GridBagConstraints.BOTH;
		gbc_btnSettings.insets = new Insets(0, 0, 5, 5);
		gbc_btnSettings.gridx = 1;
		gbc_btnSettings.gridy = 5;
		optionsPanel.add(btnSettings, gbc_btnSettings);

		// Quit button
		btnQuit = new JButton("Quit");
		btnQuit.setToolTipText("Quit VoxSpell");
		btnQuit.addActionListener(this);
		btnQuit.setFocusPainted(false);
		btnQuit.setFont(new Font("Arial", Font.BOLD, 16));
		GridBagConstraints gbc_btnQuit = new GridBagConstraints();
		gbc_btnQuit.fill = GridBagConstraints.BOTH;
		gbc_btnQuit.insets = new Insets(0, 0, 5, 5);
		gbc_btnQuit.gridx = 1;
		gbc_btnQuit.gridy = 6;
		optionsPanel.add(btnQuit, gbc_btnQuit);

	}

}
