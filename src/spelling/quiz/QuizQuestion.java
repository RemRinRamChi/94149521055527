package spelling.quiz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;

import spelling.SpellingAidMain;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class QuizQuestion extends JPanel {
	private JTextField textField;
	private SpellingAidMain mainFrame;

	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public QuizQuestion(SpellingAidMain contentFrame){
		this();
		mainFrame = contentFrame;
	}
	/**
	 * Create the panel.
	 */
	public QuizQuestion() {
		setLayout(null);
		
		JLabel avatar = new JLabel("\r\n");
		avatar.setIcon(new ImageIcon("C:\\Users\\YaoJian\\Downloads\\avatar.png"));
		avatar.setBounds(28, 36, 154, 150);
		add(avatar);
		
		JLabel lblPleaseSpellWord = new JLabel("Please spell word 1 of 8\r\n");
		lblPleaseSpellWord.setHorizontalAlignment(SwingConstants.LEFT);
		lblPleaseSpellWord.setFont(new Font("Arial Narrow", Font.PLAIN, 24));
		lblPleaseSpellWord.setBounds(213, 29, 286, 45);
		add(lblPleaseSpellWord);
		
		JLabel lblNewLabel = new JLabel("Definition:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(213, 75, 86, 14);
		add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(213, 98, 286, 64);
		add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setBackground(Color.LIGHT_GRAY);
		
		textField = new JTextField();
		textField.setBounds(213, 173, 286, 20);
		add(textField);
		textField.setColumns(10);
		
		JButton btnListenAgain = new JButton("Listen again");
		btnListenAgain.setBounds(213, 204, 168, 31);
		add(btnListenAgain);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConfirm.setBounds(391, 204, 108, 31);
		add(btnConfirm);
		
		JLabel lblYouOnlyHave = new JLabel("You only have 2 attempts");
		lblYouOnlyHave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblYouOnlyHave.setBounds(213, 256, 258, 14);
		add(lblYouOnlyHave);
		
		JLabel lblstAttempt = new JLabel("1st attempt");
		lblstAttempt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblstAttempt.setBounds(213, 281, 86, 20);
		add(lblstAttempt);
		
		JLabel lblndAttempt = new JLabel("2nd attempt");
		lblndAttempt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblndAttempt.setBounds(213, 302, 86, 20);
		add(lblndAttempt);
		
		JLabel lblPlaceholder = new JLabel(": placeholder");
		lblPlaceholder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlaceholder.setBounds(309, 281, 86, 20);
		add(lblPlaceholder);
		
		JLabel label = new JLabel(": placeholder");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(309, 302, 86, 20);
		add(label);
		
		JLabel lblNewLabel_1 = new JLabel("Correct !");
		lblNewLabel_1.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_1.setForeground(Color.MAGENTA);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial Narrow", Font.PLAIN, 22));
		lblNewLabel_1.setBounds(21, 212, 182, 23);
		add(lblNewLabel_1);
		
		JLabel lblIncorrect = new JLabel("Incorrect");
		lblIncorrect.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIncorrect.setBounds(405, 281, 86, 20);
		add(lblIncorrect);
		
		JLabel lblCorrect = new JLabel("Correct\r\n");
		lblCorrect.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCorrect.setBounds(405, 302, 86, 20);
		add(lblCorrect);
		
		JButton btnStop = new JButton("Stop\r\n");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.changeCardPanel("Main");
			}
		});
		btnStop.setBounds(58, 253, 108, 31);
		add(btnStop);
		
		JLabel lblCurrentQuiz = new JLabel("Current Quiz ");
		lblCurrentQuiz.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCurrentQuiz.setBounds(517, 73, 77, 14);
		add(lblCurrentQuiz);
		
		JLabel lblCurrentStreak = new JLabel("Current Streak");
		lblCurrentStreak.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCurrentStreak.setBounds(517, 98, 96, 14);
		add(lblCurrentStreak);
		
		JLabel lblLongeststreak = new JLabel("Longest Streak\r\n");
		lblLongeststreak.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLongeststreak.setBounds(517, 122, 96, 14);
		add(lblLongeststreak);
		
		JLabel lblSpelledCorrectly = new JLabel("Spelled Correctly");
		lblSpelledCorrectly.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSpelledCorrectly.setBounds(517, 147, 108, 14);
		add(lblSpelledCorrectly);
		
		JLabel lblLevelAccuracy = new JLabel("Level Accuracy\r\n");
		lblLevelAccuracy.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLevelAccuracy.setBounds(517, 172, 96, 14);
		add(lblLevelAccuracy);
		
		JLabel lblNzcerLvl = new JLabel(": NZCER Lvl 1");
		lblNzcerLvl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNzcerLvl.setBounds(623, 73, 127, 14);
		add(lblNzcerLvl);
		
		JLabel label_2 = new JLabel(": 2");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_2.setBounds(623, 98, 127, 14);
		add(label_2);
		
		JLabel label_3 = new JLabel(": 5");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_3.setBounds(623, 122, 127, 14);
		add(label_3);
		
		JLabel label_4 = new JLabel(": 7/8");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_4.setBounds(623, 147, 127, 14);
		add(label_4);
		
		JLabel label_5 = new JLabel(": 77%");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_5.setBounds(623, 172, 127, 14);
		add(label_5);

	}
}
