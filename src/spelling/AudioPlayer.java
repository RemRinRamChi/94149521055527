package spelling;

import javax.swing.SwingWorker;

public class AudioPlayer extends SwingWorker<Void, Void>{
	public enum AudioReward{AllCorrect,NotAllCorrect}; //TODO
	
	protected Void doInBackground() throws Exception {
		Tools.processStarter("ffplay test.mp3");		
		return null;
	}
	
	public static AudioPlayer audioPlayerGetter(){
		return new AudioPlayer();
	}
	
}
