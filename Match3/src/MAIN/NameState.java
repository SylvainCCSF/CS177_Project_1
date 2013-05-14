package MAIN;


import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Animation.Effects.Text;
import Audio.OutOfRangeException;
import Audio.SoundTrack;
import GUI.Button;
import Scores.ScoresInfo;


public class NameState extends BasicGameState {
	
	public static final int ID = 4;
	private final float WIDTH, HEIGHT;
	private Button newGameButton, exitButton;
	private SoundTrack backgroundMusic;
	private boolean startMusic = true;
	private Image background, cursor, alphabet;
	private Rectangle mouseRect;
	private Input input;
	
	private ScoresInfo scoresList;
	private String name;

	
	private Text text;
	
	public NameState(float _WIDTH, float _HEIGHT)
	{
		mouseRect = new Rectangle(Mouse.getX(), Mouse.getY(), 2, 2);
		WIDTH = _WIDTH;
		HEIGHT = _HEIGHT;
		try{
		background = new Image("Content/ImageFiles/Bricks2.png");
		cursor = new Image("Content/ImageFiles/Cursor.png");
		alphabet = new Image("Content/ImageFiles/FontsTrans.png");
		}catch(SlickException e){}
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{		
		// initialize and launch the background music
		backgroundMusic = SoundTrack.TRACK_ONE;
		
		// get the last scores list
		scoresList = retrieveScores();
		
		try {
			backgroundMusic.setVolume(.9f);
			backgroundMusic.play();
			text = new Text();
		} catch (OutOfRangeException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		//start music
		if(game.getCurrentStateID() == ID && startMusic)
		{
		 backgroundMusic = SoundTrack.TRACK_THREE;
			try {
				backgroundMusic.setVolume(0.3f);
				backgroundMusic.play();
			    } catch (OutOfRangeException e) {e.printStackTrace();}
			finally{   startMusic = false;}
		}
		
        //getMouse Input
		mouseRect.x = Mouse.getX();
		mouseRect.y = (int)(HEIGHT - Mouse.getY());
		
		// keyboard input
		input = container.getInput();

		// catch keys pressed
		if(input.isKeyPressed(Input.KEY_ENTER))
		{
			game.enterState(2, new FadeOutTransition(Color.darkGray, 1000), new FadeInTransition(Color.white, 300) );
		}
		
		if(input.isKeyPressed(Input.KEY_ESCAPE))
		{
			container.exit();
		}
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
	{
		background.draw(0, 0, WIDTH, HEIGHT);
		
		// find the good scale to display alphabet
		int alphabetWidth = 650;
		int pad = 50;
		float scale = (float) (WIDTH - 2 * pad) / alphabetWidth;
		
		alphabet.draw(pad, pad, scale, Color.darkGray);
		
		text.draw("press Enter to continue", WIDTH * 0.15f, HEIGHT * 0.90f,  WIDTH * 0.03f,WIDTH * 0.03f, Color.gray );
		text.draw("press Enter to continue", WIDTH * 0.15f+3, HEIGHT * 0.90f+3,  WIDTH * 0.03f,WIDTH * 0.03f, Color.black );
		
		//the Cursor, draw last!
		cursor.draw(Mouse.getX(), HEIGHT-Mouse.getY());
	}

	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	/**
	 * Retrieve the scores list. </br>
	 * ScoresInfos object are serializable and are stored in the
	 * file 'Content/Backups/scores.ser'.</br>
	 * If the file does not exist, a new ScoresInfo object is instantiated,
	 * since it is the first time the game runs locally. It returns
	 * this new object.</br>
	 * If the file exists, it retrieves and returns the ScoresInfo object
	 *  saved in it.
	 * @return ScoresInfo object that contains the scores list
	 */
	public static ScoresInfo retrieveScores() {
		ScoresInfo list = null;
		
	    try {
	      File f = new File("Content/Backups/scores.ser");
	      if (f.exists()) {
	    	  FileInputStream fichier = new FileInputStream(f);
		      ObjectInputStream ois = new ObjectInputStream(fichier);
		      list = (ScoresInfo) ois.readObject();
	      } else {
	    	  list = new ScoresInfo();
	      }
	      
	    } 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
	    
	    return list;
	}
	
	/**
	 * Save the scores list into the 'Content/Backups/scores.ser' file
	 * (uses serialization)
	 * @param list ScoresInfo object that stores the scores list
	 */
	public static void saveScores(ScoresInfo list) {
	    try {
	        FileOutputStream fichier 
	           = new FileOutputStream("Content/Backups/scores.ser");
	        ObjectOutputStream oos = new ObjectOutputStream(fichier);
	        oos.writeObject(list);
	        oos.flush();
	        oos.close();
	      }
	      catch (java.io.IOException e) {
	        e.printStackTrace();
	      }
	}
}
