package spelling;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import spelling.quiz.QuizDone;
import spelling.quiz.QuizQuestion;
import spelling.settings.OptionsPanel;
import spelling.statistics.SpellingAidStats;

import java.awt.CardLayout;
import java.awt.Dimension;
/**
 * This is the main frame with a panel of card layout which switches between different GUIs
 * @author yyap601
 *
 */
public class SpellingAidMain extends JFrame {

	private JPanel contentPane;
	private WelcomeScreen welcomeScreen;
	private MainOptionsPanel mainOptions;
	private QuizQuestion quizQuestion;
	private SpellingAidStats voxSpellStats;
	private QuizDone doneQuizQuestion;
	private OptionsPanel optionsPanel;

	public SpellingAidStats getVoxSpellStats(){
		return voxSpellStats;
	}
	
	public QuizQuestion getQuizQuestion() {
		return quizQuestion;
	}
	
	public QuizDone getDonePanel() {
		return doneQuizQuestion;
	}

	/**
	 * Change panel displayed within main frame
	 * @param mode The state VoxSpell is currently in.
	 */
	public void changeCardPanel(String mode){
		// change panel to display according to mode 
		((CardLayout) contentPane.getLayout()).show(contentPane, mode);
		// change size of main VoxSpell frame depending on the size of the panel contained within (width+20,height+40) 
		if(mode.equals("Welcome")){
			welcomeScreen.clearField();
			setSize(470,490);
		} else if (mode.equals("Main")){
			mainOptions.setUserName(getUserName());
			setSize(470,540);
		} else if (mode.equals("Quiz")){
			setSize(780,400);
		} else if (mode.equals("Done")){
			setSize(610,430);
		} else if (mode.equals("Stats")){
			voxSpellStats.setUserName(getUserName());
			setSize(490,640);
		} else if (mode.equals("Settings")){
			optionsPanel.setUserName(getUserName());
			setSize(470,380);
		}
		
		// DOESN'T work on Linux
		// make sure to recentre main frame
		//this.setLocationRelativeTo(null);
		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	    try 
	    { 
	    	// change look and feel on VoxSpell to windows L+F -- for Linux
	    	UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); 
	    } 
	    catch(Exception e){ 
	        try {
		    	// change look and feel on VoxSpell to windows L+F -- for Windows
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// set main frame's content pane to be of card layout to take in panels to switch between them easily 
		contentPane.setLayout(new CardLayout(0, 0));
		
		// initialize panels corresponding to different states of VoxSpell
		welcomeScreen = new WelcomeScreen(this);
		mainOptions = new MainOptionsPanel(this);
		doneQuizQuestion = new QuizDone(this);
		quizQuestion = new QuizQuestion(this,doneQuizQuestion);
		voxSpellStats= new SpellingAidStats(this);
		optionsPanel = new OptionsPanel(this);
		
		// add panels corresponding to different states of VoxSpell into content pane
		contentPane.add(welcomeScreen,"Welcome");
		contentPane.add(mainOptions, "Main");
		contentPane.add(quizQuestion, "Quiz");
		contentPane.add(doneQuizQuestion, "Done");
		contentPane.add(voxSpellStats, "Stats");
		contentPane.add(optionsPanel, "Settings");


		// check for the presence of the hidden statistic files that are required
		// CALLED AFTER ADDING PANELS TO DECIDE THE PANEL TO DISPLAY
		makeSureAllNecessaryFilesArePresent();
		
		// set location here
		setLocationRelativeTo(null);
	}
	
	// checks that all the files that are storing the statistics are present and create any files that do not exist
	private void makeSureAllNecessaryFilesArePresent() {
		File spelling_aid_failed = new File(".spelling_aid_failed");
		File spelling_aid_statistics = new File(".spelling_aid_statistics");
		File spelling_aid_tried_words = new File(".spelling_aid_tried_words");
		File spelling_aid_accuracy = new File(".spelling_aid_accuracy");
		File spelling_aid_user = new File(".spelling_aid_user");

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
			if(! spelling_aid_user.exists()){
				spelling_aid_user.createNewFile();
				// first panel to be displayed is the welcome screen
				changeCardPanel("Welcome");
			} else {
				// already gotten user's name
				changeCardPanel("Main");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// create special video with a background swingworker thread when the app starts
		//VideoCreator createSpecialVideo = new VideoCreator();
		//createSpecialVideo.execute();
	}
	
	// might want to change this
	private static String getUserName(){
		String name = "";
		File spelling_aid_user = new File(".spelling_aid_user");
		BufferedReader readUserName;
		try {
			readUserName = new BufferedReader(new FileReader(spelling_aid_user));
			name = readUserName.readLine();
			readUserName.close();
		} catch (Exception e) { //dangerous Exception
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}

	public void setVoice(String voice) {
		quizQuestion.setFestivalVoice(voice);
	}

}
