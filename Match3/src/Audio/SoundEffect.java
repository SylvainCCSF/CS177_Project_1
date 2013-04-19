package Audio;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This enum contains all sound effects of Match3 game. It defines
 * also a static variable 'SoundEffect.volume' to enable a mute mode
 * for the game (read further).
 * 
 * INSTRUCTIONS:
 * 
 * 1/ To add a sound effect, simply add an item to the list
 *    in the following format: SOUNDNAME('fileName.wav') where
 *    SOUNDNAME is the name of the sound that will be called in the 
 *    program and 'fileName.wav' the sound file (wav format) that will
 *    be placed into the '/res' directory.
 * 
 * 2/ To play a sound, just call SoundEffect.SOUNDNAME.play()
 *    anywhere in the program.
 * 
 * 3/ For the mute mode, set the static variable SoundEffect.volume to OFF
 *    by doing SoundEffect.volume = Volume.OFF
 * 
 * 
 * I've based my code on this webpage:
 * http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 * @author sylvain
 *
 */
public enum SoundEffect {
//-------------------------------------------------------------------------
//                   VARIABLES
//-------------------------------------------------------------------------
	// Available sound effects
	// Play one with 'SoundEffect.SOUNDNAME.play()'
	SELECT("select.wav"),
	SWAP("swap.wav");
	
	 // Each sound effect has its own clip, loaded with its own sound file.
	 private Clip clip;
	 
	 // Nested enum to enable a volume switch ON/OFF button
	 // NOTE: Is declared 'static' to be able to switch on/off
	 //       all sounds at the same time.
	 public static enum Volume {
		 ON, 
		 OFF;
	 }
	 
	 // Volume is 'ON' by default
	 public static Volume volume = Volume.ON;

//-------------------------------------------------------------------------
//              METHODS	 
//-------------------------------------------------------------------------
	 /**
	  * Constructor: build each item on the enum with
	  * its own sound file
	  * @param soundFileName sound file name
	  */
	SoundEffect(String soundFileName) {
		try {
			// **** Locate the file ****
			//
			// 1 - build the relative path for the resource
			// 2 - use an URL object to build the absolute path
			//     for the resource
			// 3 - get the file from the URL object
			//
			// NOTE: be sure that the classpath contains
			//       the '/Content' folder
			char sep = File.separatorChar; // default name-separator character 
			String path =  sep
					     + "SoundClips" + sep
					     + soundFileName;
			URL url = getClass().getResource(path);
			File soundFile = new File(url.getPath());
			
			// Set up an audio input
			AudioInputStream audioInputStream 
				= AudioSystem.getAudioInputStream(soundFile);
			
			// Get a clip resource.
			clip = AudioSystem.getClip();
	         
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioInputStream);
		} 
		catch (UnsupportedAudioFileException 
				 | IOException 
				 | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Plays a sound. The method stops the clip if running, 
	 * rewinds to the beginning of the clip and plays it again
	 * to avoid overlap.
	 */
	public void play() {
		if (volume == Volume.ON) {
			if (clip.isRunning())
				clip.stop();          // 1. stop the clip if running
			clip.setFramePosition(0); // 2. rewind
			clip.start();             // 3. start a new time
		}
	}
	
	/**
	 * Stop a sound which is playing.
	 */
	public void stop() {
		if (volume == Volume.ON)
			if (clip.isRunning())
				clip.stop();
	}
	
	/**
	 * Pre-load all the sounds files to avoid pausing while
	 * loeading the sound file for the first time.
	 */
	public static void init() {
		values(); // call the constructor for all the element in the enum
	}
	
}