package MAIN;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import GameObjectHandler.CardHandler;



public class FirstState extends BasicGameState {

	   public static final int ID = 0;
	   public final int WIDTH, HEIGHT;
	   private CardHandler CH;
	   
	   
	   public FirstState(int width, int height)
	   {
		   WIDTH = width;
		   HEIGHT = height;
	   }
	   
	   
	   @Override //returns the state id for state switching
	   public int getID() {
	      return ID;
	   }
	   
	   
	    @Override //initialize the logic
	     public void init( GameContainer container, StateBasedGame game ) throws SlickException
	     {
	    	CH = new CardHandler();
	     } 
	    
	    
	     @Override // update the cardHandler -> cardHandler.update() pls keep this method as clean as possible
	     public void update( GameContainer container, StateBasedGame game, int delta ) throws SlickException
	     {
	    	 CH.update(container, game, delta);
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
	     
	     
	       CH.render(container, game, g);
	       g.drawString("mouseX: " + Mouse.getX() + "\n MouseY: " + Mouse.getY(), 50,50 );
	     }
	}

