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

	// 10 different youtube videos and their titles
	private final String[] youtubeVideos = new String[]{
			"https://r2---sn-uo1-53as.googlevideo.com/videoplayback?ratebypass=yes&itag=22&requiressl=yes&ipbits=0&upn=oUc4JgaBYKQ&ip=115.188.46.114&ei=TfcJWJecAaW_4AKb27WIBg&pl=26&mt=1477048070&mv=m&ms=au&source=youtube&signature=9B50D03A5F38D8F8E2B81F96127ED8CD070D2388.D0CB980A5690D953FD2CC3D7A67EB2936CDCEFB8&initcwndbps=1836250&mm=31&mn=sn-uo1-53as&id=o-ANoV_Pw2mZELkuZ498YnaVo_shhQT7w9OpqWHksT48Yu&expire=1477069741&mime=video%2Fmp4&key=yt6&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&lmt=1476886918474566&dur=300.117",
			"https://r6---sn-uo1-53as.googlevideo.com/videoplayback?ei=wPcJWJ-vK9eW4gLqgI6wAg&requiressl=yes&itag=22&id=o-ADqmJzC-bIxN3FH9U6FNJxg00skwbfeEaoiI2yRZ7WK2&dur=295.055&mm=31&mn=sn-uo1-53as&ip=115.188.46.114&ms=au&mt=1477048070&source=youtube&lmt=1475685352909807&expire=1477069856&ratebypass=yes&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&upn=yJ0m3voz-Ck&mime=video%2Fmp4&key=yt6&signature=84C2EB5D2B2F9C3377A49DB61E9E215C89016D68.38C3F0BFD4C28BB11063D2A40E8C39FC5ED05863&mv=m&initcwndbps=1836250&ipbits=0&pl=26",
			"https://r6---sn-uo1-53as.googlevideo.com/videoplayback?pl=26&initcwndbps=1836250&dur=248.430&source=youtube&ratebypass=yes&requiressl=yes&id=o-AFoif1KG5YxHXXPKE9v7wCUTEsVloU464cz4sP2397SG&ip=115.188.46.114&ei=HPgJWPmSKsq84gLGhZywCg&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&signature=E14607098256DDEB56AECF225BB2EDBDE0B4FC4C.486DFFDAC5EF7D62A21B610E15784A0DB2719CF7&ms=au&mv=m&mt=1477048070&mn=sn-uo1-53as&mm=31&upn=wacqidi5CYA&key=yt6&mime=video%2Fmp4&expire=1477069948&lmt=1471936219610944&ipbits=0&itag=22",
			"https://r2---sn-uo1-53as.googlevideo.com/videoplayback?mm=31&pl=26&mn=sn-uo1-53as&id=o-AGtUMm48hZRH1SaWIE79JDTb71Hci8QkLE4VP8Yn9PX0&dur=289.692&requiressl=yes&ip=115.188.46.114&mt=1477048358&mv=m&ms=au&ei=Y_gJWKHRAZC-4AKKtp-oCg&upn=8hIj9PUVGy0&mime=video%2Fmp4&ratebypass=yes&key=yt6&itag=22&lmt=1472379327211546&source=youtube&initcwndbps=1816250&signature=D54184135701A39002A86AE01B13883F744D2EF4.B9CD3C21DC8996EB54F9BF9B030434B53FF5D732&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&ipbits=0&expire=1477070019",
			"https://r2---sn-uo1-53as.googlevideo.com/videoplayback?ratebypass=yes&signature=4131EAF5F3F982F403FBA1BB6F199191838DDC03.3838B9EE1A5186F22154F9889B9E445BB12BE961&ei=wfkJWNKYLsie4ALU2L_IDQ&itag=22&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&initcwndbps=1837500&id=o-AM8iqhiCrRt71j6pHEPQXBxZHlafuOdEB-ZCKC9NPvtd&ipbits=0&mm=31&mn=sn-uo1-53as&ms=au&mt=1477048644&requiressl=yes&mv=m&dur=226.487&ip=115.188.46.114&lmt=1473111560299708&upn=XWSDsBGO_08&expire=1477070369&key=yt6&source=youtube&mime=video%2Fmp4&pl=26",
			"https://r2---sn-uo1-53as.googlevideo.com/videoplayback?signature=34387A7A1F5194D3464EAD5086D26E7932AC6877.977B59133CAF2B06A3F45CDA5687CAEB02DF515D&ipbits=0&itag=22&upn=pyJ6iwfjOz0&dur=342.656&mime=video%2Fmp4&expire=1477070598&ms=au&ei=pvoJWJrhCor84AKTgoLICw&id=o-AEizJMX2JSXQYRJT74NOxHT52OqwqQemT4nMN2phruqL&pl=26&mv=m&mt=1477048933&mn=sn-uo1-53as&mm=31&source=youtube&ratebypass=yes&ip=115.188.46.114&requiressl=yes&lmt=1472032778782346&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&initcwndbps=1857500&key=yt6",
			"https://r4---sn-uo1-53az.googlevideo.com/videoplayback?requiressl=yes&ratebypass=yes&pl=26&expire=1477070701&initcwndbps=1832500&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&id=o-AA9wzfQ1XNww8WYrGao2i-XYmgpQYXsmi1neIRgVi9lJ&ei=DfsJWLXVLsG84ALEyJnoDQ&ip=115.188.46.114&ms=au&mv=m&mt=1477048933&dur=282.168&upn=NMiX39_XF5o&mn=sn-uo1-53az&source=youtube&mm=31&signature=A3BCED5F8EAFCF1B1F2902DE5D77B0C326479ECB.BB00A8FEF57419FF18F5C939FE067F1324D7F9DB&lmt=1471634401924889&ipbits=0&itag=22&key=yt6&mime=video%2Fmp4",
			"https://r2---sn-uo1-53az.googlevideo.com/videoplayback?requiressl=yes&ratebypass=yes&pl=26&expire=1477070758&initcwndbps=1832500&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&id=o-AI3CxESIe7sWgvqU4cHFOSXM1foXz4oID57g1n34TZ5i&ei=RvsJWMuLJcG84AKyhZi4Aw&ip=115.188.46.114&ms=au&mv=m&mt=1477048933&dur=256.232&upn=GMYYQ64EXxE&mn=sn-uo1-53az&source=youtube&mm=31&signature=7E6679EF2D93DC4B949321B14916A8123E60F08A.75EF66CCE1002D6261BCA4F46356DB8251994100&lmt=1472305903167852&ipbits=0&itag=22&key=yt6&mime=video%2Fmp4",
			"https://r5---sn-uo1-53as.googlevideo.com/videoplayback?initcwndbps=1857500&source=youtube&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&pl=26&upn=No4fePPDyhg&mm=31&ipbits=0&mime=video%2Fmp4&requiressl=yes&ratebypass=yes&mv=m&mt=1477048933&ms=au&dur=275.133&mn=sn-uo1-53as&signature=AD714CAFE19C995B46DE8F5EF964C28E00F5CF45.B2FE29329E185D674C3BCB5A2B0A4FEC1B3243B5&key=yt6&itag=22&expire=1477070852&id=o-ADXcj2AEQYImj1HriufWwkmDSaNcxKRL7K954D0IyGsK&ei=pPsJWJ_jA8LK4gL_iKPYDw&lmt=1471950721149712&ip=115.188.46.114",
			"https://r3---sn-uo1-53az.googlevideo.com/videoplayback?expire=1477071126&ipbits=0&ratebypass=yes&initcwndbps=1826250&upn=ftfJVQg0B84&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&dur=248.058&source=youtube&id=o-AM7_ufH_5mzD58cMpGxGfNIwqG51Bmk9MaQxL0K5b9Mc&requiressl=yes&itag=22&lmt=1471236520964382&ip=115.188.46.114&mime=video%2Fmp4&signature=4A2CAAB33554742898FED6C7661D81901B11AEDB.6E06A58E92C5BCC891D3AF0114D63FA9956F060E&ei=tvwJWPTMH8Ww4ALDtqvAAg&ms=au&mt=1477049226&mv=m&pl=26&mm=31&mn=sn-uo1-53az&key=yt6"			
	};
	private final String[] videoTitles = new String[]{
			"What causes cavities? - Mel Rosenberg",
			"Could we survive prolonged space travel? - Lisa Nip",
			"Are spotty fruits and vegetables safe to eat? - Elizabeth Brauer",
			"Why do we hiccup? - John Cameron",
			"How playing sports benefits your body ... and your brain - Leah Lagos and Jaspal Ricky Singh",
			"What does it mean to be a refugee? - Benedetta Berti and Evelien Borgman",
			"How stress affects your body - Sharon Horesh Bergquist",
			"How does a jellyfish sting? - Neosha S Kashef",
			"How do vaccines work? - Kelwalin Dhanasarnsombut",
			"Unravel - Tokyo Ghoul OP [piano]"
	};
	
	// video to play if internet connection doesn't exist
	private final String bunnyVideo = "";
	
	private final JPanel contentPanel = new JPanel();
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private JPanel buttonPane;
	final EmbeddedMediaPlayer video;
	private JButton pauseBtn;
	private JButton muteBtn;
	private JLabel timeLbl;
	private JButton stopBtn;

	/**
	 * Create the dialog.
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
		
		new NativeDiscovery().discover();
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        video = mediaPlayerComponent.getMediaPlayer();
        video.setPlaySubItems(true);
		contentPanel.add(mediaPlayerComponent);
	
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
	
	public void play(){
		Random rand = new Random(); //TODO this needs to happen outside
		int  randomVid = rand.nextInt(9);
		setTitle(videoTitles[randomVid]);
        video.playMedia(youtubeVideos[randomVid]);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==pauseBtn){
			video.pause();
			//Toggle text of button to play/pause
			if (pauseBtn.getText().equals("PLAY")){
				pauseBtn.setText("PAUSE");
			}
			else {
				pauseBtn.setText("PLAY");
			}
		} else if(e.getSource()==muteBtn){
			video.mute();
			//Toggle text of button to mute/unmute
			if (muteBtn.getText().equals("MUTE")){
				muteBtn.setText("UNMUTE");
			}
			else {
				muteBtn.setText("MUTE");
			}
		} else if(e.getSource()==stopBtn){
			if(video.isMute()){
				video.mute();
			}
			video.stop();
			dispose();
		}
	}



	public void windowClosing(WindowEvent e) {
		if(video.isMute()){
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
