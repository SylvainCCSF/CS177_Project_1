package MAIN;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Audio.OutOfRangeException;
import Audio.SoundTrack;
import GameObjectHandler.CardHandler;
import Scores.ScoresInfo;



public class FirstState extends BasicGameState {

	public static final int ID = 1;
	public final float WIDTH, HEIGHT;
	private static CardHandler CH;
	private SoundTrack backgroundMusic;
	private boolean startMusic = true;
	private Image cursor;
	private Image background;
	private Point bgPoint1, bgPoint2;
	private static ScoresInfo scoresList;
	private  Input input;
	
	
	
	public FirstState(float _WIDTH, float _HEIGHT)
	{
		WIDTH = _WIDTH;
		HEIGHT = _HEIGHT;
		CH = new CardHandler(WIDTH, HEIGHT);
	}


	@Override //returns the state id for state switching
	public int getID() {
		return ID;
	}


	@Override //initialize the logic
	public void init( GameContainer container, StateBasedGame game ) throws SlickException
	{
		CH.init();
		   try{
			cursor = new Image("Content/ImageFiles/Cursor.png");
			background = new Image("Content/ImageFiles/starBG.jpg");
			
			// print scores list for debugging
			System.out.println(scoresList);
			
			
			}catch(SlickException e){}
		    bgPoint1 = new Point(0,0);
			bgPoint2 = new Point(0, (int)HEIGHT);
	} 


	@Override // update the cardHandler -> cardHandler.update() pls keep this method as clean as possible
	public void update( GameContainer container, StateBasedGame game, int delta ) throws SlickException
	{

	    input = container.getInput();
		
		//Update the CardHandler
		CH.update(container, game, delta);
		
		
		if(game.getCurrentStateID() == ID && startMusic)
		{
			   backgroundMusic = SoundTrack.TRACK_TWO;
			try {
				backgroundMusic.setVolume(0.3f);
				backgroundMusic.play();
			    } catch (OutOfRangeException e) {e.printStackTrace();}
			   finally{ startMusic = false;}
		}
		
		if(input.isKeyDown(Input.KEY_1))
		{
			startMusic = true;
			game.enterState(0);
		}
		
		//check for escape
		if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			// add score to the list
			scoresList.addEntry(CH.getScoreObject());
			container.exit();
		}
		
		//gameover
		if((int)CH.getTime().getTime() <= 0)
		{
			game.enterState(2);
		}
		
		
		
		//update Background
		
		bgPoint1.y -= (int)((60 / CH.getTime().getTime()));
	    bgPoint2.y -= (int)(60 / (CH.getTime().getTime()));
		
		if(bgPoint1.y < -HEIGHT)
		{
		  bgPoint1.y = (int)HEIGHT;
		}
		if(bgPoint2.y < -HEIGHT)
		{
			bgPoint2.y = (int)HEIGHT;
		}
		
	} 


	@Override //Graphics g is your rendering component
	public void render( GameContainer container, StateBasedGame game, Graphics g ) throws SlickException 
	{ 

		//draw background
		background.draw(bgPoint1.x, bgPoint1.y,WIDTH, HEIGHT);
		background.draw(bgPoint2.x, bgPoint2.y,WIDTH, HEIGHT);
		

		//Render the Card Handler
		CH.render(container, game, g);
		// countdown
		g.drawString("" + CH.getTime(), WIDTH * 0.5f, HEIGHT * 0.10f);
		// score
		g.drawString("SCORE: " + CH.getScoreAmount(), WIDTH * 0.25f, HEIGHT * 0.15f);
		cursor.draw(Mouse.getX(), HEIGHT-Mouse.getY());
		
	}
	
	
	public ScoresInfo getScore()
	{
		return scoresList;
	}
}

