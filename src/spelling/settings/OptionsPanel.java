package spelling.settings;

import javax.swing.JPanel;

import spelling.AudioPlayer;
import spelling.SpellingAidMain;
import spelling.quiz.SpellList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * This is the GUI for the options panel which contains options to change name
 * , change cheering audio files, change festival voice and also clear settings
 * @author yyap601
 *
 */
public class OptionsPanel extends JPanel implements ActionListener{
	private JFileChooser cheeringFileChooser = new JFileChooser();
	private SpellingAidMain mainFrame;
	private JButton btnChangeName;
	private JButton btnClearStats;
	private JButton btnBack;
	private JButton btnClearPref;
	private JButton btnCheer2;
	private JButton btnCheer1;
	private JComboBox voiceComboBox;
	private JLabel lblName;
	private JLabel lblNewLabel;
	private JLabel lblChangeVoice;
	private JLabel lblSetOwnCheer1;
	private JLabel lblSetOwnCheer2;
	private JLabel lblSetPref;
	private JLabel lblClearSettings;
	private JLabel lblClearStatistics;
	private JLabel lblClearPreferences;
	private JLabel lblSettings;
	private JButton btnHelp;
	
	
	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public OptionsPanel(SpellingAidMain contentFrame){
		this();
		// Make sure that the file chooser only accepts (*.mp3) and (*.wav) files
		FileNameExtensionFilter filter = new FileNameExtensionFilter("AUDIO FILES (*.mp3)/(*.wav)", "mp3", "wav");
		cheeringFileChooser.setFileFilter(filter);
		cheeringFileChooser.setAcceptAllFileFilterUsed(false);
		
		mainFrame = contentFrame;
	}
	
