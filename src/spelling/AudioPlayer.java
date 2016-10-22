package spelling;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingWorker;

public class AudioPlayer extends SwingWorker<Void, Void>{
	public enum AudioReward{AllCorrect,NotAllCorrect}; //TODO
	private AudioReward rewardType;
	
	public AudioPlayer(AudioReward type){
		rewardType = type;
	}
	
	protected Void doInBackground() throws Exception {
		if(rewardType.equals(AudioReward.AllCorrect)){
			Tools.processStarter("mplayer mp3/test.mp3");		
		} else if(rewardType.equals(AudioReward.NotAllCorrect)){
			Tools.processStarter("mplayer mp3/test.mp3");		
		} 
		return null;
	}
	
	/**
	 * 
	 * @param type reward type either cheer 1 or 2 depending on user's performance
	 * @return AudioPlater SwingWorker
	 */
	public static AudioPlayer getAudioPlayer(AudioReward type){
		return new AudioPlayer(type);
	}
	
	/**
	 * Set cheer 1 for when user gets 100% right
	 * @param path path to the cheer 1 file
	 */
	public static void setCheer1(String path){
		// TODO need what format??
	}
	
	/**
	 * Set cheer 2 for when user gets less than 100% right
	 * @param path path to the cheer 2 file
	 */
	public static void setCheer2(String path){
		
	}
	
	/**
	 *  make sure cheer file is present to check if user has set any preferred cheering
	 */
	private void makeSureCheerFileIsPresent() {
		File cheerFile = new File(".spelling_aid_cheer");
		try{
			if(! cheerFile.exists()){
				cheerFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	
}
