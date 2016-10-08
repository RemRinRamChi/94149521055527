package spelling.quiz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class QuizDone extends JPanel {

	/**
	 * Create the panel.
	 */
	public QuizDone() {
		setLayout(null);
		
		JLabel lblYourResults = new JLabel("Time for your results ...");
		lblYourResults.setFont(new Font("Arial Narrow", Font.PLAIN, 22));
		lblYourResults.setBounds(189, 31, 421, 37);
		add(lblYourResults);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(189, 79, 373, 113);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblOutOf = new JLabel("7 out of 8 correct !");
		lblOutOf.setBounds(0, 0, 373, 113);
		panel.add(lblOutOf);
		lblOutOf.setBackground(Color.LIGHT_GRAY);
		lblOutOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutOf.setFont(new Font("Arial Narrow", Font.PLAIN, 34));
		
		JLabel lblGoodJob = new JLabel("Good job! Please claim for reward");
		lblGoodJob.setFont(new Font("Arial Narrow", Font.PLAIN, 20));
		lblGoodJob.setBounds(93, 208, 421, 37);
		add(lblGoodJob);
		
		JButton btnTryAnotherLevel = new JButton("TRY ANOTHER LEVEL\r\n");
		btnTryAnotherLevel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnTryAnotherLevel.setBounds(93, 305, 421, 30);
		add(btnTryAnotherLevel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(93, 256, 421, 38);
		add(panel_1);
		panel_1.setLayout(null);
		
		JButton button = new JButton("Video Reward");
		button.setFont(new Font("Tahoma", Font.PLAIN, 13));
		button.setBounds(0, 0, 194, 28);
		panel_1.add(button);
		
		JButton button_1 = new JButton("Audio Reward");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		button_1.setBounds(227, 0, 194, 28);
		panel_1.add(button_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\YaoJian\\Downloads\\avatar.png"));
		lblNewLabel.setBounds(20, 42, 159, 150);
		add(lblNewLabel);

	}
}
