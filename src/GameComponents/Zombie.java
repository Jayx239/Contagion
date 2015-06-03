package GameComponents;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Zombie implements Person {
	public Zombie(BufferedImage Sprite){
		ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Instantiate instance variables
		setEntryPosition();	// Set position of screen that zombies start at 
		maxSpeed = (int)Math.ceil((Math.random()*2));	// Max Speed of character, TODO make this selectable for varying degrees of difficulty
		health = 100;	// Health set to 100 initially
		healthDepletionRate = 10; // Rate at which players health depletes TODO make selectable for difficulty
		damageMultiplier = -5;
		zombieSprite = Sprite;
		ZombieChaseThread = new Thread(new zombieMoveRunnable());
		ZombieChaseThread.start();
	}
	public Thread ZombieChaseThread;
	private Dimension ScreenSize;
	// Position = top left
	private int positionX;
	private int positionY;
	// Zombie cent coordinates x+64xy+64
	private static int zombieSpriteWidth = 64;
	private static int zombieSpriteHeight = 64;
	private int maxSpeed;
	private int health;
	private int healthDepletionRate;
	public static boolean pauseGame;
	private int damageMultiplier;
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
		case 1: this.positionX = 0;
				this.positionY = (int)(Math.random()*ScreenSize.getHeight());
				break;
		case 2: this.positionY = 0;
				this.positionX = (int)(Math.random()*ScreenSize.getWidth());
				break;
		case 3: this.positionX = (int)(ScreenSize.getWidth());
				this.positionY = (int)(Math.random()*ScreenSize.getHeight());
				break;
		case 4: this.positionY = (int)(ScreenSize.getHeight());
				this.positionX = (int)(Math.random()*ScreenSize.getWidth());
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
		Player.setHealthStat(damageMultiplier);
		if(Player.getHealthStat() <= 0){
			Player.dieStat();
		}
		else{
			System.out.println(Player.getHealthStat());
		}
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		ZombieChaseThread.interrupt();
	}

	@Override
	public void setHealth(int increment) {
		// TODO Auto-generated method stub
		health+= increment;
		if(health <= 0){
			die();
		}
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPositionX() {
		// TODO Auto-generated method stub
		return positionX;
	}

	@Override
	public int getPositionY() {
		// TODO Auto-generated method stub
		return positionY;
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
		//TODO make 128/2 variable for scaling(image size/2)
		// Zombie below player
		if(this.getPositionY()>Player.getPositionYStat()-((zombieSpriteHeight))){
				setPositionY(-maxSpeed);
		}
		//Zombie above player
		if(this.getPositionY()<Player.getPositionYStat()-(zombieSpriteHeight)){
				setPositionY(+maxSpeed);
			}
		
		// Zombie to right of player
		if((this.getPositionX())>Player.getPositionXStat()-(zombieSpriteWidth)){
				setPositionX(-maxSpeed);
		}
		// Zombie to left of player
		if ((this.getPositionX())<Player.getPositionXStat()-(zombieSpriteWidth)){
				setPositionX(+maxSpeed);
		}
		
	}
	// Zombie Chase runnable for chasing player thread
	private class zombieMoveRunnable implements Runnable{
		zombieMoveRunnable(){
			
		}
		public void run(){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(;;){
				chase();
				if(Math.abs(positionX-Player.getPositionXStat()) <= (Player.getPlayerWidth()+zombieSpriteHeight)  && Math.abs(positionY-Player.getPositionYStat()) <= (Player.getPlayerWidth()+zombieSpriteWidth)){
					attack();
				}
				try {
					Thread.sleep((int)(100/maxSpeed));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			}
		}
	}
	public Thread getThread(){
		return ZombieChaseThread;
	}

	@Override
	public void setPosition() {
		// TODO Auto-generated method stub
		
	}
	public static int getSpriteDim(){
		return zombieSpriteWidth;
	}

}
