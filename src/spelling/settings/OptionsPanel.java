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
	private JButton btnChangeName;
	private JButton btnClearStats;
	private JButton btnBack;
	private JButton btnClearPref;
	private JButton btnCheer2;
	private JButton btnCheer1;
	private JComboBox themeComboBox;
	private JComboBox voiceComboBox;
	
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
		
		btnChangeName = new JButton("change");
		btnChangeName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.changeCardPanel("Welcome");
			}
		});
		btnChangeName.setFont(new Font("Arial", Font.PLAIN, 12));
		btnChangeName.setBounds(258, 132, 133, 23);
		add(btnChangeName);
		
		JLabel lblClearStatistics = new JLabel("Clear Statistics");
		lblClearStatistics.setFont(new Font("Arial", Font.PLAIN, 14));
		lblClearStatistics.setBounds(42, 350, 206, 14);
		add(lblClearStatistics);
		
		btnClearStats = new JButton("clear");
		btnClearStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int userChoice = JOptionPane.showConfirmDialog (mainFrame, "All progress will be lost. (Continue?)","Warning",JOptionPane.WARNING_MESSAGE);
				if(userChoice == JOptionPane.YES_OPTION){
					//CLEAR STATS info dialog
					JOptionPane.showMessageDialog(mainFrame, ClearStatistics.clearStats(), "VoxSpell Statistics Cleared", JOptionPane.INFORMATION_MESSAGE);
					mainFrame.updateSpellingList(new SpellList());
				}
			}
		});
		btnClearStats.setFont(new Font("Arial", Font.PLAIN, 12));
		btnClearStats.setBounds(258, 346, 133, 23);
		add(btnClearStats);
		
		btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.changeCardPanel("Main");
			}
		});
		btnBack.setFont(new Font("Arial", Font.PLAIN, 14));
		btnBack.setBounds(296, 442, 96, 36);
		add(btnBack);
		
		voiceComboBox = new JComboBox();
		voiceComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(voiceComboBox.getSelectedItem().toString().equals("Default")){
					mainFrame.setVoice("Default");
				} else if (voiceComboBox.getSelectedItem().toString().equals("Auckland")){
					mainFrame.setVoice("Auckland");
				}
			}
		});
		voiceComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		voiceComboBox.setModel(new DefaultComboBoxModel(new String[] {"Default", "Auckland"}));
		voiceComboBox.setBounds(258, 166, 133, 23);
		add(voiceComboBox);
		
		JLabel lblChangeVoice = new JLabel("VoxSpell Voice");
		lblChangeVoice.setFont(new Font("Arial", Font.PLAIN, 14));
		lblChangeVoice.setBounds(42, 170, 112, 14);
		add(lblChangeVoice);
		
		JLabel lblClearPreferences = new JLabel("Clear Preferences");
		lblClearPreferences.setFont(new Font("Arial", Font.PLAIN, 14));
		lblClearPreferences.setBounds(42, 384, 205, 14);
		add(lblClearPreferences);
		
		btnClearPref = new JButton("clear");
		btnClearPref.setFont(new Font("Arial", Font.PLAIN, 12));
		btnClearPref.setBounds(258, 380, 133, 23);
		add(btnClearPref);
		
		JLabel lblSetOwnCheer1 = new JLabel("Own cheer 1");
		lblSetOwnCheer1.setToolTipText("Cheering when user gets 100% right");
		lblSetOwnCheer1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSetOwnCheer1.setBounds(42, 242, 205, 14);
		add(lblSetOwnCheer1);
		
		btnCheer1 = new JButton("set");
		btnCheer1.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCheer1.setBounds(258, 238, 133, 23);
		add(btnCheer1);
		
		JLabel lblSetOwnCheer2 = new JLabel("Own cheer 2");
		lblSetOwnCheer2.setToolTipText("Cheering when user gets less than 100% right");
		lblSetOwnCheer2.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSetOwnCheer2.setBounds(42, 276, 205, 14);
		add(lblSetOwnCheer2);
		
		btnCheer2 = new JButton("set");
		btnCheer2.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCheer2.setBounds(258, 272, 133, 23);
		add(btnCheer2);
		
		JLabel lblSetPref = new JLabel("Set own preferences");
		lblSetPref.setFont(new Font("Arial", Font.BOLD, 13));
		lblSetPref.setBounds(42, 92, 349, 23);
		add(lblSetPref);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(42, 117, 349, 8);
		add(separator);
		
		JLabel lblClearSettings = new JLabel("Clear settings");
		lblClearSettings.setFont(new Font("Arial", Font.BOLD, 13));
		lblClearSettings.setBounds(42, 306, 349, 23);
		add(lblClearSettings);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(42, 331, 349, 8);
		add(separator_1);
		
		JLabel lblBackgroundColour = new JLabel("VoxSpell Theme");
		lblBackgroundColour.setFont(new Font("Arial", Font.PLAIN, 14));
		lblBackgroundColour.setBounds(42, 204, 205, 14);
		add(lblBackgroundColour);
		
		themeComboBox = new JComboBox();
		themeComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		themeComboBox.setModel(new DefaultComboBoxModel(new String[] {"Light", "Dark"}));
		themeComboBox.setBounds(258, 200, 133, 23);
		add(themeComboBox);

	}
	
	/**
	 * Set user name to be displayed as the current user name
	 * @param name current user name
	 */
	public void setUserName(String name){
		lblName.setText(name);
	}
}
