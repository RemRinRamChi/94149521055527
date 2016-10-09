package spelling;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WelcomeScreen extends JPanel {
	private JTextField nameField;
	private SpellingAidMain mainFrame;
	
	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public WelcomeScreen(SpellingAidMain contentFrame){
		this();
		mainFrame = contentFrame;
	}
	/**
	 * Create the panel.
	 */
	public WelcomeScreen() {
		setLayout(null);
		
		JLabel welcomeImage = new JLabel("");
		welcomeImage.setBounds(0, 11, 450, 200);
		welcomeImage.setIcon(new ImageIcon("C:\\Users\\YaoJian\\Downloads\\welcome.png"));
		add(welcomeImage);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 210, 430, 229);
		add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{50, 330, 50, 0};
		gbl_panel.rowHeights = new int[]{80, 80, 28, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel nameQuery = new JLabel("What would you like to be called ?");
		nameQuery.setFont(new Font("Arial", Font.PLAIN, 18));
		GridBagConstraints gbc_nameQuery = new GridBagConstraints();
		gbc_nameQuery.anchor = GridBagConstraints.SOUTH;
		gbc_nameQuery.insets = new Insets(0, 0, 5, 5);
		gbc_nameQuery.gridx = 1;
		gbc_nameQuery.gridy = 0;
		panel.add(nameQuery, gbc_nameQuery);
		
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
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("Arial", Font.PLAIN, 11));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.changeCardPanel("Main");
			}
		});
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.fill = GridBagConstraints.VERTICAL;
		gbc_btnConfirm.insets = new Insets(0, 0, 0, 5);
		gbc_btnConfirm.anchor = GridBagConstraints.EAST;
		gbc_btnConfirm.gridx = 1;
		gbc_btnConfirm.gridy = 2;
		panel.add(btnConfirm, gbc_btnConfirm);

	}
}
