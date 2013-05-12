package Animation.Effects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Text {
	
    private SpriteSheet image;
	private  String str;
	private int[] characterArray;
	private int width, height, x, y;
	
	public Text() throws SlickException
	{
		width = 17;
		height =23;
		
		try{
			init();
				
			}catch(SlickException e){}
	}
	
	public void init() throws SlickException
	{
		
		try{
		image =new SpriteSheet( new Image("Content/ImageFiles/FONTYellow.png"), width, height);
			
		}catch(SlickException e){}
	}

	public void draw(String _str, int _x, int _y)
	{
		x=_x-width;
		y=_y;
		str = _str;
		characterArray = new int[str.length()];
		for(int i =0; i < str.length(); i++)
		{
		characterArray[i] = getLetterX(_str.charAt(i));
	//	image.renderInUse(10, 10, characterArray[i], 1);
		Image sprite = image.getSubImage( characterArray[i],1);
		sprite.draw(x+=width,y);
		
		}
		
	}
	
	private int getLetterX(char c)
	{
		switch(c)
		{
			case 'A': return (int)0;
			case 'B': return 1;
			case 'C': return 2;
			case 'D': return 3;
			case 'E': return 4;
			case 'F': return 5;
			case 'G': return 6;
			case 'H': return 7;
			case 'I': return 8;
			case 'J': return 9;
			case 'K': return 10;
			case 'L': return 11;
			case 'M': return 12;
			case 'N': return 13;
			case 'O': return 14;
			case 'P': return 15;
			case 'Q': return 16;
			case 'R': return 17;
			case 'S': return 18;
			case 'T': return 19;
			case 'U': return 20;
			case 'V': return 21;
			case 'W': return 22;
			case 'X': return 23;
			case 'Y': return 24;
			case 'Z': return 25;
			
			case '0': return 26;
			case '1': return 27;
			case '2': return 28;
			case '3': return 29;
			case '4': return 30;
			case '5': return 31;
			case '6': return 32;
			case '7': return 33;
			case '8': return 34;
			case '9': return 35;
			default : return -1;
			
			
		}
	}
	
	
}
