package MAIN;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Match3 extends StateBasedGame {

	public static final byte menu = 0;
	public static final int WIDTH=600, HEIGHT=800;
	
	//declare the name of the applet and add the first state
	public Match3()
	{
		
		super("Tutorial Applet");
		
		//add states here- They are saved in a stack
		this.addState(new FirstState(WIDTH, HEIGHT)); 
	}
	
	//creates appGameContainer for the library
	public static void main(String[] argv) throws SlickException
	{
		AppGameContainer app;
		
		try{ 
	     	   app = new AppGameContainer(new Match3());
	     	   app.setDisplayMode(WIDTH, HEIGHT, false); 
	     	   app.setShowFPS(true);
	     	   app.setTargetFrameRate(60);
	     	   app.start();    	   
	     	}catch(SlickException e)
	     	 {
	     	 	e.printStackTrace();
	     	 }
	}
	
	public void initStatesList(GameContainer gc) throws SlickException
	{
		 //init() states here
		this.getState(1).init(gc, this);
		this.enterState(1);  //enter first state
	}

	
	
	
}
