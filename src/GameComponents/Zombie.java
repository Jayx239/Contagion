package GameComponents;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Zombie implements Person {
	public Zombie(){
		ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Instantiate instance variables
		setEntryPosition();	// Set position of screen that zombies start at 
		maxSpeed = 5;	// Max Speed of character, TODO make this selectable for varying degrees of difficulty
		health = 100;	// Health set to 100 initially
		healthDepletionRate = 5; // Rate at which players health depletes TODO make selectable for difficulty
	}
	private Dimension ScreenSize;
	private int positionX;
	private int positionY;
	private int maxSpeed;
	private int health;
	private int healthDepletionRate;
	
	// Method to randomly select which position zombies spawn at
	private void setEntryPosition(){
		int entrySide = (int) (4*Math.random());
		switch(entrySide){
		case 1: positionX = 0;
				positionY = (int)(Math.random()*ScreenSize.getHeight());
				break;
		case 2: positionY = 0;
				positionX = (int)(Math.random()*ScreenSize.getWidth());
				break;
		case 3: positionX = (int)(ScreenSize.getWidth());
				positionY = (int)(Math.random()*ScreenSize.getHeight());
				break;
		case 4: positionY = (int)(ScreenSize.getHeight());
				positionX = (int)(Math.random()*ScreenSize.getWidth());
				break;
		}
	}
	
	// Person inteface methods
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
