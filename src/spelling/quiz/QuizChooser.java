package spelling.quiz;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import spelling.SpellingAidMain;
import spelling.quiz.SpellList.QuizMode;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Color;
/**
 * This is the dialog to choose the quiz
 * @author yyap601
 *
 */
public class QuizChooser extends JDialog implements ActionListener {
	private SpellingAidMain mainFrame;
	private QuizQuestion mainQuizPanel;
	private QuizMode theMode;
	private JFileChooser ownListChooser = new JFileChooser();
	private JButton chooseFile;
	private JLabel lblChosenList;

	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public QuizChooser(SpellingAidMain contentFrame, QuizQuestion quizPanel, QuizMode mode){
		this();
		mainFrame = contentFrame;
		mainQuizPanel = quizPanel;
		theMode = mode;
	}

	/**
	 * Create the dialog and its components.
	 */
	public QuizChooser() {
		// Make sure that the file chooser only accepts (*.txt) files
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES (*.txt)", "txt", "text");
		ownListChooser.setFileFilter(filter);
		ownListChooser.setAcceptAllFileFilterUsed(false);

		// create all components and lay them out properly
		createAndLayoutComponents();
		
		// centre dialog
		setLocationRelativeTo(null);
	}

	// perform appropriate actions based on button press
	public void actionPerformed(ActionEvent e) {
		// if choose file button is pressed
		if(e.getSource()==chooseFile){
			int returnVal = ownListChooser.showDialog(this, "Choose list");
			 if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = ownListChooser.getSelectedFile();
		            // Change chosen list label approprately
		            lblChosenList.setText("Chosen list: " + file.getName());
		            // TODO
		        } 
		} else { // when a level from the default list was chosen
			dispose();
			mainFrame.changeCardPanel("Quiz");
			String chosenButton = ((JButton) e.getSource()).getText();
			if(chosenButton.equals("?")){
				// get a random level from 1 to 11 if the random button was chosen
				Random rand = new Random();
				int  randomLvl = rand.nextInt(11);
				chosenButton = randomLvl+"";
			}
			mainQuizPanel.startQuiz(chosenButton,theMode);
		}
	}
	
	// create all components and lay them out properly
	private void createAndLayoutComponents(){
		// Set layout and title of QuizChooser dialog
				getContentPane().setFont(new Font("Arial", Font.PLAIN, 14));
				setTitle("Choose Quiz");
				setName("dialog216");
				setModal(true);
				setBounds(100, 100, 375, 395);
				getContentPane().setLayout(null);

				// Create title "Choose NZCER spelling list level" 
				JLabel lblNZCERLevel = new JLabel("Choose NZCER spelling list level\r\n");
				lblNZCERLevel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNZCERLevel.setFont(new Font("Arial", Font.PLAIN, 22));
				lblNZCERLevel.setBounds(0, 11, 359, 58);
				getContentPane().add(lblNZCERLevel);

				// Create level 1 button
				JButton btn1 = new JButton("1");
				btn1.setFont(new Font("Arial", Font.PLAIN, 11));
				btn1.addActionListener(this);
				btn1.setFocusPainted(false);
				btn1.setBounds(32, 70, 49, 48);
				getContentPane().add(btn1);

				// Create level 2 button
				JButton btn2 = new JButton("2");
				btn2.addActionListener(this);
				btn2.setFont(new Font("Arial", Font.PLAIN, 11));
				btn2.setFocusPainted(false);
				btn2.setBounds(81, 70, 49, 48);
				getContentPane().add(btn2);
				
				// Create level 3 button
				JButton btn3 = new JButton("3");
				btn3.addActionListener(this);
				btn3.setFont(new Font("Arial", Font.PLAIN, 11));
				btn3.setFocusPainted(false);
				btn3.setBounds(130, 70, 49, 48);
				getContentPane().add(btn3);
				
				// Create level 4 button
				JButton btn4 = new JButton("4");
				btn4.addActionListener(this);
				btn4.setFont(new Font("Arial", Font.PLAIN, 11));
				btn4.setFocusPainted(false);
				btn4.setBounds(179, 70, 49, 48);
				getContentPane().add(btn4);
				
				// Create level 5 button
				JButton btn5 = new JButton("5");
				btn5.addActionListener(this);
				btn5.setFont(new Font("Arial", Font.PLAIN, 11));
				btn5.setFocusPainted(false);
				btn5.setBounds(228, 70, 49, 48);
				getContentPane().add(btn5);
				
				// Create level 6 button
				JButton btn6 = new JButton("6");
				btn6.addActionListener(this);
				btn6.setFont(new Font("Arial", Font.PLAIN, 11));
				btn6.setFocusPainted(false);
				btn6.setBounds(277, 70, 49, 48);
				getContentPane().add(btn6);
				
				// Create level 7 button
				JButton btn7 = new JButton("7");
				btn7.addActionListener(this);
				btn7.setFont(new Font("Arial", Font.PLAIN, 11));
				btn7.setFocusPainted(false);
				btn7.setBounds(32, 120, 49, 48);
				getContentPane().add(btn7);
				
				// Create level 8 button
				JButton btn8 = new JButton("8");
				btn8.addActionListener(this);
				btn8.setFont(new Font("Arial", Font.PLAIN, 11));
				btn8.setFocusPainted(false);
				btn8.setBounds(81, 120, 49, 48);
				getContentPane().add(btn8);
				
				// Create level 9 button
				JButton btn9 = new JButton("9");
				btn9.addActionListener(this);
				btn9.setFont(new Font("Arial", Font.PLAIN, 11));
				btn9.setFocusPainted(false);
				btn9.setBounds(130, 120, 49, 48);
				getContentPane().add(btn9);
				
				// Create level 10 button
				JButton btn10 = new JButton("10");
				btn10.addActionListener(this);
				btn10.setFont(new Font("Arial", Font.PLAIN, 11));
				btn10.setFocusPainted(false);
				btn10.setBounds(179, 120, 49, 48);
				getContentPane().add(btn10);
				
				// Create level 11 button
				JButton btn11 = new JButton("11");
				btn11.addActionListener(this);
				btn11.setFont(new Font("Arial", Font.PLAIN, 11));
				btn11.setFocusPainted(false);
				btn11.setBounds(228, 120, 49, 48);
				getContentPane().add(btn11);
				
				// Create RANDOM level button
				JButton btnR = new JButton("?");
				btnR.addActionListener(this);
				btnR.setFont(new Font("Arial", Font.PLAIN, 11));
				btnR.setFocusPainted(false);
				btnR.setBounds(277, 120, 49, 48);
				getContentPane().add(btnR);
				
				// Create separator between default list and own list
				JLabel label = new JLabel("___________________________________________________");
				label.setForeground(Color.LIGHT_GRAY);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setBounds(0, 164, 359, 29);
				getContentPane().add(label);
				
				// Create title "Use own list" 
				JLabel lblUseOwnList = new JLabel("Use own list");
				lblUseOwnList.setHorizontalAlignment(SwingConstants.CENTER);
				lblUseOwnList.setFont(new Font("Arial", Font.PLAIN, 22));
				lblUseOwnList.setBounds(0, 194, 359, 35);
				getContentPane().add(lblUseOwnList);

				// Create combo box to have levels to choose from user's own list
				JComboBox ownListComboBox = new JComboBox();
				ownListComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
				ownListComboBox.setBounds(32, 261, 202, 29);
				getContentPane().add(ownListComboBox);

				// Add button that opens up a file chooser to allow user to choose own list
				chooseFile = new JButton("...");
				chooseFile.addActionListener(this);
				chooseFile.setToolTipText("this should open up a file chooser to choose the user's own list which overwrites the last user list");
				chooseFile.setFont(new Font("Arial", Font.PLAIN, 11));
				chooseFile.setFocusPainted(false);
				chooseFile.setBounds(240, 261, 35, 29);
				getContentPane().add(chooseFile);

				// Create "OK" button to confirm level from user's own list
				JButton btnConfirmLvl = new JButton("OK");
				btnConfirmLvl.setFont(new Font("Arial", Font.PLAIN, 11));
				btnConfirmLvl.setFocusPainted(false);
				btnConfirmLvl.setBounds(277, 261, 49, 29);
				getContentPane().add(btnConfirmLvl);

				// If user doesn't want to take a quiz
				JButton btnCancel = new JButton("Cancel");
				btnCancel.setFont(new Font("Arial", Font.PLAIN, 11));
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btnCancel.setFocusPainted(false);
				btnCancel.setBounds(130, 301, 98, 29);
				getContentPane().add(btnCancel);



				
				lblChosenList = new JLabel("Chosen list: no list chosen\r\n");
				lblChosenList.setHorizontalAlignment(SwingConstants.CENTER);
				lblChosenList.setFont(new Font("Arial", Font.PLAIN, 14));
				lblChosenList.setBounds(10, 227, 339, 29);
				getContentPane().add(lblChosenList);
	}
}
