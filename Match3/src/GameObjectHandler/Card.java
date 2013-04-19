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
		
		///<summary>
		/// Holds index locations of where it exists in the CH.Board[][],
		/// Has a bounding Rectangle to check for mouse intersections.
		/// Has an cardType to inform the CardHandler what type of image
		/// should be displayed within its bounding Rectangle. {This can
		/// be swapped out for an animation object}
		///<summary>
		
		private byte rowIndex, columnIndex, cardType;
		private Rectangle boundingRectangle;
		
		public Card(byte _rowIndex, byte _columnIndex, byte _cardType)
		{
			rowIndex = _rowIndex;
			columnIndex = _columnIndex;
			cardType = _cardType;
			boundingRectangle = new Rectangle( rowIndex * 50, columnIndex * 50, 50, 50);
		}
		
		public byte getCardType(){return cardType;}		
		public Rectangle getBoundingRectangle(){ return boundingRectangle;}
		public void setCardType(byte _value){cardType = _value;}
		public void setBoundingRectangle(Rectangle _value){ boundingRectangle = _value;}
		
	}
