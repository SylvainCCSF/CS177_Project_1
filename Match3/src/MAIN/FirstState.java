package MAIN;

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



public class FirstState extends BasicGameState {

	public static final int ID = 1;
	public final float WIDTH, HEIGHT;
	private CardHandler CH;
	private SoundTrack backgroundMusic;
	private boolean startMusic = true;
	private Image cursor;
	
	
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
			}catch(SlickException e){}
		
	} 


	@Override // update the cardHandler -> cardHandler.update() pls keep this method as clean as possible
	public void update( GameContainer container, StateBasedGame game, int delta ) throws SlickException
	{

	    Input input = container.getInput();
		
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
		
		if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			container.exit();
		}
	} 


	@Override //Graphics g is your rendering component
	public void render( GameContainer container, StateBasedGame game, Graphics g ) throws SlickException 
	{ 

		//draws the fps
		g.drawString( Integer.toString( container.getWidth() ) +
				"x" +
				Integer.toString( container.getHeight() ), 
				10, 
				25 ); 

		//Render the Card Handler
		CH.render(container, game, g);
		g.drawString("mouseX: " + Mouse.getX() + "\n MouseY: " + Mouse.getY(), 50,50 );
		//countdown
		g.drawString("remainingTime: " + CH.getTime(), 150, 25);
		
		cursor.draw(Mouse.getX(), HEIGHT-Mouse.getY());
		
	}
}

