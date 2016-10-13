package spelling;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextField;

import spelling.settings.ClearStatistics;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.awt.event.ActionEvent;
/**
 * This is the GUI to welcome the user if it is the user's first time or when the user decides the change names
 * @author yyap601
 *
 */
public class WelcomeScreen extends JPanel {
	private JTextField nameField;
	private SpellingAidMain mainFrame;
	private JButton btnConfirm;
	
	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public WelcomeScreen(SpellingAidMain contentFrame){
		this();
		mainFrame = contentFrame;
	}
	/**
	 * Create the panel and components for the WelcomeScreen.
	 */
	public WelcomeScreen() {
		setLayout(null);
		
		//Welcome image
		JLabel welcomeImage = new JLabel("");
		welcomeImage.setBounds(0, 11, 450, 200);
		welcomeImage.setIcon(new ImageIcon("img/welcome.png"));
		add(welcomeImage);
		
		//Set welcome screen to be of grid bag layout
		JPanel panel = new JPanel();
		panel.setBounds(10, 210, 430, 229);
		add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{50, 330, 50, 0};
		gbl_panel.rowHeights = new int[]{80, 80, 32, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		//Label asking what the user want to be called
		JLabel nameQuery = new JLabel("What would you like to be called ?");
		nameQuery.setFont(new Font("Arial", Font.PLAIN, 18));
		GridBagConstraints gbc_nameQuery = new GridBagConstraints();
		gbc_nameQuery.anchor = GridBagConstraints.SOUTH;
		gbc_nameQuery.insets = new Insets(0, 0, 5, 5);
		gbc_nameQuery.gridx = 1;
		gbc_nameQuery.gridy = 0;
		panel.add(nameQuery, gbc_nameQuery);
		
		//Text field to let the user type in his/her name
		nameField = new JTextField();
		nameField.setToolTipText("(e.g. Sherlock)");
		nameField.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.insets = new Insets(0, 0, 5, 5);
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.gridx = 1;
		gbc_nameField.gridy = 1;
		panel.add(nameField, gbc_nameField);
		nameField.setColumns(10);
		nameField.addKeyListener(new KeyListener(){
			//listen to ENTER key to click the confirm button
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					btnConfirm.doClick();
				}
			}
			public void keyTyped(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
			}
		});
		
		// confirm name button
		btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("Arial", Font.PLAIN, 12));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(nameField.getText().equals("")){
					JOptionPane.showMessageDialog(mainFrame, "Please enter your name (e.g. Sherlock)", "What is your name?", JOptionPane.ERROR_MESSAGE);
					nameField.requestFocus();
				} else {
					storeUserName(nameField.getText());
					mainFrame.changeCardPanel("Main");
				}
			}
		});
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.fill = GridBagConstraints.VERTICAL;
		gbc_btnConfirm.insets = new Insets(0, 0, 0, 5);
		gbc_btnConfirm.anchor = GridBagConstraints.EAST;
		gbc_btnConfirm.gridx = 1;
		gbc_btnConfirm.gridy = 2;
		panel.add(btnConfirm, gbc_btnConfirm);

		nameField.requestFocus();
	}
	
	//store confirmed user name in a file after clearing the last name 
	private void storeUserName(String name){
		File spelling_aid_user = new File(".spelling_aid_user");
		ClearStatistics.clearFile(spelling_aid_user);
		Tools.record(spelling_aid_user, name);
	}
	
	//clear the name field, for when the user is changing names 
	public void clearField(){
		nameField.setText("");
		nameField.requestFocus();
	}
	
	
}
