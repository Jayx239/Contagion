package GameComponents;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Player implements Person {
	public Player(){
		ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Instantiate instance variables
		positionX = (int) (ScreenSize.getWidth()/2);	// Character at center of screen X
		positionY = (int) (ScreenSize.getHeight()/2);	// Character at center of screen Y
		maxSpeed = 6;	// Max Speed of character, TODO make this selectable for varying degrees of difficulty
		health = 100;	// Health set to 100 initially
		damageMultiplier = -25; // Rate at which players damages enemys TODO make selectable for difficulty
		// import sprite image
		try {
			playerSprite = ImageIO.read(new File("assets/SoldierSprite.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Instance variables
	private Dimension ScreenSize;	// screen size for positioning
	private static int positionX;	// sprite positioning
	private static int positionY;	// sprite positioning
	private static int spriteWidth = 44;	// sprited width for clipping
	private static int spriteHeight = 28;	// sprite height for clipping
	private int maxSpeed;	// player max speed
	private static int health;	// player health
	private int[] spriteClipCoordinates = new int[4];	/* used to clip part of buffered image for painting, (used to animate sprites, but the quality of
	player sprites was bad so this was just used to get one image*/
	private static int damageMultiplier;	// damage multiplier for zombie damage
	public static boolean pauseGame = false;	// pause game boolean, not yet implemented
	private static int zombiesKilled = 0;	// zombie kill count
	private static int[] moveCoordinates= new int[4];	// [xup,yup,xdown,ydown]
	private int[] faceDirectionXY ={0,0};	// used to set facing direction of player
	private int[] spriteWH = {28,46};
	private int[] spritePosition = {166,230};
	private int[]spritePositionDown = {165,232};
	private int[] spritePositionUp = {580,228};
	private int[] spritePositionLeft={365,212};
	private int[] spritePositionRight = {16,266};
	/*
	 * sprite dimensions
	 * 36x28
	 * x166 y230
	 */
	// Player buffered image sprite
	private BufferedImage playerSprite;
	// Person Interface Methods
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	// unused
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	// methods to print to console if player is dead
	public void die() {
		// TODO Auto-generated method stub
		
	}
	public static void dieStat(){
		
		System.out.println("Dead");
	}

	@Override
	// methods to set health
	public void setHealth(int increment) {
		// TODO Auto-generated method stub
		health+= increment;
	}
	public static void setHealthStat(int increment) {
		// TODO Auto-generated method stub
		if(health >0){
		health += increment;
		}
	}

	@Override
	// get health methods
	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}
	public static int getHealthStat() {
		// TODO Auto-generated method stub
		return health;
	}

	@Override
	// methods for get getting player position (static and nonstatic)
	public int getPositionX() {
		// TODO Auto-generated method stub
		return positionX;
	}
	public static int getPositionXStat(){
		return positionX;
	}
	@Override
	public int getPositionY() {
		// TODO Auto-generated method stub
		return positionY;
	}
	public static int getPositionYStat(){
		return positionY;
	}

	@Override
	// method for moving player
	public void setPosition(){
		int dx = 0;
		int dy = 0;
		// boundary detection X
		if((positionX-ScreenSize.width >= -spriteWidth-maxSpeed && moveCoordinates[0] ==1)){
			dx = -moveCoordinates[2] * maxSpeed;
		}
		else if((positionX <= maxSpeed && moveCoordinates[2] ==1)){
			dx = moveCoordinates[0] * maxSpeed;
		}
		else{
			dx = (moveCoordinates[0] - moveCoordinates[2]) * maxSpeed;
		}
		// boundary detection y
		if((positionY-ScreenSize.height >= -spriteHeight-maxSpeed-50 && moveCoordinates[3] ==1)){
			dy = -moveCoordinates[1] * maxSpeed;
		}
		else if((positionY <= maxSpeed && moveCoordinates[1] ==1)){
			dy = moveCoordinates[3] * maxSpeed;
		}
		else{
			dy = (moveCoordinates[3] - moveCoordinates[1]) * maxSpeed;
			
		}
		faceDirectionXY[0] = dx/maxSpeed;
		faceDirectionXY[1] = dy/maxSpeed;
		
		switch(faceDirectionXY[0]){
		case 0:
				switch(faceDirectionXY[1]){
				case 0:
					break;
					
				case 1:
					spriteWH[0] =28;
					spriteWH[1] = 44;
					spritePosition = spritePositionDown;
				break;
				
				case -1:
					spriteWH[0] =28;
					spriteWH[1] = 44;
					spritePosition = spritePositionUp;
					break;
				}
				
			break;
		case 1:
			switch(faceDirectionXY[1]){
			case 0:
				spriteWH[0] =44;
				spriteWH[1] =28;
				spritePosition = spritePositionRight;
				break;
				
			case 1:
				spriteWH[0] =28;
				spriteWH[1] = 44;
				spritePosition = spritePositionUp;
			break;
			
			case -1:
				spriteWH[0] =28;
				spriteWH[1] = 44;
				spritePosition = spritePositionDown;
				break;
			
			}
			break;
		case -1:
			switch(faceDirectionXY[1]){
			case 0:
				spriteWH[0] =44;
				spriteWH[1] =28;
				spritePosition = spritePositionLeft;
				break;
				
			case 1:
				spriteWH[0] =28;
				spriteWH[1] = 44;
				spritePosition = spritePositionUp;
			break;
			
			case -1:
				spriteWH[0] =28;
				spriteWH[1] = 44;
				spritePosition = spritePositionDown;
				break;
			}
			break;
		}
		// increment positions
		positionX += dx;
		positionY += dy;
		
	}
	// method for respawn
	public void respawn(){
		health = 100;
		
		positionX = (int) (ScreenSize.getWidth()/2);	// Character at center of screen X
		positionY = (int) (ScreenSize.getHeight()/2);	// Character at center of screen Y
	}
	

	@Override
	// method to get sprite for panel painting
	public BufferedImage getSprite() {
		// TODO Auto-generated method stub
		return playerSprite;
	}

	@Override
	// method for getting sprite clip coordinates for animation (need better sprite sheet to make this worth it)
	public int[] getAffineSprite() {
		// TODO Auto-generated method stub
		return spriteClipCoordinates;
	}
	// method to get player width
	public static int getPlayerWidth(){
		return spriteWidth;
	}
	// method to get player height
	public static int getPlayerHeight(){
		return spriteHeight;
	}
	// method to set move coordinates (used by key presses to set move coordinates array)
	public void setMoveCoordinate(int index,int val){
		moveCoordinates[index] = val;
	}
	// method to get player damage multiplier
	public static int getDamage(){
		return damageMultiplier;
	}
	// method to increment kill count
	public static void addKill(){
		zombiesKilled++;
	}
	// method to get kill count for scoreboard
	public static int getKillCount(){
		return zombiesKilled;
	}
	public int[] getSpriteWH(){
		return spriteWH;
	}
	public int[] getSpritePosition(){
		return spritePosition;
	}
	
}
