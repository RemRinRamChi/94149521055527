package spelling;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import spelling.quiz.QuizDone;
import spelling.quiz.SpellList;
import spelling.quiz.Quiz;
import spelling.settings.ClearFiles;
import spelling.settings.OptionsPanel;
import spelling.statistics.StatisticsViewController;

import java.awt.CardLayout;
import java.awt.Color;
/**
 * This is the VoxSpell main frame with a panel of card layout which switches between different states
 * , it handles the resizing of the main frame corresponding to different states (e.g. new quiz and statistics)
 * It also makes sure that all necessary files for the spelling aid are present.
 * @author yyap601
 *
 */
public class SpellingAidMain extends JFrame {

	private JPanel contentPane;
	private WelcomeScreen welcomeScreen;
	private MainOptionsPanel mainOptions;
	private Quiz quizQuestion;
	private StatisticsViewController voxSpellStats;
	private QuizDone doneQuizQuestion;
	private OptionsPanel optionsPanel;

	/**
	 * Change panel displayed within main frame
	 * @param mode The state VoxSpell is currently in.
	 */
	public void changeCardPanel(String mode){
		// change panel to display according to mode 
		((CardLayout) contentPane.getLayout()).show(contentPane, mode);
		// change size of main VoxSpell frame depending on the size of the panel contained within (width+16,height+40) 
		if(mode.equals("Welcome")){
			welcomeScreen.clearField();
			setSize(464,490);
			setTitle("Welcome To VOXSPELL");
		} else if (mode.equals("Main")){
			mainOptions.setUserName(getUserName());
			setSize(464,540);
			setTitle("Welcome To VOXSPELL");
		} else if (mode.equals("Quiz")){
			setSize(796,400);
			setTitle("VOXPSELL Quiz");
		} else if (mode.equals("Done")){
			setSize(606,540);
			setTitle("VOXPSELL Quiz Results for "+doneQuizQuestion.getLevel());
		} else if (mode.equals("Stats")){
			voxSpellStats.setUserName(getUserName());
			setSize(486,690);
			setTitle("VOXPSELL Statistics");
		} else if (mode.equals("Settings")){
			optionsPanel.setUserName(getUserName());
			setSize(456,525);
			setTitle("VOXPSELL Settings");
		}

		// DOESN'T work on Linux or maybe Java 1.7//TODO
		// make sure to recentre main frame
		this.setLocationRelativeTo(null);

	}

	/**
	 * Launch VOXSPELL.
	 */
	public static void main(String[] args) {
		try 
		{ 
			// change look and feel on VoxSpell to windows L+F -- for Linux
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); 
		} 
		catch(Exception e){ 
			try {
				// change look and feel on VoxSpell to nimbus L+F -- for Windows
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpellingAidMain frame = new SpellingAidMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SpellingAidMain() {

		setResizable(false);
		setTitle("Welcome To VOXSPELL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250,250,250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// set main frame's content pane to be of card layout to take in panels to switch between them easily 
		contentPane.setLayout(new CardLayout(0, 0));

		// check for the presence of the hidden statistic files that are required
		makeSureAllNecessaryFilesArePresent();

		// initialize panels corresponding to different states of VoxSpell
		welcomeScreen = new WelcomeScreen(this);
		mainOptions = new MainOptionsPanel(this);
		doneQuizQuestion = new QuizDone(this);
		quizQuestion = new Quiz(this,doneQuizQuestion);
		voxSpellStats= new StatisticsViewController(this);
		optionsPanel = new OptionsPanel(this);

		// add panels corresponding to different states of VoxSpell into content pane
		contentPane.add(welcomeScreen,"Welcome");
		contentPane.add(mainOptions, "Main");
		contentPane.add(quizQuestion, "Quiz");
		contentPane.add(doneQuizQuestion, "Done");
		contentPane.add(voxSpellStats, "Stats");
		contentPane.add(optionsPanel, "Settings");


		// CALLED AFTER ADDING PANELS TO DECIDE THE PANEL TO DISPLAY
		makeSureNameFileExists();

		applyPreferences();
		
		// set location here
		setLocationRelativeTo(null);
	}

	/**
	 *  checks that all the files that are storing the statistics are present and create any files that do not exist
	 */
	private void makeSureAllNecessaryFilesArePresent() {
		File spelling_aid_failed = new File(".spelling_aid_failed");
		File spelling_aid_statistics = new File(".spelling_aid_statistics");
		File spelling_aid_tried_words = new File(".spelling_aid_tried_words");
		File spelling_aid_accuracy = new File(".spelling_aid_accuracy");
		File spelling_aid_cheer = new File(".spelling_aid_cheer");
		File spelling_aid_other_prefs = new File(".spelling_aid_other_prefs");
		File userList = new File(".USER-spelling-lists.txt");

		try{
			if(! spelling_aid_failed.exists()){
				spelling_aid_failed.createNewFile();
			}
			if(! spelling_aid_statistics.exists()){
				spelling_aid_statistics.createNewFile();
			}
			if(! spelling_aid_tried_words.exists()){
				spelling_aid_tried_words.createNewFile();
			}
			if(! spelling_aid_accuracy.exists()){
				spelling_aid_accuracy.createNewFile();
			}
			if(! spelling_aid_cheer.exists()){
				spelling_aid_cheer.createNewFile();
			}
			if(! spelling_aid_other_prefs.exists()){
				spelling_aid_other_prefs.createNewFile();
			}
			if(! userList.exists()){
				userList.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void makeSureNameFileExists(){
		File spelling_aid_user = new File(".spelling_aid_user");
		try{
			if(! spelling_aid_user.exists()){
				spelling_aid_user.createNewFile();
				// first panel to be displayed is the welcome screen if there isn't a previous user name
				changeCardPanel("Welcome");
			} else {
				// go straight to main panel if user's name is already known
				changeCardPanel("Main");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// getters
	public StatisticsViewController getVoxSpellStats(){
		return voxSpellStats;
	}
	public Quiz getQuiz() {
		return quizQuestion;
	}
	public QuizDone getDonePanel() {
		return doneQuizQuestion;
	}
	/**
	 * Get user's name from the store user name
	 */
	private static String getUserName(){
		String name = "";
		File spelling_aid_user = new File(".spelling_aid_user");
		BufferedReader readUserName;
		try {
			readUserName = new BufferedReader(new FileReader(spelling_aid_user));
			name = readUserName.readLine();
			readUserName.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * This method applies user's preferences to VOXSpell every time it starts and also when the user changes it
	 */
	public void applyPreferences(){
		File prefFile = new File(".spelling_aid_other_prefs");
		String preferredVoice = "Default";
		try {
			// get the most recent setted preferences
			BufferedReader readPreferences = new BufferedReader(new FileReader(prefFile));
			String pref = readPreferences.readLine();
			while(pref != null){
				if(pref.charAt(0)=='v'){
					preferredVoice = pref.substring(1);
				} 
				pref = readPreferences.readLine();
			}
			readPreferences.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// tidy up the .spelling_aid_cheer to have only 2 cheer file paths
		ClearFiles.clearFile(prefFile);
		optionsPanel.setSelectedVoice(preferredVoice);
	}
	
	/**
	 * Change voice generator of VoxSpell
	 * @param voice festival's voice
	 */
	public void setVoice(String voice) {
		quizQuestion.setFestivalVoice(voice);
		Tools.record(new File(".spelling_aid_other_prefs"), "v"+voice);
	}


	/**
	 * Update spelling list
	 */
	public void updateSpellingList(SpellList sL){
		quizQuestion.updateSpellList(sL);
	}
}
