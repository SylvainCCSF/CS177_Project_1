
import java.awt.*;
import javax.swing.JApplet;
import java.applet.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Match3 extends Applet
{
	private final int APPLET_WIDTH = 800; // width of the applet panel
	private final int APPLET_HEIGHT = 600; // height of the applet panel
	
	private Point clickPoint = null; 
	private Button debugButton; // for debugging
	static int cardSize = 45;
	static int offsetX = 120;
	static int offsetY = 120;
	static int gridSize = 7;
	static int maxX = gridSize*cardSize+offsetX;
	static int maxY = gridSize*cardSize+offsetY;
	private boolean isRunning = true;

	Card firstCard;
	boolean updateGrid=false;
	int numImages = 7;
	Color[] images;
	Card[][] grid;
	
	// Constructor - set up code for listeners, background color, panel size, and reset button
	public void init()  {	
		//set up input
		LineListener listener = new LineListener();
		addMouseMotionListener(listener); // mouse motion listener
		addMouseListener(listener); // mouse listener
		
		//set up the window
		setBackground (Color.black);		
		setSize(APPLET_WIDTH, APPLET_HEIGHT);
		setFont (new Font ("Arial", Font.BOLD,16));
		
		//add debug button
		debugButton = new Button ("Debug");
		debugButton.addActionListener(new ButtonListener());
		add (debugButton);

		//get list of cards
		images = GetImages();

		//Build Grid
		grid = BuildGrid();
		//get grid with no matches
		while (CheckForMatches().size()>=1){
			grid = BuildGrid();
		}
		
		//****** Sylvain: 2013-04-10 ******
		// Pre-load all sound effects
		Sound.init();
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

	//removes matches
	public void FindAndRemoveMatches(){
		ArrayList<ArrayList> matches = CheckForMatches();
		for(int i =0; i<matches.size(); i++){
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
	//resets grid
	public void AddNewPieces(){
		
		for(int i=0; i<gridSize;i++){
			for(int j=0; j<gridSize;j++){
				
				if (grid[i][j] == null ){
					int imageIndex = (int)(Math.random()*numImages);
					grid[i][j] = new Card(i,j,imageIndex);

				}
				
			}
		}
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
	
	public void MovePieces(){
		
	}
	
	public void SwapCards(Card a, Card b){
		Card temp = a.clone();
		a.x = b.x;
		a.y = b.y;
		
		b.x = temp.x;
		b.y = temp.y;
		
		grid[a.x][a.y] = a;
		grid[b.x][b.y] = b;
		
		//****** Sylvain: 2013-04-10 ******
		Sound.SWAP.play();

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
			if (grid[col][row].image == grid[col+i][row].image){
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
			if (grid[col][row].image == grid[col][row+i].image){
				match.add(grid[col][row+i]);
			}else{

				return match;
			}
		}
		return match;
	}
	
	
	public Color[] GetImages(){
		images = new Color[numImages];
		images[0] = Color.red;
		images[1] = Color.cyan;
		images[2] = Color.green;
		images[3] = Color.yellow;
		images[4] = Color.white;
		images[5] = Color.orange;
		images[6] = Color.pink;
		
		return images;
	}
	
	// Draws graphics
	public void paint(Graphics g) {
		// Create Grid
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				if (grid[i][j] == null){
					continue;
				}
				int colorIndex = grid[i][j].image;
				g.setColor(images[colorIndex]);
				g.fillRect((cardSize)*i+offsetX, (cardSize)*j+offsetY, cardSize, cardSize);
				g.setColor(Color.black);
				g.drawRect((cardSize)*i+offsetX, (cardSize)*j+offsetY, cardSize, cardSize);
			}
		}
		
		//highlight selection
		if (firstCard != null){
			g.setColor(Color.magenta);
			g.drawRect(firstCard.x*cardSize+offsetX, firstCard.y*cardSize+offsetY, cardSize, cardSize);
		}
		
		// score display
		g.drawString("Score: " + 0, 0, 60);
	}

	
	// Listener for all mouse events
	private class LineListener implements MouseListener,
	MouseMotionListener {
		
		// counts the number of times mouse is clicked
		public void mouseClicked(MouseEvent e) {
			Point pnt = e.getPoint();
			
			Point selection = new Point();
			Card clickCard;
			if (pnt.x>offsetX && pnt.x<maxX && pnt.y>offsetY && pnt.y<maxY){
				selection.x = (pnt.x-offsetX)/cardSize;
				selection.y = (pnt.y-offsetY)/cardSize;
				clickCard = grid[selection.x][selection.y];

				//firstCard = grid[selection.x][selection.y];

			}else{
				return;
			}
			
			//first card
			if (firstCard == null){
				firstCard = clickCard;
				//****** Sylvain: 2013-04-10 ******
				Sound.SELECT.play();

			//card too far away
			}else if(!isAdjacent(firstCard,clickCard)){
				firstCard = clickCard;
				//****** Sylvain: 2013-04-10 ******
				Sound.SELECT.play();
			
			//same card
			}else if(firstCard == clickCard){
				firstCard = null;
				//****** Sylvain: 2013-04-10 ******
				Sound.SELECT.play();
			
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
			
			repaint();
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
		
		// mouse movement 
		public void mouseMoved(MouseEvent e) {
			Point pnt = e.getPoint();
			clickPoint = pnt;;
			//repaint(); // requests JVM to call paint method		
		}
		
		// lists the empty methods from the MouseListener and MouseMotonListener Interface
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}
		
	}
	
	// debug button, resets grid (for now)
	private class ButtonListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			//Build Grid
			grid = BuildGrid();
			//get grid with no matches
			while (CheckForMatches().size()>=1){
				grid = BuildGrid();
				
			}
			repaint();
		}
	}
	
	
}