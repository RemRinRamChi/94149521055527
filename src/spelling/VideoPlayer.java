package spelling;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.SwingConstants;

public class VideoPlayer extends JDialog implements ActionListener,WindowListener{

	// 5 different videos
	private final String bigBuckBunnyVideo = "vids/bigbucknarrated.mp4";
	
	private final JPanel contentPanel = new JPanel();
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private JPanel buttonPane;
	final EmbeddedMediaPlayer video;
	private JButton pauseBtn;
	private JButton muteBtn;
	private JLabel timeLbl;
	private JButton stopBtn;

	/**
	 * Create the video player dialog.
	 */
	public VideoPlayer() {
		setResizable(false);
		addWindowListener(this);
		setTitle("Video Reward");
		setBounds(100, 100, 675, 470);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 669, 390);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			buttonPane = new JPanel();
			buttonPane.setBounds(0, 390, 669, 54);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				pauseBtn = new JButton("PAUSE");
				pauseBtn.setBounds(178, 11, 150, 30);
				pauseBtn.setActionCommand("");
				pauseBtn.addActionListener(this);
				buttonPane.add(pauseBtn);
				getRootPane().setDefaultButton(pauseBtn);
			}
			
			muteBtn = new JButton("MUTE");
			muteBtn.setActionCommand("Cancel");
			muteBtn.setBounds(338, 11, 150, 30);
			muteBtn.addActionListener(this);
			buttonPane.add(muteBtn);
			
			stopBtn = new JButton("STOP");
			stopBtn.setActionCommand("Cancel");
			stopBtn.setBounds(498, 11, 150, 30);
			stopBtn.addActionListener(this);
			buttonPane.add(stopBtn);
			
			timeLbl = new JLabel("TIME");
			timeLbl.setHorizontalAlignment(SwingConstants.LEFT);
			timeLbl.setFont(new Font("Arial", Font.PLAIN, 13));
			timeLbl.setBounds(29, 12, 150, 27);
			buttonPane.add(timeLbl);
		}
		
		// help vlcj discover the vlc media player installed 
		new NativeDiscovery().discover();
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        video = mediaPlayerComponent.getMediaPlayer();
        video.setPlaySubItems(true); // enable it to play youtube sub items
		contentPanel.add(mediaPlayerComponent); // add it to the content panel
	
		// timer to show video time passed
        Timer timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				long time = (long)(video.getTime()/1000.0);
				timeLbl.setText("Seconds passed: "+String.valueOf(time));
			}
		});
        timer.start();
		
		setVisible(true);
	}
	
	// play video
	public void play(){
        video.playMedia(bigBuckBunnyVideo);
	}

	public void actionPerformed(ActionEvent e) {
		// PAUSE/PLAY
		if(e.getSource()==pauseBtn){
			video.pause();
			//Toggle text of button to play/pause
			if (pauseBtn.getText().equals("PLAY")){
				pauseBtn.setText("PAUSE");
			}
			else {
				pauseBtn.setText("PLAY");
			}
		// MUTE/UNMUTE
		} else if(e.getSource()==muteBtn){
			video.mute();
			//Toggle text of button to mute/unmute
			if (muteBtn.getText().equals("MUTE")){
				muteBtn.setText("UNMUTE");
			}
			else {
				muteBtn.setText("MUTE");
			}
		// STOP VIDEO
		} else if(e.getSource()==stopBtn){
			if(video.isMute()){
				video.mute();
			}
			video.stop();
			dispose();
		}
	}

	// STOP VIDEO AND DISPOSE DIALOG WHEN CLOSED
	public void windowClosing(WindowEvent e) {
		if(video.isMute()){ // to prevent possible muted behaviour of next video play
			video.mute();
		}
		video.stop();
		dispose();
	}
	
	// no specific actions required
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}