	// perform appropriate actions based on button press
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnChangeName){ // CHANGE NAME
			mainFrame.changeCardPanel("Welcome");
		} else if(e.getSource()==voiceComboBox){ // CHANGE VOXSPELL VOICE
			if(voiceComboBox.getSelectedItem().toString().equals("Default")){
				mainFrame.setVoice("Default");
			} else if (voiceComboBox.getSelectedItem().toString().equals("Auckland")){ 
				mainFrame.setVoice("Auckland");
			}
		} else if(e.getSource()==btnCheer1 || e.getSource()==btnCheer2){ // SET CHEERING VOICE 
			int returnVal = cheeringFileChooser.showDialog(this, "Choose audio file");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File ownFile = cheeringFileChooser.getSelectedFile();
				if(! ownFile.exists()){ // if user choose to type in own file name for a file that doesn't exist
					JOptionPane.showMessageDialog(this, "Please make sure that the audio file exists.", "Invalid audio file", JOptionPane.WARNING_MESSAGE);
				} else {
					if(e.getSource()==btnCheer1){
						AudioPlayer.setCheer1(ownFile.getAbsolutePath());
					} else {
						AudioPlayer.setCheer2(ownFile.getAbsolutePath());
					}
				}
			} 
		} else if(e.getSource()==btnClearStats){ // CLEAR STATISTICS
			int userChoice = JOptionPane.showConfirmDialog (mainFrame, "All progress will be lost. (Continue?)","Warning",JOptionPane.WARNING_MESSAGE);
			if(userChoice == JOptionPane.YES_OPTION){
				//CLEAR STATS info dialog
				JOptionPane.showMessageDialog(mainFrame, ClearFiles.clearStats(), "VoxSpell Statistics Cleared", JOptionPane.INFORMATION_MESSAGE);
				mainFrame.updateSpellingList(new SpellList());
			}
		} else if(e.getSource()==btnClearPref){ // CLEAR PREFERENCES
			int userChoice = JOptionPane.showConfirmDialog (mainFrame, "All preferences will be resetted, this includes own cheering voice and own list, not current name. (Continue?)","Warning",JOptionPane.WARNING_MESSAGE);
			if(userChoice == JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(mainFrame, ClearFiles.clearPrefs(), "VoxSpell Preferences Cleared", JOptionPane.INFORMATION_MESSAGE);
				mainFrame.applyPreferences();
			}
		} else if(e.getSource()==btnBack){ // GO BACK TO MAIN OPTIONS
			mainFrame.changeCardPanel("Main");
		} else if(e.getSource()==btnHelp){ 
			JOptionPane.showMessageDialog(mainFrame, ""
					+ "New Quiz: Start a new spelling quiz (maximum of 10 words will be asked)\n"
					+ "Review Quiz: Review a spelling quiz and be asked on the questions that you have gotten wrong previously (maximum of 10 words will be asked)\n"
					+ "View Statistics: Show statistics on each level attempted and also statistics on each word attempted\n"
					+ "Options: Change or clear preferences (VoxSpell asking voice, VoxSpell cheering voice)\n"
					+ "\n"
					+ "Notes: cheer files can only be mp3 or wav files, user's own spelling list can only be text or txt files\n"
					+ "\n"
					+ "Please look at the USER_MANUAL.pdf file provided for more detailed help.", "VoxSpell Help", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
	}
	
	/**
	 * set selected festival voice
	 * @param selected
	 */
	public void setSelectedVoice(String selected){
		voiceComboBox.setSelectedItem(selected);
	}
	
	/**
	 * Create the options panel and lay out its components appropriately.
	 */
	public OptionsPanel() {
		setLayout(null);
		// Options label
		lblSettings = new JLabel("Options");
		lblSettings.setFont(new Font("Arial", Font.PLAIN, 24));
		lblSettings.setBounds(42, 30, 146, 52);
		add(lblSettings);
		// "Current Name :"
		lblNewLabel = new JLabel("Current Name :");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel.setBounds(42, 136, 112, 14);
		add(lblNewLabel);
		// label with user's currently selected name
		lblName = new JLabel("name");
		lblName.setFont(new Font("Arial", Font.PLAIN, 14));
		lblName.setBounds(144, 136, 104, 14);
		add(lblName);
		// Change NAME button
		btnChangeName = new JButton("change");
		btnChangeName.setToolTipText("Change your name, statistics will not be affected");
		btnChangeName.addActionListener(this);
		btnChangeName.setFont(new Font("Arial", Font.BOLD, 14));
		btnChangeName.setBounds(258, 132, 133, 23);
		add(btnChangeName);
		
		// CLEAR STATISTICS label and button
		lblClearStatistics = new JLabel("Clear Statistics");
		lblClearStatistics.setFont(new Font("Arial", Font.PLAIN, 14));
		lblClearStatistics.setBounds(42, 322, 206, 14);
		add(lblClearStatistics);
		btnClearStats = new JButton("clear");
		btnClearStats.setToolTipText("Clear sattistics/progress for all level");
		btnClearStats.addActionListener(this);
		btnClearStats.setFont(new Font("Arial", Font.BOLD, 14));
		btnClearStats.setBounds(258, 318, 133, 23);
		add(btnClearStats);
		
		// BACK button
		btnBack = new JButton("BACK");
		btnBack.addActionListener(this);
		btnBack.setFont(new Font("Arial", Font.BOLD, 14));
		btnBack.setBounds(295, 405, 96, 36);
		add(btnBack);
		
		// Change VOXSPELL VOICE combo box and label
		voiceComboBox = new JComboBox();
		voiceComboBox.addActionListener(this);
		voiceComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		voiceComboBox.setModel(new DefaultComboBoxModel(new String[] {"Default", "Auckland"}));
		voiceComboBox.setBounds(258, 166, 133, 23);
		add(voiceComboBox);
		lblChangeVoice = new JLabel("VoxSpell Voice");
		lblChangeVoice.setFont(new Font("Arial", Font.PLAIN, 14));
		lblChangeVoice.setBounds(42, 170, 112, 14);
		add(lblChangeVoice);
		
		// CLEAR PREFERENCES label and button
		lblClearPreferences = new JLabel("Clear Preferences");
		lblClearPreferences.setFont(new Font("Arial", Font.PLAIN, 14));
		lblClearPreferences.setBounds(42, 356, 205, 14);
		add(lblClearPreferences);
		btnClearPref = new JButton("clear");
		btnClearPref.setToolTipText("Clear own cheers and voice preference and also the own spelling list");
		btnClearPref.addActionListener(this);
		btnClearPref.setFont(new Font("Arial", Font.BOLD, 14));
		btnClearPref.setBounds(258, 352, 133, 23);
		add(btnClearPref);
		
		// Own cheer 1 label and button (when user gets 100% right)
		lblSetOwnCheer1 = new JLabel("Own cheer 1");
		lblSetOwnCheer1.setToolTipText("Cheering when user gets 100% right");
		lblSetOwnCheer1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSetOwnCheer1.setBounds(42, 204, 205, 14);
		add(lblSetOwnCheer1);
		btnCheer1 = new JButton("set");
		btnCheer1.setToolTipText("Set cheering sound for when you get at least one less then the total number of questions right");
		btnCheer1.addActionListener(this);
		btnCheer1.setFont(new Font("Arial", Font.BOLD, 14));
		btnCheer1.setBounds(258, 200, 133, 23);
		add(btnCheer1);
		
		// Own cheer 2 label and button (when user gets less than 100% right)
		lblSetOwnCheer2 = new JLabel("Own cheer 2");
		lblSetOwnCheer2.setToolTipText("Cheering when user gets less than 100% right");
		lblSetOwnCheer2.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSetOwnCheer2.setBounds(42, 238, 205, 14);
		add(lblSetOwnCheer2);
		btnCheer2 = new JButton("set");
		btnCheer2.setToolTipText("Set cheering sound for when you get less than at least one less then the total number of questions right");
		btnCheer2.addActionListener(this);
		btnCheer2.setFont(new Font("Arial", Font.BOLD, 14));
		btnCheer2.setBounds(258, 234, 133, 23);
		add(btnCheer2);
		
		// Set own preferences label
		lblSetPref = new JLabel("Set own preferences");
		lblSetPref.setFont(new Font("Arial", Font.BOLD, 14));
		lblSetPref.setBounds(42, 92, 349, 23);
		add(lblSetPref);
		JSeparator separator = new JSeparator();
		separator.setBounds(42, 117, 349, 8);
		add(separator);
		// Clear settings label
		lblClearSettings = new JLabel("Clear settings");
		lblClearSettings.setFont(new Font("Arial", Font.BOLD, 14));
		lblClearSettings.setBounds(42, 278, 349, 23);
		add(lblClearSettings);
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(42, 303, 349, 8);
		add(separator_1);
		// Help button
		btnHelp = new JButton("Help");
		btnHelp.setToolTipText("Please have a look at the USER_MANUAL.pdf provided");
		btnHelp.setForeground(Color.WHITE);
		btnHelp.setFont(new Font("Arial", Font.BOLD, 14));
		btnHelp.setBackground(new Color(15, 169, 249));
		btnHelp.setBounds(42, 405, 96, 36);
		btnHelp.addActionListener(this);
		add(btnHelp);
		
		applyTheme();

	}
	
	/**
	 * Apply colour to components
	 */
	public void applyTheme(){
		Color backgroundColour = new Color(255,255,255);
		Color buttonText = new Color(255,255,255);
		Color normalText = new Color(0,0,0);
		Color buttonColour = new Color(15,169,249);

		
		// background color
		this.setBackground(backgroundColour);
		
		// normal text
		lblName.setForeground(normalText);
		lblNewLabel.setForeground(normalText);
		lblChangeVoice.setForeground(normalText);
		lblSetOwnCheer1.setForeground(normalText);
		lblSetOwnCheer2.setForeground(normalText);
		lblSetPref.setForeground(normalText);
		lblClearSettings.setForeground(normalText);
		lblClearStatistics.setForeground(normalText);
		lblClearPreferences.setForeground(normalText);
		lblSettings.setForeground(normalText);
		
		// button text
		btnChangeName.setForeground(buttonText);
		btnClearStats.setForeground(buttonText);
		btnBack.setForeground(buttonText);
		btnClearPref.setForeground(buttonText);
		btnCheer2.setForeground(buttonText);
		btnCheer1.setForeground(buttonText);
		voiceComboBox.setForeground(buttonText);
		btnHelp.setForeground(buttonText);

		// normal button color
		btnChangeName.setBackground(buttonColour);
		btnClearStats.setBackground(buttonColour);
		btnBack.setBackground(buttonColour);
		btnClearPref.setBackground(buttonColour);
		btnCheer2.setBackground(buttonColour);
		btnCheer1.setBackground(buttonColour);
		voiceComboBox.setBackground(buttonColour);
		btnHelp.setBackground(buttonColour);
	}
	
	/**
	 * Set user name to be displayed as the current user name
	 * @param name current user name
	 */
	public void setUserName(String name){
		lblName.setText(name);
	}
}
