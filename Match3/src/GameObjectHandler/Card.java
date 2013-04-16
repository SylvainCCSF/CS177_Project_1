package GameObjectHandler;


	import org.lwjgl.input.Mouse;
    import org.newdawn.slick.GameContainer;
    import org.newdawn.slick.Graphics;
    import org.newdawn.slick.Input;
    import org.newdawn.slick.SlickException;
    import org.newdawn.slick.geom.Rectangle;
    import org.newdawn.slick.state.StateBasedGame;
    import org.newdawn.slick.Image;

	public class Card {
		
		private boolean previous = false;
		private boolean current = false;
		private boolean isCardSelected = false;
		private int rowIndex, columnIndex, cardType;
		private Input input;
		private Rectangle boundingRectangle, mouseRectangle;
		private int mouseX, mouseY;
		private Image image;
		
		
		public Card(int _rowIndex, int _columnIndex, int _cardType)
		{
			rowIndex = _rowIndex;
			columnIndex = _columnIndex;
			cardType = _cardType;
			boundingRectangle = new Rectangle( rowIndex * 50, columnIndex * 50, 50, 50);
			
			mouseRectangle = new Rectangle(0,0,2,2);
		}
		
		
		
		public void update(GameContainer container, StateBasedGame game, int delta ) throws SlickException
		{
			isCardSelected = false;
			mouseX = Mouse.getX();
			mouseY = 800 - Mouse.getY(); //mouse is inverted
		    input  = container.getInput();
			mouseRectangle.setBounds(mouseX, mouseY, 2, 2);
			boolean current = Mouse.isButtonDown(0);
			
			if(current && boundingRectangle.intersects(mouseRectangle) && !previous)
			{
				isCardSelected = true;
			}
			
			previous = current;
			current = previous;
		}
		
		
		public void render( GameContainer container, StateBasedGame game, Graphics g )
		{
			
			image.draw(boundingRectangle.getX(), boundingRectangle.getY());
		}
		
		
		public boolean getIsCardSelected()
		{
			return isCardSelected;
		}
		
		public int getCardType()
		{
			return cardType;
		}
		
		
		public void setIsCardSelected(boolean _value)
		{
			isCardSelected = _value;
		}
		
		public void setCardType(int _value)
		{
			cardType = _value;
		}
		
		public void loadContent() throws SlickException
		{
			try
			{
			if(cardType == 0)
			{
				image = new Image("/Content/ImageFiles/icon_50x50.png");
			}
			
			else if(cardType == 1)
			{
				image = new Image("/Content/ImageFiles/images.jpg");
			}
			
			else if(cardType == 2)
			{
				image = new Image("/Content/ImageFiles/myspace-50x50.png");
			}
			
			else if(cardType == 3)
			{
				image = new Image("/Content/ImageFiles/Purple_50x50.png");
			}
			
			else if(cardType == 4)
			{
				image = new Image("/Content/ImageFiles/Red_50x50.png");
			}
			
			else if(cardType == 5)
			{
				image = new Image("/Content/ImageFiles/th_Kirby_from_Super_Star_Ultra-50x50.png");
			}
		
			else if(cardType == 6)
			{
				image = new Image("/Content/ImageFiles/black.png");
			}
			}catch(SlickException e){}
			
		}
		
	}
