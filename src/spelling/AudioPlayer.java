package spelling;

import javax.swing.SwingWorker;

public class AudioPlayer extends SwingWorker<Void, Void>{
	public enum AudioReward{AllCorrect,NotAllCorrect}; //TODO
	private AudioReward rewardType;
	
	public AudioPlayer(AudioReward type){
		rewardType = type;
	}
	
	protected Void doInBackground() throws Exception {
		if(rewardType.equals(AudioReward.AllCorrect)){
			Tools.processStarter("mplayer test.mp3");		
		} else if(rewardType.equals(AudioReward.NotAllCorrect)){
			Tools.processStarter("mplayer test.mp3");		
		} 
		return null;
	}
	
	public static AudioPlayer getAudioPlayer(AudioReward type){
		return new AudioPlayer(type);
	}
	
}
