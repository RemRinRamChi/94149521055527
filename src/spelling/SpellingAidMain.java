package spelling;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.Dimension;

public class SpellingAidMain extends JFrame {

	private JPanel contentPane;
	private JPanel welcomeScreen;
	private JPanel mainOptions;

	public void changeCardPanel(String mode){
		((CardLayout) contentPane.getLayout()).show(contentPane, mode);
		if(mode.equals("Welcome")){
			setSize(470,490);
		} else if (mode.equals("Main")){
			setSize(470,540);
		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	    try 
	    { 
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
		contentPane.setLayout(new CardLayout(0, 0));
		welcomeScreen = new WelcomeScreen(this);
		mainOptions = new MainOptionsPanel(this);
		
		contentPane.add(welcomeScreen,"Welcome");
		changeCardPanel("Welcome");
		contentPane.add(mainOptions, "Main");
	}

}
