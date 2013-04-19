package GameObjectHandler;


import java.awt.Point;
import java.util.LinkedList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

//////////////////////////////////////////////////////
/// This Object handles the card/Jewel
/// logic and acts as a container for the 
/// card/Jewels. It is called from the GameState's
/// Update()/Render()/init() methods.
//////////////////////////////////////////////////////

public class CardHandler {
	
   
	private LinkedList<Point> indexList;
	private Card[][] board;
	private byte rowSize = 12, columnSize = 16;
	private Rectangle mouseRectangle;
	private int mouseX, mouseY;
	private boolean previousClick = false;
	private boolean currentClick = false;
	private Image[] image;
	
	///<summary>
	///Constructor instantiates the board and mouse Rect
	///<summary>
	
	public CardHandler()
	{
		board = new Card[rowSize][columnSize];
		indexList = new LinkedList();
		mouseRectangle = new Rectangle(0,0, 2, 2);
		fillBoard();
		image = new Image[7];
	}
	
	///<summary>
	///fills an image array of images named {0-6}
	///<summary>
	public void init()
	{
		for(byte i = 0; i < image.length; i++)
		{
			try {
				image[i] = new Image("Content/ImageFiles/" + i + ".png");
			} catch (SlickException e) {e.printStackTrace();}
		}
	}
	///<summary>
	/// Randomly assigns an imageType(byte) 
	/// to every card in the Board[][] array
	///<summary>
	
	public void fillBoard()
	{
		byte imageType;
		for(byte i = 0; i < rowSize; i++)
		{
			for(byte k = 0; k < columnSize; k++)
			{
				imageType = (byte)(Math.random()*6);
				board[i][k] = new Card(i,k, imageType);
			}
		}
	}
	
	///<summary>
    /// Takes in the Mouse pos, 
	/// Resets the currentClicked & prevClicked booleans,
	/// Cycles through Board[][] checking for mouseIntersection + isClicked,
	/// Calls the recursive check(),
	/// Clears any and all matches.
	///<summary>
	
	public void update(GameContainer container, StateBasedGame game, int delta ) throws SlickException
	{
		mouseX = Mouse.getX();
		mouseY = 800 - Mouse.getY();
		mouseRectangle.setBounds(mouseX, mouseY, 2, 2);
		
		currentClick = Mouse.isButtonDown(0);
		
		for(int i = 0; i < rowSize; i++)
		{
			for(int k = 0; k < columnSize; k++)
			{
				
				if(currentClick && mouseRectangle.intersects(board[i][k].getBoundingRectangle()) && !previousClick)
				{
				    check(i, k, "null");
					clearMatches();
				}
			}
		}
		previousClick = currentClick;
		currentClick = previousClick;
	}
	
	///<summary>
	/// Draws the card type of all cards
	///<summary>
	
	public void render( GameContainer container, StateBasedGame game, Graphics g )
	{
		for(byte i = 0; i < rowSize; i++)
		{
			for(byte k = 0; k < columnSize; k++)
			{
				image[board[i][k].getCardType()].draw((int)board[i][k].getBoundingRectangle().getX(), (int)board[i][k].getBoundingRectangle().getY());
			}
		}
	}
	
	///<summary>
	/// Recursively checks for matches,
	/// saves the index locations as a Point(x,y) in a Linked list,
	/// check() up, down, left, right from current pos.
	///<summary>
	
	public void check(int _x, int _y, String _comingFrom) throws SlickException
	{
		String comingFrom = _comingFrom;
		int x = _x;
		int y = _y;
		

		if(x!= 0 && board[x][y].getCardType() == board[x-1][y].getCardType()  && !comingFrom.equalsIgnoreCase("right") && !indexList.contains(new Point(x-1, y)))
		{   
			indexList.add(new Point(x, y));
			indexList.add(new Point(x-1, y));
		    check( x-1 , y, "left");
		}
	  
		if( x+1 < rowSize && board[x][y].getCardType() == board[x+1][y].getCardType() && !comingFrom.equalsIgnoreCase("left") && !indexList.contains(new Point(x+1, y)))
		{
			indexList.add(new Point(x, y));
			indexList.add(new Point(x+1, y));
			check(x+1, y, "right");
		}
		
		if( y - 1 >= 0 && board[x][y].getCardType() == board[x][y-1].getCardType() && !comingFrom.equalsIgnoreCase("above") && !indexList.contains(new Point(x, y-1)))
		{
			indexList.add(new Point(x, y));
	    	indexList.add(new Point(x, y-1));
			check(x, y-1,"below");
		}
		
			if( y+1 < columnSize && board[x][y].getCardType() == board[x][y+1].getCardType() && !comingFrom.equalsIgnoreCase("below") && !indexList.contains(new Point(x, y+1)))
		{
			indexList.add(new Point(x, y));
			indexList.add(new Point(x, y+1));
	    	check(x, y+1,"above");
		}
	}
	
	
	///<summary>
	/// If the Linked list holding the Board[][] indexes of 
	/// currently selected matches is greater than 2 remove
	/// them from the Board[][],
	/// Clears the ArrayList for next selection.
	///<summary>
	
	public void clearMatches() throws SlickException
	{
		if(indexList.size() > 3)
		{
			for(int i = 0; i < indexList.size(); i++)
			{				
			    board[indexList.get(i).x][indexList.get(i).y].setCardType((byte)6);
			}
		}
		indexList.clear();
	}
	
}
