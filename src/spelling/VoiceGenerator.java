package spelling;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingWorker;

import spelling.settings.ClearFiles;

/**
 * 
 * This class governs the main logic for generating festival voices for pronouncing sentences
 * and spelling words
 * @authors yyap601 hchu167
 *
 */
public class VoiceGenerator extends SwingWorker<Void, Void>{
		private double stretch;
		private Voice voice;
		private double pitch;
		private double range;
		private File schemeFile;
		
		// texts to be said in background
		private String swingWorkerChangedText;
		private String swingWorkerNormalText;
		
		public static enum Voice{
			DEFAULT,AUCKLAND;
		}
		
		/**
		 * constructor to construct a voice generator with chosen voice and speed
		 * @param chosenVoice chosen festival voice 
		 * @param chosenDurationStretch chosen duration stretch
		 * @param pitch pitch
		 * @param range range
		 */
		public VoiceGenerator(Voice chosenVoice, double chosenDurationStretch, double pitch, double range){
			makeSureScmFileIsPresent();
			schemeFile = new File(".spelling_aid_voice.scm");
					
			this.pitch = pitch;
			this.range = range;
			this.stretch = chosenDurationStretch;
			this.voice = chosenVoice;
			
		}
		
		/**
		 * Change the preferred voice
		 * @param chosenVoice chosenVoice: chosen festival voice 
		 */
		public void setVoice(Voice chosenVoice){
			voice=chosenVoice;
		}
		
		/**
		 * Say 2 different texts at different speeds
		 * @param normalSpeedText normalSpeedText: text to be said in normal speed
		 * @param changedText changedText: text to be said in the chosen duration stretch
		 */
		public void sayText(String normalSpeedText,String changedText){
			double originalStretch = stretch;
			ClearFiles.clearFile(schemeFile);
			if(voice == Voice.DEFAULT){
				// to make "a" sound more appropriately
				if(changedText.equals("a,")){
					changedText="ae,";
				}
				if(changedText.equals("a,a,")){
					changedText="ae,ae,";
				}
				Tools.record(schemeFile, "(voice_rab_diphone)\n" );
			} else {
				Tools.record(schemeFile, "(voice_akl_nz_jdt_diphone)\n");
				if(changedText.equals("a")){
					stretch-=0.3;
				} else {
					stretch-=0.1;
				}

			}
			Tools.record(schemeFile,("(SayText \"" + normalSpeedText + "\" )\n"));
			Tools.record(schemeFile,"(set! duffint_params '((start " + pitch + ")(end " + (pitch - range) + ")))\n");
			Tools.record(schemeFile,"(Parameter.set 'Int_Method 'DuffInt)\n");
			Tools.record(schemeFile,"(Parameter.set 'Int_Target_Method Int_Targets_Default)\n");
			Tools.record(schemeFile, "(Parameter.set 'Duration_Stretch "+stretch+")\n");
			Tools.record(schemeFile,("(SayText \"" + changedText + "\" )\n"));
			Tools.record(schemeFile,"(quit)");
			Tools.processStarter("festival -b .spelling_aid_voice.scm");
			stretch = originalStretch;
		}
		
		/**
		 *  make sure scheme file is present for writing or typing
		 */
		private void makeSureScmFileIsPresent() {
			File scmFile = new File(".spelling_aid_voice.scm");
			try{
				if(! scmFile.exists()){
					scmFile.createNewFile();
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		
		/**
		 * set the text to be said in the background
		 * @param normal text to be said in normal speed
		 * @param changed text to be said in the chosen duration stretch
		 */
		public void setTextForSwingWorker(String normal, String changed){
			swingWorkerChangedText = changed;
			swingWorkerNormalText = normal;
		}

		/**
		 * says text in background
		 */
		protected Void doInBackground() throws Exception {
			sayText(swingWorkerNormalText,swingWorkerChangedText);
			return null;
		}
}