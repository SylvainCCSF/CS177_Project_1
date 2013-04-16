package MAIN;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Match3 extends StateBasedGame {

	public static final int menu = 0;
	public static final int WIDTH=600, HEIGHT=800;
	
	//declare the name of the applet and add the first state
	public Match3()
	{
		super("Tutorial Applet");
		this.addState(new FirstState(WIDTH, HEIGHT)); //add states here
	}
	
	//creates appGameContainer for the library
	public static void main(String[] argv) throws SlickException
	{
		AppGameContainer app;
		
		try{ 
	     	   app = new AppGameContainer(new Match3());
	     	   app.setDisplayMode(WIDTH, HEIGHT, false); 
	     	   app.setShowFPS(true);
	     	   app.setTargetFrameRate(45);
	     	   app.start();    	   
	     	}catch(SlickException e)
	     	 {
	     	 	e.printStackTrace();
	     	 }
	}
	
	public void initStatesList(GameContainer gc) throws SlickException
	{
		this.getState(0).init(gc,this); //init states here
		this.enterState(0);  //enter first state
	}

	
	
	
}
