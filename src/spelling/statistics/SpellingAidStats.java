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

public class SpellingAidStats extends JPanel {
	private SpellingAidMain mainFrame;

	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public SpellingAidStats(SpellingAidMain contentFrame){
		this();
		mainFrame = contentFrame;
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
		lblHi.setFont(new Font("Asiago", Font.PLAIN, 40));
		lblHi.setBounds(193, 31, 235, 64);
		friendlyPanel.add(lblHi);
		
		JLabel lblHereAreSomeStats = new JLabel("Here are some statistics");
		lblHereAreSomeStats.setFont(new Font("Asiago", Font.PLAIN, 22));
		lblHereAreSomeStats.setBounds(193, 95, 272, 53);
		friendlyPanel.add(lblHereAreSomeStats);
		
		JTextArea statsTextArea = new JTextArea();
		statsTextArea.setEditable(false);
		statsTextArea.setBounds(10, 191, 450, 300);
		add(statsTextArea);
		
		JLabel lblMoreInfo = new JLabel("More on a specific quiz:");
		lblMoreInfo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMoreInfo.setBounds(20, 502, 176, 22);
		add(lblMoreInfo);
		
		JComboBox quizComboBox = new JComboBox();
		quizComboBox.setBounds(21, 527, 226, 22);
		add(quizComboBox);
		
		JButton btnSelectQuiz = new JButton("Select Quiz");
		btnSelectQuiz.setBounds(22, 560, 89, 23);
		add(btnSelectQuiz);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.changeCardPanel("Main");
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnBack.setBounds(359, 516, 89, 67);
		add(btnBack);

	}
}
