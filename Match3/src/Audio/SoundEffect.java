package Audio;

import java.io.File;
import java.net.URL;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Enumeration of all sound effect available.
 * @author sylcha
 *
 */
public enum SoundEffect {
	// Add the files in the '/Content/SoundClips' directory
	// than add the sound to the list following the syntax:
	// EFFECT_NAME("sound_file_name")
	SELECT("select.wav"),
	SWAP("swap.wav");
	
	private Sound effect; // clip that handles the sound effect
	private static boolean mute = false; // flag for mute status
	
	public static float volume = 1.0f; // volume level 
	private static float oldVolume; // to retrieve last vol level after mute
	
	/**
	 * Constructor.
	 * @param soundFileName name of the sound file (String)
	 */
	SoundEffect(String soundFileName) {
		char sep = File.separatorChar; // default name-separator character 
		String path =  sep
				     + "SoundClips" + sep
				     + soundFileName;
		URL url = getClass().getResource(path);
		
		try {
			effect = new Sound(url);
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Play the sound at the general volume level.
	 */
	public void play() {
		effect.play(0.5f, volume);
	}
	
	/**
	 * Increase the volume of 10%.
	 * Set the static variable 'volume' to a new value less than 1.0f.
	 */
	public static void volumeUp() {
		// increase of 10%
		if (!mute)
			volume += 0.1; 
		// control the maximum value
		if (volume > 1.0f)
			volume = 1.0f;
	}
	
	/**
	 * Decrease the volume of 10%
	 * Set the static variable 'volume' to a new value more than 0.0f.
	 */
	public static void volumeDown() {
		// decrease of 10%
		volume -= 0.1;
		// control the minimum value
		if (volume < 0.0f)
			volume = 0.0f;
	}
	
	public static void switchSound() {
		if (!mute) {
			oldVolume = volume;
			volume = 0.0f;
			mute = true;
		}
		else {
			volume = oldVolume;
			mute = false;
		}
	}
}