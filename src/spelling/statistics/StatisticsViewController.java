package spelling.statistics;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import spelling.SpellingAidMain;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
/**
 * This is the GUI for the spelling aid statistics
 * @author yyap601
 *
 */
public class StatisticsViewController extends JPanel {
	private SpellingAidMain mainFrame;
	private JPanel specificQuiz;
	private JPanel statsPanel;
	private JTextArea statsTextArea;
	private JLabel lblHi;

	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public StatisticsViewController(SpellingAidMain contentFrame){
		this();
		mainFrame = contentFrame;
		specificQuiz = new SpecificQuizStats(mainFrame,this);
		statsPanel.add(specificQuiz, "Specific");
	}
	
	/**
	 * Change panel displayed within stats frame
	 * @param mode The state VoxSpellStats is currently in.
	 */
	public void changeCardPanel(String mode){
		// change panel to display according to mode 
		((CardLayout) statsPanel.getLayout()).show(statsPanel, mode);
	}
	
	/**
	 * Create the panel.
	 */
	public StatisticsViewController() {
		setLayout(null);
		
		JPanel friendlyPanel = new JPanel();
		friendlyPanel.setLayout(null);
		friendlyPanel.setBackground(Color.WHITE);
		friendlyPanel.setBounds(0, 0, 475, 180);
		add(friendlyPanel);
		
		JLabel avatar = new JLabel("");
		avatar.setIcon(new ImageIcon("img/avatar.png"));
		avatar.setBounds(24, 11, 159, 155);
		friendlyPanel.add(avatar);
		
		lblHi = new JLabel("Hi Sherlock");
		lblHi.setFont(new Font("Arial", Font.PLAIN, 40));
		lblHi.setBounds(193, 31, 235, 64);
		friendlyPanel.add(lblHi);
		
		JLabel lblHereAreSomeStats = new JLabel("Here are some statistics");
		lblHereAreSomeStats.setFont(new Font("Arial", Font.PLAIN, 22));
		lblHereAreSomeStats.setBounds(193, 95, 272, 53);
		friendlyPanel.add(lblHereAreSomeStats);
		
		statsPanel = new JPanel();
		statsPanel.setBounds(10, 191, 450, 392);
		add(statsPanel);
		statsPanel.setLayout(new CardLayout(0, 0));
		
		JPanel overallStatsPanel = new JPanel();
		statsPanel.add(overallStatsPanel, "Overall");
		overallStatsPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 450, 300);
		overallStatsPanel.add(scrollPane);
		
		statsTextArea = new JTextArea();
		scrollPane.setViewportView(statsTextArea);
		statsTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
		statsTextArea.setEditable(false);
		
		JPanel implementationComingSoon = new JPanel();
		implementationComingSoon.setVisible(false);
		implementationComingSoon.setBounds(10, 311, 227, 81);
		overallStatsPanel.add(implementationComingSoon);
		implementationComingSoon.setLayout(null);
		
		JLabel lblMoreInfo = new JLabel("More on a specific quiz:");
		lblMoreInfo.setBounds(0, 0, 176, 22);
		implementationComingSoon.add(lblMoreInfo);
		lblMoreInfo.setFont(new Font("Arial", Font.PLAIN, 15));
		
		JComboBox quizComboBox = new JComboBox();
		quizComboBox.setBounds(1, 25, 226, 22);
		implementationComingSoon.add(quizComboBox);
		quizComboBox.setModel(new DefaultComboBoxModel(new String[] {"pls click this 1st", "", "This combobox ", "contains all the ", "quiz levels tried ", "by the user, ", "when u click ", "select quiz, it ", "displays things ", "that are similar ", "to when u click ", "\"Select Quiz\" now", ",functionality ", "coming soon", "", "thanks"}));
		quizComboBox.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JButton btnSelectQuiz = new JButton("Select Quiz");
		btnSelectQuiz.setBounds(2, 58, 89, 23);
		implementationComingSoon.add(btnSelectQuiz);
		btnSelectQuiz.setFont(new Font("Arial", Font.PLAIN, 11));
		btnSelectQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeCardPanel("Specific");
			}
		});
		
		JButton btnBack = new JButton("BACK");
		btnBack.setBounds(349, 325, 89, 67);
		overallStatsPanel.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.changeCardPanel("Main");
			}
		});
		btnBack.setFont(new Font("Arial", Font.PLAIN, 13));



	}
	
	public void scrollToTop(){
		statsTextArea.setCaretPosition(0);
	}

	public void appendText(String txt){
		statsTextArea.append(txt);
	}
	
	public void clearStatsArea(){
		statsTextArea.setText("");
	}
	
	public void setUserName(String name){
		lblHi.setText("Hi "+name);
	}
}
