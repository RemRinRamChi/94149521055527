package spelling.settings;

import javax.swing.JPanel;

import spelling.SpellingAidMain;
import spelling.VoiceGenerator.Voice;
import spelling.quiz.SpellList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
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
		lblSettings.setFont(new Font("Arial", Font.PLAIN, 24));
		lblSettings.setBounds(42, 30, 146, 52);
		add(lblSettings);
		
		JLabel lblNewLabel = new JLabel("Current Name :");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel.setBounds(42, 136, 112, 14);
		add(lblNewLabel);
		
		lblName = new JLabel("name");
		lblName.setFont(new Font("Arial", Font.PLAIN, 14));
		lblName.setBounds(144, 136, 104, 14);
		add(lblName);
		
		JButton btnNewButton = new JButton("change");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.changeCardPanel("Welcome");
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 12));
		btnNewButton.setBounds(258, 132, 133, 23);
		add(btnNewButton);
		
		JLabel lblClearStatistics = new JLabel("Clear Statistics");
		lblClearStatistics.setFont(new Font("Arial", Font.PLAIN, 14));
		lblClearStatistics.setBounds(42, 380, 206, 14);
		add(lblClearStatistics);
		
		JButton btnClear = new JButton("clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int userChoice = JOptionPane.showConfirmDialog (mainFrame, "All progress will be lost. (Continue?)","Warning",JOptionPane.WARNING_MESSAGE);
				if(userChoice == JOptionPane.YES_OPTION){
					//CLEAR STATS info dialog
					JOptionPane.showMessageDialog(mainFrame, ClearStatistics.clearStats(), "VoxSpell Statistics Cleared", JOptionPane.INFORMATION_MESSAGE);
					mainFrame.updateSpellingList(new SpellList());
				}
			}
		});
		btnClear.setFont(new Font("Arial", Font.PLAIN, 12));
		btnClear.setBounds(258, 376, 133, 23);
		add(btnClear);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.changeCardPanel("Main");
			}
		});
		btnBack.setFont(new Font("Arial", Font.PLAIN, 14));
		btnBack.setBounds(295, 462, 96, 36);
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
		comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Default", "Auckland"}));
		comboBox.setBounds(258, 166, 133, 23);
		add(comboBox);
		
		JLabel lblChangeVoice = new JLabel("VoxSpell Voice");
		lblChangeVoice.setFont(new Font("Arial", Font.PLAIN, 14));
		lblChangeVoice.setBounds(42, 170, 112, 14);
		add(lblChangeVoice);
		
		JLabel lblClearPreferences = new JLabel("Clear Preferences");
		lblClearPreferences.setFont(new Font("Arial", Font.PLAIN, 14));
		lblClearPreferences.setBounds(42, 414, 205, 14);
		add(lblClearPreferences);
		
		JButton button = new JButton("clear");
		button.setFont(new Font("Arial", Font.PLAIN, 12));
		button.setBounds(258, 410, 133, 23);
		add(button);
		
		JLabel lblSetOwnReward = new JLabel("Own reward video");
		lblSetOwnReward.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSetOwnReward.setBounds(42, 204, 205, 14);
		add(lblSetOwnReward);
		
		JButton btnSet = new JButton("set");
		btnSet.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSet.setBounds(258, 200, 133, 23);
		add(btnSet);
		
		JLabel lblSetOwnCheer = new JLabel("Own cheer 1");
		lblSetOwnCheer.setToolTipText("Cheering when user gets 100% right");
		lblSetOwnCheer.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSetOwnCheer.setBounds(42, 240, 205, 14);
		add(lblSetOwnCheer);
		
		JButton button_1 = new JButton("set");
		button_1.setFont(new Font("Arial", Font.PLAIN, 12));
		button_1.setBounds(258, 236, 133, 23);
		add(button_1);
		
		JLabel lblSetOwnNot = new JLabel("Own cheer 2");
		lblSetOwnNot.setToolTipText("Cheering when user gets less than 100% right");
		lblSetOwnNot.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSetOwnNot.setBounds(42, 274, 205, 14);
		add(lblSetOwnNot);
		
		JButton button_2 = new JButton("set");
		button_2.setFont(new Font("Arial", Font.PLAIN, 12));
		button_2.setBounds(258, 270, 133, 23);
		add(button_2);
		
		JLabel lblChange = new JLabel("Set own preferences");
		lblChange.setFont(new Font("Arial", Font.BOLD, 13));
		lblChange.setBounds(42, 92, 349, 23);
		add(lblChange);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(42, 117, 349, 8);
		add(separator);
		
		JLabel lblClear = new JLabel("Clear settings");
		lblClear.setFont(new Font("Arial", Font.BOLD, 13));
		lblClear.setBounds(42, 336, 349, 23);
		add(lblClear);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(42, 361, 349, 8);
		add(separator_1);
		
		JLabel lblBackgroundColour = new JLabel("Background colour");
		lblBackgroundColour.setFont(new Font("Arial", Font.PLAIN, 14));
		lblBackgroundColour.setBounds(42, 306, 205, 14);
		add(lblBackgroundColour);
		
		JButton btnChange = new JButton("change");
		btnChange.setFont(new Font("Arial", Font.PLAIN, 12));
		btnChange.setBounds(258, 302, 133, 23);
		add(btnChange);

	}
	
	/**
	 * Set user name to be displayed as the current user name
	 * @param name current user name
	 */
	public void setUserName(String name){
		lblName.setText(name);
	}
}
