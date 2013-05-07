package GameObjectHandler;


import java.util.ArrayList;
import java.awt.Point;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Time.CountDown;

import Audio.OutOfRangeException;
import Audio.SoundEffect;
//////////////////////////////////////////////////////
/// This Object handles the card/Jewel
/// logic and acts as a container for the 
/// card/Jewels. It is called from the GameState's
/// Update()/Render()/init() methods.
//////////////////////////////////////////////////////
import Audio.SoundTrack;

public class CardHandler {
	private final int APPLET_WIDTH = 800; // width of the applet panel
	private final int APPLET_HEIGHT = 800; // height of the applet panel
	
	private Point clickPoint = null; 
	//private Button debugButton; // for debugging
	static int cardSize = 50;
	static int offsetX = 120;
	static int offsetY = 120;
	static int gridSize = 8;
	static int maxX = gridSize*cardSize+offsetX;
	static int maxY = gridSize*cardSize+offsetY;
	private boolean isRunning = true;
	private boolean isSwapping = false;
	private boolean isDropping = false;
	private boolean madeMove = false;
	double gDeltaTime = 0;
	double moveRate = 5;
	Card firstCard;
	boolean updateGrid=false;
	int numImages = 6;
	//Color[] images;
	Card[][] grid;


	private int mouseX, mouseY;

	private boolean currentClick = false;
	private Image[] images;
	
	// for background music
	private SoundTrack backgroundMusic;
	
	// countdowwn
	private float playingTime;
	private CountDown countDown;

	//constructor
	public CardHandler() {
	}

