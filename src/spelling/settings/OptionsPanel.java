package spelling.settings;

import javax.swing.JPanel;

import spelling.SpellingAidMain;
import spelling.VoiceGenerator.Voice;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
/**
 * This is the GUI for the options panel
 * @author yyap601
 *
 */
public class OptionsPanel extends JPanel {
	private SpellingAidMain mainFrame;
	private JLabel lblName;
	
	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public OptionsPanel(SpellingAidMain contentFrame){
		this();
		mainFrame = contentFrame;
	}
	/**
	 * Create the panel.
	 */
	public OptionsPanel() {
		setLayout(null);
		
		JLabel lblSettings = new JLabel("Options");
		lblSettings.setFont(new Font("Arial", Font.PLAIN, 26));
		lblSettings.setBounds(42, 24, 146, 41);
		add(lblSettings);
		
		JLabel lblNewLabel = new JLabel("Current Name :");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel.setBounds(42, 89, 112, 14);
		add(lblNewLabel);
		
		lblName = new JLabel("name");
		lblName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblName.setBounds(156, 76, 213, 41);
		add(lblName);
		
		JButton btnNewButton = new JButton("Change Name");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.changeCardPanel("Welcome");
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton.setBounds(42, 114, 133, 35);
		add(btnNewButton);
		
		JLabel lblClearStatistics = new JLabel("Clear Statistics");
		lblClearStatistics.setFont(new Font("Arial", Font.PLAIN, 16));
		lblClearStatistics.setBounds(42, 166, 112, 14);
		add(lblClearStatistics);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//CLEAR STATS info dialog
				JOptionPane.showMessageDialog(mainFrame, ClearStatistics.clearStats(), "VoxSpell Statistics Cleared", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnClear.setFont(new Font("Arial", Font.PLAIN, 14));
		btnClear.setBounds(42, 191, 133, 35);
		add(btnClear);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.changeCardPanel("Main");
			}
		});
		btnBack.setFont(new Font("Arial", Font.PLAIN, 18));
		btnBack.setBounds(265, 278, 104, 41);
		add(btnBack);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedItem().toString().equals("Default")){
					mainFrame.setVoice("Default");
				} else if (comboBox.getSelectedItem().toString().equals("Auckland")){
					mainFrame.setVoice("Auckland");
				}
			}
		});
		comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Default", "Auckland"}));
		comboBox.setBounds(223, 192, 146, 34);
		add(comboBox);
		
		JLabel lblChangeVoice = new JLabel("Change Voice");
		lblChangeVoice.setFont(new Font("Arial", Font.PLAIN, 16));
		lblChangeVoice.setBounds(223, 166, 112, 14);
		add(lblChangeVoice);

	}
	
	/**
	 * Set user name to be displayed as the current user name
	 * @param name current user name
	 */
	public void setUserName(String name){
		lblName.setText(name);
	}
}
