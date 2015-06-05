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
		try {
			playerSprite = ImageIO.read(new File("assets/SoldierSprite.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Instance variables
	private Dimension ScreenSize;
	private static int positionX;
	private static int positionY;
	private static int spriteWidth = 36;
	private static int spriteHeight = 28;
	private int maxSpeed;
	private static int health;
	private int[] spriteClipCoordinates = new int[4];
	private static int damageMultiplier;
	public static boolean pauseGame = false;
	private static int zombiesKilled = 0;
	private static int[] moveCoordinates= new int[4];	// [xup,yup,xdown,ydown]
	/*
	 * 36x28
	 * x166 y230
	 */
	private BufferedImage playerSprite;
	// Person Interface Methods
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}
	public static void dieStat(){
		
		System.out.println("Dead");
	}

	@Override
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
	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}
	public static int getHealthStat() {
		// TODO Auto-generated method stub
		return health;
	}

	@Override
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
	public void setPosition(){
		int dx = (moveCoordinates[0] - moveCoordinates[2]) * maxSpeed;
		int dy = (moveCoordinates[3] - moveCoordinates[1]) * maxSpeed;
		positionX += dx;
		positionY += dy;
		
	}
	public void respawn(){
		health = 100;
		positionX = (int) (ScreenSize.getWidth()/2);	// Character at center of screen X
		positionY = (int) (ScreenSize.getHeight()/2);	// Character at center of screen Y
	}
	/*public void setPosition(int direction) {
		// TODO Auto-generated method stub
		//-2:left 2:right -1:down 1:up
		switch(direction){
		case -2: positionX -= maxSpeed;
			break;
		case -1: positionY += maxSpeed;
				break;
		case 1: positionY -= maxSpeed;
				break;
		case 2: positionX += maxSpeed;
				break;
		};
	}*/
	

	@Override
	public BufferedImage getSprite() {
		// TODO Auto-generated method stub
		return playerSprite;
	}

	@Override
	public int[] getAffineSprite() {
		// TODO Auto-generated method stub
		return spriteClipCoordinates;
	}
	public static int getPlayerWidth(){
		return spriteWidth;
	}
	public static int getPlayerHeight(){
		return spriteHeight;
	}
	public void setMoveCoordinate(int index,int val){
		moveCoordinates[index] = val;
	}
	public static int getDamage(){
		return damageMultiplier;
	}
	public static void addKill(){
		zombiesKilled++;
	}
	public static int getKillCount(){
		return zombiesKilled;
	}
	
}