	//init
	public void init() {
		images = new Image[numImages];
		
		grid = BuildGrid();
		while (CheckForMatches().size()>=1){
			grid = BuildGrid();
		}
		
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = new Image("Content/ImageFiles/" + i + ".png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		// initialize and launch the background music
		backgroundMusic = SoundTrack.TRACK_THREE;
		try {
			backgroundMusic.setVolume(0.3f);
			backgroundMusic.play();
		} catch (OutOfRangeException e) {
			e.printStackTrace();
		}
		
		// initialize the playing time and launch the countdown
		playingTime = 30.0f;
		countDown = new CountDown(playingTime);
	}


	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		gDeltaTime = (double)delta/1000;
		//System.out.println(gDeltaTime);
		GetInput();
		MovePieces();
		
		// update countdown
		countDown.tick();
	}

	
	public Card[][] BuildGrid(){
		
		Card[][] grid = new Card[gridSize][gridSize];
		
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				int imageIndex = (int)(Math.random()*numImages);
				grid[i][j] = new Card(i,j,imageIndex);

			}
		}
		
		return grid;
	}
	
	public void FindAndRemoveMatches(){
		ArrayList<ArrayList> matches = CheckForMatches();
		for(int i =0; i<matches.size(); i++){
			// play delete sound for each match3
			SoundEffect.DELETE.play();
			for( int j = 0; j<matches.get(i).size(); j++){
				ArrayList<Card> match = matches.get(i);
				Card a = match.get(j);
				int x = a.x;
				int y = a.y;
				grid[a.x][a.y]=null;
				DropDown(x, y);
			}
		}
		
		AddNewPieces();
		
	}
	
	
	public void DropDown(int x, int y){
	
			for(int row = y-1; row>=0; row--){
				if (grid[x][row] !=null ){
					grid[x][row].y++;
					grid[x][row+1] = grid[x][row];
					grid[x][row] = null;
				}
				
			}
			
	}

	public void AddNewPieces(){
		
		for(int i=0; i<gridSize;i++){
			int missingPieces = 1;
			for(int j=gridSize-1; j>=0;j--){
				
				if (grid[i][j] ==null ){
					int imageIndex = (int)(Math.random()*numImages);
					Card newCard = new Card(i,j,imageIndex);
					newCard.drawY = -missingPieces++;
					SoundEffect.DROP.play();
					//System.out.println("newcard.y "+newCard.y);
					//System.out.println("i "+i+ " j "+j);
					grid[i][j] = newCard;
					isDropping=true;
				}
				
			}
		}
	}

	
	public void MovePieces(){
		madeMove=false;
		for (int row = 0; row<gridSize; row++){
			for(int col = 0; col<gridSize; col++){
				if (grid[col][row] != null){
					
					Card card = grid[col][row];
					
						//close enough
					if (Math.abs(card.drawY - row) <.1){
						card.drawY = row;
					}
					if (Math.abs(card.drawX - col) <.1){
						card.drawX = col;
					}
					
						//move up
					if (card.drawY > row){
						card.drawY = card.drawY - moveRate*gDeltaTime;
					//	System.out.println("up "+card.drawY + " row "+row);
						madeMove=true;
						
						//move down
					}else if (card.drawY < row){
					//	System.out.println("down "+card.drawY + " row "+row);
						card.drawY = card.drawY + moveRate*gDeltaTime;
					//	System.out.println("down2 "+card.drawY + " row "+row);
						madeMove=true;
						
						//move left
					}else if (card.drawX < col){
						card.drawX = card.drawX + moveRate*gDeltaTime;
						madeMove=true;
						
						//move right
					}else if (card.drawX < col){
						card.drawX = card.drawX - moveRate*gDeltaTime;
						madeMove=true;
					}
					
				}
			}
		}
		if(!madeMove){
			FindAndRemoveMatches();
		}
	}
	
	public void SwapCards(Card a, Card b){
		Card temp = a.clone();
		a.x = b.x;
		a.y = b.y;
		
		b.x = temp.x;
		b.y = temp.y;
		
		grid[a.x][a.y] = a;
		grid[b.x][b.y] = b;
		
		SoundEffect.SWAP.play();
		//System.out.println("swapping");
	}
	
	
	public void GetInput(){

		if (Mouse.isButtonDown(0) && currentClick==false){
			mouseX = Mouse.getX();
			mouseY = Mouse.getY();
			mouseClicked(mouseX, mouseY);
		//	System.out.println("x: "+mouseX + " y: "+mouseY);
			currentClick=true;
		}else if(!Mouse.isButtonDown(0)){
			currentClick=false;
		}
		
		
	}
	
	
	
	public void mouseClicked(int clickX, int clickY) {
		clickY = APPLET_HEIGHT - clickY;
		Point pnt = new Point(clickX, clickY);

		Point selection = new Point();
		Card clickCard;
		if (pnt.x>offsetX && pnt.x<maxX && pnt.y>offsetY && pnt.y<maxY){
			selection.x = (pnt.x-offsetX)/cardSize;
			selection.y = (pnt.y-offsetY)/cardSize;
			clickCard = grid[selection.x][selection.y];
			
			System.out.println("pnt x: " + pnt.x + " y: "+pnt.y);
//			System.out.println("selection x: " + selection.x + " y: "+selection.y);
//			System.out.println("clickCard x: " + clickCard.x + " y: "+clickCard.y);
			//firstCard = grid[selection.x][selection.y];

		}else{
			return;
		}
		
		//first card
		if (firstCard == null){
			firstCard = clickCard;
			SoundEffect.SELECT.play();

		//card too far away
		}else if(!isAdjacent(firstCard,clickCard)){
			firstCard = clickCard;
			SoundEffect.BAD.play();
		
		//same card
		}else if(firstCard == clickCard){
			firstCard = null;
			SoundEffect.BAD.play();
		
		//swap cards
		}else if(isAdjacent(firstCard, clickCard)){
			
			SwapCards(firstCard, clickCard);
			
			//make sure a match is performed
			if(CheckForMatches().size()<1){
				SwapCards(firstCard, clickCard);
				firstCard = null;
			//if there is a match
			}else{
				
				firstCard = null;
				FindAndRemoveMatches();
			}
		}
	}
	
	public boolean isAdjacent(Card a, Card b){
		
		if (Math.abs(b.x - a.x) > 1){
			return false;
		}
		
		if (Math.abs(b.y - a.y) > 1){
			return false;
		}			
		
		return true;
	}
	
	
	public ArrayList CheckForMatches(){
		ArrayList<ArrayList> matchList = new ArrayList();

		//search horizontal
		for (int row = 0; row<gridSize; row++){
			for (int col = 0; col<gridSize-2; col++){
				ArrayList<Card> match = getMatchHoriz(col, row);
				if (match.size()>2){
					matchList.add(match);
					col += match.size() -1;
				}
			}
		}
		
		//search vertical
		for (int col = 0; col<gridSize; col++){
			for (int row = 0; row<gridSize-2; row++){
				ArrayList<Card> match = getMatchVert(col, row);
				if (match.size()>2){
					matchList.add(match);
					row += match.size() -1;
					
				}
			}
		}		
		
			
		return matchList;
	}
	
	
	public ArrayList getMatchHoriz(int col, int row){
		ArrayList<Card> match = new ArrayList();

		match.add(grid[col][row]);
		for(int i=1; col+i<gridSize; i++){
			if (grid[col][row].cardType == grid[col+i][row].cardType){
				match.add(grid[col+i][row]);
			}else{

				return match;
			}
		}

		return match;
	}
	
	public ArrayList getMatchVert(int col, int row){
		ArrayList<Card> match = new ArrayList();

		match.add(grid[col][row]);
		for(int i=1; row+i<gridSize; i++){
			if (grid[col][row].cardType == grid[col][row+i].cardType){
				match.add(grid[col][row+i]);
			}else{

				return match;
			}
		}
		return match;
	}	
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		//draw grid
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				//images[grid[i][j].getCardType()].draw((cardSize)*i+offsetX, (cardSize)*j+offsetY);
				Card card = grid[i][j];
				Image image = images[card.getCardType()];
				float y = (float)card.drawY;
				//System.out.println("y "+y);
				image.draw(card.x*cardSize+offsetX, y*cardSize+offsetY);

			}
		}
		
		//draw selection
		if (firstCard != null){
			//g.setColor(Color.magenta);
			g.drawRect(firstCard.x*cardSize+offsetX, firstCard.y*cardSize+offsetY, cardSize, cardSize);
		}
	}
	
	/**
	 * Getter for the countdown
	 * @return
	 */
	public CountDown getTime() {
		return countDown;
	}
}
