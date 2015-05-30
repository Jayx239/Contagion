package GameComponents;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;


public class Player implements Person {
	public Player(){
		Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Instantiate instance variables
		positionX = (int) (ScreenSize.getWidth()/2);	// Character at center of screen X
		positionY = (int) (ScreenSize.getHeight()/2);	// Character at center of screen Y
		maxSpeed = 5;	// Max Speed of character, TODO make this selectable for varying degrees of difficulty
		health = 100;	// Health set to 100 initially
		healthDepletionRate = 5; // Rate at which players health depletes TODO make selectable for difficulty
	}
	// Instance variables
	private int positionX;
	private int positionY;
	private int maxSpeed;
	private int health;
	private int healthDepletionRate;
	
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

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPositionX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPositionY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPosition() {
		// TODO Auto-generated method stub
		
	}
}
