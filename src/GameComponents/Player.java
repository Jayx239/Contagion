package GameComponents;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Player implements Person {
	public Player(){
		Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Instantiate instance variables
		positionX = (int) (ScreenSize.getWidth()/2);	// Character at center of screen X
		positionY = (int) (ScreenSize.getHeight()/2);	// Character at center of screen Y
		maxSpeed = 5;	// Max Speed of character, TODO make this selectable for varying degrees of difficulty
		health = 100;	// Health set to 100 initially
		damageMultiplier = 5; // Rate at which players damages enemys TODO make selectable for difficulty
		try {
			playerSprite = ImageIO.read(new File("assets/SoldierSprite.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Instance variables
	private static int positionX;
	private static int positionY;
	private int maxSpeed;
	private static int health;
	/*
	 * 36x28
	 * x166 y230
	 */
	private BufferedImage playerSprite;
	private int damageMultiplier;
	
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

	@Override
	public void setHealth(int increment) {
		// TODO Auto-generated method stub
		
	}
	public void setHealthStat(int increment) {
		// TODO Auto-generated method stub
		setHealth(increment);
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
		return positionY;
	}
	public static int getPositionXStat(){
		return positionY;
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
	public void setPosition() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage getSprite() {
		// TODO Auto-generated method stub
		return playerSprite;
	}
}
