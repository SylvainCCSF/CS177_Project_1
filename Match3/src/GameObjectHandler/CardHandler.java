package GameObjectHandler;


import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class CardHandler {
	
	
    private int index = 0;
	private LinkedList<Point> indexArray;
	private Card[][] board;
	private int rowSize = 12, columnSize = 16;
	
	
	public CardHandler()
	{
		board = new Card[rowSize][columnSize];
		fillBoard();
		indexArray = new LinkedList();
	try{
		loadContent();
	   }catch(SlickException e){}
	}
	
	public void loadContent() throws SlickException
	{
		for(int i = 0; i < rowSize; i++)
		{
			for(int k = 0; k < columnSize; k++)
			{
				board[i][k].loadContent();
			}
		}
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta ) throws SlickException
	{ 
		
		for(int i = 0; i < rowSize; i++)
		{
			for(int k = 0; k < columnSize; k++)
			{
				board[i][k].update(container, game, delta);
				
				if( board[i][k].getIsCardSelected())
				{
				    check(i, k, "null");

					clearMatches();
				}
				
			}
		}
	}
	
	public void render( GameContainer container, StateBasedGame game, Graphics g ) throws SlickException
	{
		
		for(int i = 0; i < rowSize; i++)
		{
			for(int k = 0; k < columnSize; k++)
			{
				board[i][k].render(container, game, g);
			}
		} 
		
	}
	
	public void fillBoard()
	{
		int imageType;
		for(int i = 0; i < rowSize; i++)
		{
			for(int k = 0; k < columnSize; k++)
			{
				imageType = (int)(Math.random()*6);
				board[i][k] = new Card(i,k, imageType);
			}
		}
	}
	
	
	public void check(int _x, int _y, String _comingFrom) throws SlickException
	{
		String comingFrom = _comingFrom;
		int x = _x;
		int y = _y;
		
		
		if(x!= 0 && board[x][y].getCardType() == board[x-1][y].getCardType()  && !comingFrom.equalsIgnoreCase("right") && !indexArray.contains(new Point(x-1, y)))
		{   
			indexArray.add(new Point(x, y));
			indexArray.add(new Point(x-1, y));
		
		   System.out.println("this is the current index: " + indexArray.size());
		   check( x-1 , y, "left");
		}
	  
		if( x+1 < 12 && board[x][y].getCardType() == board[x+1][y].getCardType() && !comingFrom.equalsIgnoreCase("left") && !indexArray.contains(new Point(x+1, y)))
		{
			indexArray.add(new Point(x, y));
			indexArray.add(new Point(x+1, y));
			check(x+1, y, "right");
		}
		
		if( y - 1 >= 0 && board[x][y].getCardType() == board[x][y-1].getCardType() && !comingFrom.equalsIgnoreCase("above") && !indexArray.contains(new Point(x, y-1)))
		{
			indexArray.add(new Point(x, y));
	    	indexArray.add(new Point(x, y-1));
			check(x, y-1,"below");
		}
		
			if( y+1 < 16 && board[x][y].getCardType() == board[x][y+1].getCardType() && !comingFrom.equalsIgnoreCase("below") && !indexArray.contains(new Point(x+1, y+1)))
		{
			indexArray.add(new Point(x, y));
			indexArray.add(new Point(x, y+1));
	    	check(x, y+1,"above");
		}
		
	}
	
	//
	
	
	public void clearMatches() throws SlickException
	{
		if(indexArray.size() > 3)
		{
			for(int i = 0; i < indexArray.size(); i++)
			{
				board[indexArray.get(i).x][ indexArray.get(i).y] = new Card(indexArray.get(i).x, indexArray.get(i).y, 6 );
				try{
					board[indexArray.get(i).x][indexArray.get(i).y].loadContent();
				}catch(SlickException e){}
			}
		}
		indexArray.clear();
	}
	
}
