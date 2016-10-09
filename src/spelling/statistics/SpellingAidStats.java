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

public class SpellingAidStats extends JPanel {
	private SpellingAidMain mainFrame;
	private JPanel specificQuiz;
	private JPanel statsPanel;
	private JTextArea statsTextArea;

	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public SpellingAidStats(SpellingAidMain contentFrame){
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
	public SpellingAidStats() {
		setLayout(null);
		
		JPanel friendlyPanel = new JPanel();
		friendlyPanel.setLayout(null);
		friendlyPanel.setBackground(Color.WHITE);
		friendlyPanel.setBounds(0, 0, 475, 180);
		add(friendlyPanel);
		
		JLabel avatar = new JLabel("");
		avatar.setIcon(new ImageIcon("C:\\Users\\YaoJian\\Downloads\\avatar.png"));
		avatar.setBounds(24, 11, 159, 155);
		friendlyPanel.add(avatar);
		
		JLabel lblHi = new JLabel("Hi Sherlock");
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
		
		statsTextArea = new JTextArea();
		statsTextArea.setFont(new Font("Arial", Font.PLAIN, 13));
		statsTextArea.setBounds(0, 0, 450, 300);
		overallStatsPanel.add(statsTextArea);
		statsTextArea.setEditable(false);
		
		JLabel lblMoreInfo = new JLabel("More on a specific quiz:");
		lblMoreInfo.setBounds(10, 311, 176, 22);
		overallStatsPanel.add(lblMoreInfo);
		lblMoreInfo.setFont(new Font("Arial", Font.PLAIN, 15));
		
		JComboBox quizComboBox = new JComboBox();
		quizComboBox.setFont(new Font("Arial", Font.PLAIN, 11));
		quizComboBox.setBounds(11, 336, 226, 22);
		overallStatsPanel.add(quizComboBox);
		
		JButton btnSelectQuiz = new JButton("Select Quiz");
		btnSelectQuiz.setFont(new Font("Arial", Font.PLAIN, 11));
		btnSelectQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeCardPanel("Specific");
			}
		});
		btnSelectQuiz.setBounds(12, 369, 89, 23);
		overallStatsPanel.add(btnSelectQuiz);
		
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
	

	public void appendText(String txt){
		statsTextArea.append(txt);
	}
}
