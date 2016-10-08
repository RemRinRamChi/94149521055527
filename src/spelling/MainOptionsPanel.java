package spelling;

import javax.swing.JPanel;
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

	/**
	 * Create the panel.
	 */
	public MainOptionsPanel() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 11, 450, 200);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\YaoJian\\Downloads\\avatar.png"));
		lblNewLabel.setBounds(24, 11, 159, 184);
		panel.add(lblNewLabel);
		
		JLabel lblHiSherlock = new JLabel("Hi Sherlock");
		lblHiSherlock.setFont(new Font("Asiago", Font.PLAIN, 40));
		lblHiSherlock.setBounds(193, 49, 235, 64);
		panel.add(lblHiSherlock);
		
		JLabel lblNewLabel_1 = new JLabel("I am here to help you \r\n");
		lblNewLabel_1.setFont(new Font("Asiago", Font.PLAIN, 22));
		lblNewLabel_1.setBounds(193, 113, 247, 53);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 222, 430, 267);
		add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{100, 250, 100, 0};
		gbl_panel_1.rowHeights = new int[]{0, 20, 40, 40, 40, 40, 40, 40, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JButton btnNewButton = new JButton("New Quiz");
		btnNewButton.setFocusPainted(false);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 0;
		panel_1.add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblPleaseSelectOne = new JLabel("Please select one of the following options");
		lblPleaseSelectOne.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPleaseSelectOne = new GridBagConstraints();
		gbc_lblPleaseSelectOne.insets = new Insets(0, 0, 5, 5);
		gbc_lblPleaseSelectOne.gridx = 1;
		gbc_lblPleaseSelectOne.gridy = 1;
		panel_1.add(lblPleaseSelectOne, gbc_lblPleaseSelectOne);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		panel_1.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnReviewQuiz = new JButton("Review Quiz\r\n");
		btnReviewQuiz.setFocusPainted(false);
		btnReviewQuiz.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnReviewQuiz = new GridBagConstraints();
		gbc_btnReviewQuiz.fill = GridBagConstraints.BOTH;
		gbc_btnReviewQuiz.insets = new Insets(0, 0, 5, 5);
		gbc_btnReviewQuiz.gridx = 1;
		gbc_btnReviewQuiz.gridy = 3;
		panel_1.add(btnReviewQuiz, gbc_btnReviewQuiz);
		
		JButton btnViewStatistics = new JButton("View Statistics");
		btnViewStatistics.setFocusPainted(false);
		btnViewStatistics.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnViewStatistics = new GridBagConstraints();
		gbc_btnViewStatistics.fill = GridBagConstraints.BOTH;
		gbc_btnViewStatistics.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewStatistics.gridx = 1;
		gbc_btnViewStatistics.gridy = 4;
		panel_1.add(btnViewStatistics, gbc_btnViewStatistics);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.setFocusPainted(false);
		btnSettings.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.fill = GridBagConstraints.BOTH;
		gbc_btnSettings.insets = new Insets(0, 0, 5, 5);
		gbc_btnSettings.gridx = 1;
		gbc_btnSettings.gridy = 5;
		panel_1.add(btnSettings, gbc_btnSettings);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setFocusPainted(false);
		btnQuit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnQuit = new GridBagConstraints();
		gbc_btnQuit.fill = GridBagConstraints.BOTH;
		gbc_btnQuit.insets = new Insets(0, 0, 5, 5);
		gbc_btnQuit.gridx = 1;
		gbc_btnQuit.gridy = 6;
		panel_1.add(btnQuit, gbc_btnQuit);

	}
}
