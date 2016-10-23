package spelling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.SwingWorker;

import spelling.settings.ClearFiles;
/**
 * AudioPlayer is a class which can play an audio file in a background thread
 * @author yyap601
 *
 */
public class AudioPlayer extends SwingWorker<Void, Void>{
	public enum AudioReward{AllCorrect,NotAllCorrect}; 
	private AudioReward rewardType;
	private String cheer1;
	private String cheer2;
	
	public AudioPlayer(AudioReward type){
		scanCheerFile();
		rewardType = type;
	}
	
	// run in backgrounf
	protected Void doInBackground() throws Exception {
		if(rewardType.equals(AudioReward.AllCorrect)){
			Tools.processStarter("mplayer "+cheer1);		
		} else if(rewardType.equals(AudioReward.NotAllCorrect)){
			Tools.processStarter("mplayer "+cheer2);		
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
		// TODO need what format?? for the file as well??hmmm mplayer^
		File spelling_aid_user = new File(".spelling_aid_cheer");
		Tools.record(spelling_aid_user,"1" + path);
	}
	
	/**
	 * Set cheer 2 for when user gets less than 100% right
	 * @param path path to the cheer 2 file
	 */
	public static void setCheer2(String path){
		File spelling_aid_user = new File(".spelling_aid_cheer");
		Tools.record(spelling_aid_user,"2" + path);
	}
	
	/**
	 * Scan the cheer file to know which audio files to play for cheering
	 */
	private void scanCheerFile() {
		File cheerFile = new File(".spelling_aid_cheer");
		// default cheer file
		cheer1 = "mp3/Reward.mp3";
		cheer2 = "mp3/GoodTry.mp3";
		try {
			// get the most recent setted cheer files
			BufferedReader readCheerPath = new BufferedReader(new FileReader(cheerFile));
			String path = readCheerPath.readLine();
			while(path != null){
				if(path.charAt(0)=='1'){
					cheer1 = path.substring(1);
				} else if(path.charAt(0)=='2'){
					cheer2 = path.substring(1);
				}
				path = readCheerPath.readLine();
			}
			readCheerPath.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// tidy up the .spelling_aid_cheer to have only 2 cheer file paths
		ClearFiles.clearFile(cheerFile);
		Tools.record(cheerFile, "1"+cheer1);
		Tools.record(cheerFile, "2"+cheer2);
		
	}
	
}
