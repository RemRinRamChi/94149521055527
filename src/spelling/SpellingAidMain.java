package spelling;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import spelling.quiz.QuizQuestion;

import java.awt.CardLayout;

public class SpellingAidMain extends JFrame {

	private JPanel contentPane;
	private JPanel welcomeScreen;
	private JPanel mainOptions;
	private JPanel quizQuestion;


	/**
	 * Change panel displayed within main frame
	 * @param mode The state VoxSpell is currently in.
	 */
	public void changeCardPanel(String mode){
		// change panel to display according to mode 
		((CardLayout) contentPane.getLayout()).show(contentPane, mode);
		// change size of main VoxSpell frame depending on the size of the panel contained within (width+20,height+40) 
		if(mode.equals("Welcome")){
			setSize(470,490);
		} else if (mode.equals("Main")){
			setSize(470,540);
		} else if (mode.equals("Quiz")){
			setSize(780,400);
		} else if (mode.equals("Done")){
			setSize(780,400);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	    try 
	    { 
	    	// change look and feel on VoxSpell to windows L+F
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
	    } 
	    catch(Exception e){ 
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
		quizQuestion = new QuizQuestion(this);
		
		// add panels corresponding to different states of VoxSpell into content pane
		contentPane.add(welcomeScreen,"Welcome");
		contentPane.add(mainOptions, "Main");
		contentPane.add(quizQuestion, "Quiz");

		
		// first panel to be displayed is the welcome screen
		changeCardPanel("Welcome");
	}

}
