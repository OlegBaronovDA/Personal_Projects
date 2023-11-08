package snakeGame;

public class SnakeBody {
	
	int startXPos = 200;
	int startYPos = 440;
	
	int xPos;
	int yPos;
	
	SnakeBody next;
	
	public void snakeBody(int x, int y, SnakeBody n) {
		this.xPos = x;
		this.yPos = y;
		this.next = n;
		
	}

}
