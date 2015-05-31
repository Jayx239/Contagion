package GameComponents;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Zombie implements Person {
	public Zombie(){
		ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Instantiate instance variables
		setEntryPosition();	// Set position of screen that zombies start at 
		maxSpeed = 5;	// Max Speed of character, TODO make this selectable for varying degrees of difficulty
		health = 100;	// Health set to 100 initially
		healthDepletionRate = 5; // Rate at which players health depletes TODO make selectable for difficulty
		try {
			zombieSprite = ImageIO.read(new File("assets/ZombieSprites.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ZombieChaseThread = new Thread(new zombieMoveRunnable());
		ZombieChaseThread.start();
	}
	Thread ZombieChaseThread;
	private Dimension ScreenSize;
	private int positionX;
	private int positionY;
	private int maxSpeed;
	private int health;
	private int healthDepletionRate;
	public static boolean pauseGame;
	/*
	 * Zombie Sprite dimensions http://opengameart.org/content/zombie-sprites
	 *128x128 tiles.  8 direction, 36 frames per direction.
	 *Stance (4 frames)
	 *Lurch (8 frames)
	 *Slam (4 frames)
	 *Bite (4 frames)
	 *Block (2 frames)
	 *Hit and Die (6 frames)
	 *Critical Death (8 frames) 
	 */
	private BufferedImage zombieSprite;
	
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
	public void setPosition(int direction) {
		// TODO Auto-generated method stub
		//-2:left 2:right -1:down 1:up
	}
	public void setPositionY(int increment){
		positionY += increment; 
	}
	public void setPositionX(int increment){
		positionX += increment; 
	}

	@Override
	public BufferedImage getSprite() {
		// TODO Auto-generated method stub
		return zombieSprite;
	}

	@Override
	public int[] getAffineSprite() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void chase(){
		if(getPositionY()>Player.getPositionYStat()){
			if(maxSpeed<(getPositionY()-Player.getPositionYStat())){
				setPositionY(-(getPositionY()-Player.getPositionYStat()));
			}
			else{
				setPositionY(-maxSpeed);
			}
		}
		else{
			if(maxSpeed>(getPositionY()-Player.getPositionYStat())){
				setPositionY((getPositionY()-Player.getPositionYStat()));
			}
			else{
				setPositionY(maxSpeed);
			}
		}
		if(getPositionX()>Player.getPositionXStat()){
			if(maxSpeed<(getPositionX()-Player.getPositionXStat())){
				setPositionX(-(getPositionX()-Player.getPositionXStat()));
			}
			else{
				setPositionX(-maxSpeed);
			}
		}
		else{
			if(maxSpeed>(getPositionX()-Player.getPositionXStat())){
				setPositionX((getPositionX()-Player.getPositionXStat()));
			}
			else{
				setPositionX(maxSpeed);
			}
		}
	}
	public class zombieMoveRunnable implements Runnable{
		zombieMoveRunnable(){
			
		}
		public void run(){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(;;){
				chase();
				try {
					Thread.sleep((int)(30/maxSpeed));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			}
		}
	}

}
