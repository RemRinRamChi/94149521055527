package spelling.quiz;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import spelling.SpellingAidMain;
import spelling.Tools;
import spelling.quiz.SpellList.QuizMode;
import spelling.settings.ClearFiles;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Color;
/**
 * This is the dialog to choose the quiz to be initiated, it also allows the user to choose own list
 * @author yyap601
 *
 */
public class QuizChooser extends JDialog implements ActionListener{
	private SpellingAidMain mainFrame;
	private Quiz mainQuizPanel;
	private QuizMode theMode;
	private JFileChooser ownListChooser = new JFileChooser();
	private JButton chooseFile;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton btn5;
	private JButton btn6;
	private JButton btn7;
	private JButton btn8;
	private JButton btn9;
	private JButton btn10;
	private JButton btn11;
	private JButton btnR;
	private JButton btnCancel;
	private JButton btnConfirmLvl;
	private JComboBox ownListComboBox;
	private JLabel lblNZCERLevel;
	private JLabel lblUseOwnList;

	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public QuizChooser(SpellingAidMain contentFrame, Quiz quizPanel, QuizMode mode){
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

		// populate the combo box
		DefaultComboBoxModel combo = new DefaultComboBoxModel(Tools.getLevelValues(new File(".USER-spelling-lists.txt")));
		ownListComboBox.setModel(combo);
		applyTheme();
		// centre dialog
		setLocationRelativeTo(null);
	}
	
	/**
	 * Apply colour to components
	 */
	public void applyTheme(){
		Color backgroundColour = new Color(250,250,250);
		Color bannerColour = new Color(250,250,250);
		Color buttonText = new Color(255,255,255);
		Color normalText = new Color(0,0,0);
		Color buttonColour = new Color(15,169,249);
		
		// background color
		this.setBackground(backgroundColour);
		getContentPane().setBackground(backgroundColour);
		
		// normal text
		lblNZCERLevel.setForeground(normalText);
		lblUseOwnList.setForeground(normalText);
		
		// button text
		chooseFile.setForeground(buttonText);
		btn1.setForeground(buttonText);
		btn2.setForeground(buttonText);
		btn3.setForeground(buttonText);
		btn4.setForeground(buttonText);
		btn5.setForeground(buttonText);
		btn6.setForeground(buttonText);
		btn7.setForeground(buttonText);
		btn8.setForeground(buttonText);
		btn9.setForeground(buttonText);
		btn10.setForeground(buttonText);
		btn11.setForeground(buttonText);
		btnR.setForeground(buttonText);
		btnCancel.setForeground(buttonText);
		btnConfirmLvl.setForeground(buttonText);
		ownListComboBox.setForeground(buttonText);
		
		// normal button color
		chooseFile.setBackground(buttonColour);
		btn1.setBackground(buttonColour);
		btn2.setBackground(buttonColour);
		btn3.setBackground(buttonColour);
		btn4.setBackground(buttonColour);
		btn5.setBackground(buttonColour);
		btn6.setBackground(buttonColour);
		btn7.setBackground(buttonColour);
		btn8.setBackground(buttonColour);
		btn9.setBackground(buttonColour);
		btn10.setBackground(buttonColour);
		btn11.setBackground(buttonColour);
		btnR.setBackground(buttonColour);
		btnCancel.setBackground(buttonColour);
		btnConfirmLvl.setBackground(buttonColour);
		ownListComboBox.setBackground(buttonColour);
	}
	
