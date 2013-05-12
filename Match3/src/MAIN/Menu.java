package MAIN;

import java.awt.Rectangle;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Animation.Effects.Text;
import Audio.OutOfRangeException;
import Audio.SoundTrack;
import GUI.Button;


public class Menu extends BasicGameState {
	
	public static final int ID = 0;
	private final float WIDTH, HEIGHT;
	private Button newGameButton, settingsButton, exitButton;
	private SoundTrack backgroundMusic;
	private boolean startMusic = true;
	private Image background, cursor;
	private Rectangle mouseRect;
	
	private Text text;
	
	public Menu(float _WIDTH, float _HEIGHT)
	{
		mouseRect = new Rectangle(Mouse.getX(), Mouse.getY(), 2, 2);
		WIDTH = _WIDTH;
		HEIGHT = _HEIGHT;
		try{
		background = new Image("Content/ImageFiles/MenuBG.jpg");
		cursor = new Image("Content/ImageFiles/Cursor.png");
		}catch(SlickException e){}
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		newGameButton = new Button(new Image("Content/ImageFiles/DotsFree.png"));
		settingsButton = new Button(new Image("Content/ImageFiles/Cogs.png"));
		exitButton = new Button(new Image("Content/ImageFiles/Exit.png"));
			
		//set buttonX value
		newGameButton.setX((int)(WIDTH/2)-newGameButton.getWidthCenter());
		newGameButton.setY( (int)(HEIGHT*.2f));
		settingsButton.setX((int)(WIDTH/2)-settingsButton.getWidthCenter());
		settingsButton.setY((int)(HEIGHT*.5f));
		exitButton.setX((int)(WIDTH/2)-exitButton.getWidthCenter());
		exitButton.setY((int)(HEIGHT*.8f));
		
		
		       // initialize and launch the background music
				backgroundMusic = SoundTrack.TRACK_ONE;
				
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
        //getMouse Input
		mouseRect.x = Mouse.getX();
		mouseRect.y = (int)(HEIGHT - Mouse.getY());
		
		//check the new game button intersection and click
		newGameButton.checkIntersection(mouseRect);
		newGameButton.Update(container, game, delta);
		
		settingsButton.checkIntersection(mouseRect);
		settingsButton.Update(container, game, delta);
		
		exitButton.checkIntersection(mouseRect);
		exitButton.Update(container, game, delta);
		
		
		
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
		
		//switch to GameState with NEWGAMEBUTTON check
		if (newGameButton.isClicked())
		{
		  startMusic = true;
		  game.getState(1).init(container, game);
		  game.enterState(1);
		}
		
		if(settingsButton.isClicked())
		{
			
		}
		
		if(exitButton.isClicked())
		{
			container.exit();
		}
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
	{
		background.draw(0, 0, WIDTH, HEIGHT);
		newGameButton.render(container, game, g);
		settingsButton.render(container, game, g);
		exitButton.render(container, game, g);
		
		
		text.draw("MATCH", 40, 200);
		
		//the Cursor, draw last!
		cursor.draw(Mouse.getX(), HEIGHT-Mouse.getY());
	}

	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	

}
