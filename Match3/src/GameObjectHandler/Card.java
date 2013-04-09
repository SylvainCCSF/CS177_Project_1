public class Card{
	
	int x;
	int y;
	int image;

	public Card( int _x, int _y, int _image){
		
		image = _image;
		
		x = _x;
		y = _y;
		
	}
	
	public Card clone(){
		
		Card newCard = new Card(this.x, this.y, this.image);
		
		return newCard;
	}
	
	

}