	// perform appropriate actions based on button press
	public void actionPerformed(ActionEvent e) {
		// if choose file button is pressed
		if(e.getSource()==chooseFile){
			int returnVal = ownListChooser.showDialog(this, "Choose list");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File ownFile = ownListChooser.getSelectedFile();
				// Change chosen list label appropriately
				try {
					DefaultComboBoxModel combo = new DefaultComboBoxModel(addFromAFileToAnotherFileAndGetTitles(ownFile,new File(".USER-spelling-lists.txt")));
					ownListComboBox.setModel(combo);
					mainQuizPanel.updateSpellList(new SpellList());
				} catch(InvalidWordListException iWE){
					JOptionPane.showMessageDialog(this, iWE.getMessage(), "Invalid word list", JOptionPane.WARNING_MESSAGE);
				}
			} 
		} else if(e.getSource()==btnConfirmLvl){
			if(!(ownListComboBox.getModel().getSize()==0)){
				dispose();
				mainFrame.changeCardPanel("Quiz");
				String selection = (String) ownListComboBox.getSelectedItem();
				mainQuizPanel.startQuiz(selection,theMode);
			}
		} else { // when a level from the default list was chosen
			dispose();
			mainFrame.changeCardPanel("Quiz");
			String chosenButton = ((JButton) e.getSource()).getText();
			if(chosenButton.equals("?")){
				// get a random level from 1 to 11 if the random button was chosen
				Random rand = new Random();
				int  randomLvl = rand.nextInt(11)  + 1;
				chosenButton = randomLvl+"";
			}
			mainQuizPanel.startQuiz("Level "+chosenButton,theMode);
		}
	}

	/**
	 * Replace the contents of a file with another and returns the list of level names in a word list
	 * @param from file to get contents from
	 * @param to file to have its contents be replaced
	 * @throws InvalidWordListException 
	 */
	public static String[] addFromAFileToAnotherFileAndGetTitles(File from, File to) throws InvalidWordListException{
		boolean levelExist = false;
		ArrayList<String> returns = new ArrayList<String>(); // the level titles to return
		ArrayList<String> wordsToCopy = new ArrayList<String>(); // the words to copy from a file
		try {
			BufferedReader readFromList = new BufferedReader(new FileReader(from));
			String word = readFromList.readLine();
			if(word == null){
				throw new InvalidWordListException("Please make sure that the file is not empty.");
			}
			if(word.charAt(0)!='%'){
				throw new InvalidWordListException("First line of file has to be a level name with '%' in the front.");
			}
			while(word != null){
				if(word.charAt(0)=='%'){
					if(word.length()==1){
						throw new InvalidWordListException("All levels must have a name.");
					}
					if(!returns.contains(word.substring(1))){
						String trimmedWord = word.substring(1).trim();
						returns.add(trimmedWord);
					}
				}
				String trimmedWord = word.trim();
				wordsToCopy.add(trimmedWord);
				word = readFromList.readLine();
			}
			readFromList.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// return the titles of the list to be displayed in the combo box
		String[] ws = new String[returns.size()];
		int i = 0;
		for(String w : returns){
			ws[i] = w;
			i++;
		}

		ClearFiles.clearFile(to); // only start clearing when nothing is wrong
		// copy all the words from the selected file to the special user's own list file
		for(String word : wordsToCopy){
			Tools.record(to,word);
		}

		return ws;
	}
	/**
	 *  Create all components and lay them out properly
	 */
	private void createAndLayoutComponents(){
		// Set layout and title of QuizChooser dialog
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 14));
		setTitle("Choose Quiz");
		setName("dialog216");
		setModal(true);
		setBounds(100, 100, 375, 390);
		getContentPane().setLayout(null);

		// Create title "Choose NZCER spelling list level" 
		lblNZCERLevel = new JLabel("Choose NZCER spelling list level\r\n");
		lblNZCERLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNZCERLevel.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNZCERLevel.setBounds(0, 11, 359, 58);
		getContentPane().add(lblNZCERLevel);

		// Create level 1 button
		btn1 = new JButton("1");
		btn1.setFont(new Font("Arial", Font.BOLD, 14));
		btn1.addActionListener(this);
		btn1.setFocusPainted(false);
		btn1.setBounds(32, 70, 49, 48);
		getContentPane().add(btn1);

		// Create level 2 button
		btn2 = new JButton("2");
		btn2.addActionListener(this);
		btn2.setFont(new Font("Arial", Font.BOLD, 14));
		btn2.setFocusPainted(false);
		btn2.setBounds(81, 70, 49, 48);
		getContentPane().add(btn2);

		// Create level 3 button
		btn3 = new JButton("3");
		btn3.addActionListener(this);
		btn3.setFont(new Font("Arial", Font.BOLD, 14));
		btn3.setFocusPainted(false);
		btn3.setBounds(130, 70, 49, 48);
		getContentPane().add(btn3);

		// Create level 4 button
		btn4 = new JButton("4");
		btn4.addActionListener(this);
		btn4.setFont(new Font("Arial", Font.BOLD, 14));
		btn4.setFocusPainted(false);
		btn4.setBounds(179, 70, 49, 48);
		getContentPane().add(btn4);

		// Create level 5 button
		btn5 = new JButton("5");
		btn5.addActionListener(this);
		btn5.setFont(new Font("Arial", Font.BOLD, 14));
		btn5.setFocusPainted(false);
		btn5.setBounds(228, 70, 49, 48);
		getContentPane().add(btn5);

		// Create level 6 button
		btn6 = new JButton("6");
		btn6.addActionListener(this);
		btn6.setFont(new Font("Arial", Font.BOLD, 14));
		btn6.setFocusPainted(false);
		btn6.setBounds(277, 70, 49, 48);
		getContentPane().add(btn6);

		// Create level 7 button
		btn7 = new JButton("7");
		btn7.addActionListener(this);
		btn7.setFont(new Font("Arial", Font.BOLD, 14));
		btn7.setFocusPainted(false);
		btn7.setBounds(32, 120, 49, 48);
		getContentPane().add(btn7);

		// Create level 8 button
		btn8 = new JButton("8");
		btn8.addActionListener(this);
		btn8.setFont(new Font("Arial", Font.BOLD, 14));
		btn8.setFocusPainted(false);
		btn8.setBounds(81, 120, 49, 48);
		getContentPane().add(btn8);

		// Create level 9 button
		btn9 = new JButton("9");
		btn9.addActionListener(this);
		btn9.setFont(new Font("Arial", Font.BOLD, 14));
		btn9.setFocusPainted(false);
		btn9.setBounds(130, 120, 49, 48);
		getContentPane().add(btn9);

		// Create level 10 button
		btn10 = new JButton("10");
		btn10.addActionListener(this);
		btn10.setFont(new Font("Arial", Font.BOLD, 14));
		btn10.setFocusPainted(false);
		btn10.setBounds(179, 120, 49, 48);
		getContentPane().add(btn10);

		// Create level 11 button
		btn11 = new JButton("11");
		btn11.addActionListener(this);
		btn11.setFont(new Font("Arial", Font.BOLD, 14));
		btn11.setFocusPainted(false);
		btn11.setBounds(228, 120, 49, 48);
		getContentPane().add(btn11);

		// Create RANDOM level button
		btnR = new JButton("?");
		btnR.setToolTipText("Random Level");
		btnR.addActionListener(this);
		btnR.setFont(new Font("Arial", Font.BOLD, 14));//
		btnR.setFocusPainted(false);
		btnR.setBounds(277, 120, 49, 48);
		getContentPane().add(btnR);

		// Create separator between default list and own list
		JLabel label = new JLabel("________________________________________________");
		label.setFont(new Font("Arial", Font.PLAIN, 12));
		label.setForeground(Color.LIGHT_GRAY);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 164, 359, 29);
		getContentPane().add(label);

		// Create title "Use own list" 
		lblUseOwnList = new JLabel("Use own list");
		lblUseOwnList.setHorizontalAlignment(SwingConstants.CENTER);
		lblUseOwnList.setFont(new Font("Arial", Font.PLAIN, 20));
		lblUseOwnList.setBounds(0, 194, 359, 35);
		getContentPane().add(lblUseOwnList);

		// Create combo box to have levels to choose from user's own list
		ownListComboBox = new JComboBox();
		ownListComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		ownListComboBox.setBounds(32, 240, 202, 29);
		getContentPane().add(ownListComboBox);

		// Add button that opens up a file chooser to allow user to choose own list
		chooseFile = new JButton("...");
		chooseFile.addActionListener(this);
		chooseFile.setToolTipText("Choose own list to quiz on");
		chooseFile.setFont(new Font("Arial", Font.BOLD, 14));
		chooseFile.setFocusPainted(false);
		chooseFile.setBounds(240, 240, 35, 29);
		getContentPane().add(chooseFile);

		// Create "OK" button to confirm level from user's own list
		btnConfirmLvl = new JButton("OK");
		btnConfirmLvl.addActionListener(this);
		btnConfirmLvl.setFont(new Font("Arial", Font.BOLD, 14));
		btnConfirmLvl.setFocusPainted(false);
		btnConfirmLvl.setBounds(277, 240, 49, 29);
		getContentPane().add(btnConfirmLvl);

		// If user doesn't want to take a quiz
		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setFocusPainted(false);
		btnCancel.setBounds(130, 298, 98, 29);
		getContentPane().add(btnCancel);
	}

}
