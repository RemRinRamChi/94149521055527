package spelling;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * 
 * This class creates the main window that opens when the user chooses
 * to view a video reward, whether it is the special video or not.
 * @authors yyap601 hchu167
 *
 */
@SuppressWarnings("serial")
public class VideoPlayer extends JWindow{
	//Main embedded vlc player
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
	//Integer store to allow toggling of play/pause button
	public int pp = 0;

	//Main constructor of MediaPlayer
	public VideoPlayer() {
		final JWindow frame = new JWindow();
		
		frame.setAlwaysOnTop(true);
		final EmbeddedMediaPlayer video = mediaPlayerComponent.getMediaPlayer();
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(mediaPlayerComponent, BorderLayout.CENTER); //add video to centre of window

		frame.setContentPane(panel);
		FlowLayout options = new FlowLayout();
		final JPanel tabs = new JPanel(); //set video functionality on top of window
		tabs.setLayout(options);
		options.setAlignment(FlowLayout.TRAILING);


		//Make a mute button that stops playing sound of video
		JButton btnMute = new JButton("MUTE");
		btnMute.setPreferredSize(new Dimension(204, 30));
		tabs.add(btnMute);
		btnMute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				video.mute();
			}
		});

		//Make a play/pause button that starts/stops playing the video graphics
		final JButton btnPause = new JButton("PAUSE");
		btnPause.setPreferredSize(new Dimension(204, 30));
		tabs.add(btnPause);
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				video.pause();
				//Toggle text of button to play/pause
				if (pp % 2 == 0){
					btnPause.setText("PLAY");
				}
				else {
					btnPause.setText("PAUSE");
				}
				pp++;
			}
		});

		//Make a rewind button that skips back in the video
		JButton btnRewind = new JButton("REWIND");
		btnRewind.setPreferredSize(new Dimension(204, 30));
		tabs.add(btnRewind);
		btnRewind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				video.skip(-2500);
			}
		});

		//Make a fast forward button that skips forward in the video
		JButton btnFastForward = new JButton("FAST FORWARD");
		btnFastForward.setPreferredSize(new Dimension(204, 30));
		tabs.add(btnFastForward);
		btnFastForward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				video.skip(2500);
			}
		});

		//Make a close button to close the video
		JButton btnClose = new JButton("CLOSE");
		btnClose.setPreferredSize(new Dimension(204, 30));
		tabs.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ~~~ weird stuff
				if(video.isMute()){
					video.mute();
				}
				video.stop();
				frame.setVisible(false);
			}
		});

		//Set dimensions of window
		frame.setLocation(100, 100);
		frame.setSize(1050, 600);
		frame.setVisible(true);
		frame.add(tabs, BorderLayout.SOUTH);

		
		video.playMedia("big_buck_bunny_1_minute.avi");
	
	}
}