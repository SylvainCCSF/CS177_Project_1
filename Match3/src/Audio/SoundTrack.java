package Audio;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Audio.SoundEffect.Volume;


public enum SoundTrack {
//-------------------------------------------------------------------------
//  VARIABLES
//-------------------------------------------------------------------------
	// Available sound tracks
	// Play one with 'SoundTrack.TRACK_NAME.play()'
	TRACK_ONE("track1.wav");
	
	private Clip track; // SoundTrack object has its own clip
	private int currentPosition; // current position in the stream
	
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
//  METHODS	 
//-------------------------------------------------------------------------
	/**
	 * Constructor
	 * @param soundTrackName name of the sound track file
	 */
	SoundTrack(String soundTrackName) {
		
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
					     + soundTrackName;
			URL url = getClass().getResource(path);
			File soundFile = new File(url.getPath());
			
			
			// Set up an audio input
			AudioInputStream audioInputStream 
				= AudioSystem.getAudioInputStream(soundFile);
			
			// Get a clip resource.
			track = AudioSystem.getClip();
	         
			// Open audio clip and load samples from the audio input stream.
			track.open(audioInputStream);
			
			// Initialize the currentPosition to 0
			currentPosition = 0;
			
			

		} 
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Play the track indefinitely from the current position
	 */
	public void play() {
		if (volume == Volume.ON) {
			if (track.isRunning())
				track.stop();
			track.setFramePosition(currentPosition);
			track.loop(Clip.LOOP_CONTINUOUSLY); //play indefinitely
		}
	}
	
	/**
	 * Stop the track playing and keep track of the current position
	 * for future playing.
	 */
	public void pause() {
		if (volume == Volume.ON)
			if (track.isRunning()) {
				currentPosition = track.getFramePosition();
				track.stop();
			}
	}
	
	/**
	 * Stop the track playing and set the current position to 0
	 */
	public void stop() {
		if(volume == Volume.ON)
			if (track.isRunning()) {
				track.stop();
				currentPosition = 0; // set current position to the beginning
			}
	}

}