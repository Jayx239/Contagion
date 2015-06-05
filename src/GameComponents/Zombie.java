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
		maxSpeed = (int)Math.ceil((Math.random()*4));	// Max Speed of character, TODO make this selectable for varying degrees of difficulty
		health = 100;	// Health set to 100 initially
		//healthDepletionRate = 10; // Rate at which players health depletes TODO make selectable for difficulty
		damageMultiplier = -2;
		zombieSprite = Sprite;
		ZombieChaseThread = new Thread(new zombieMoveRunnable());
		ZombieChaseThread.start();
		spriteCoord[0] = 0;
		spriteCoord[1] = 0;
	}
	// Instance variables
	// Sprite handling (Sprites animated by cycling through sprite sheets in parts using a thread with a delay)
	private int[] spriteCoord = new int[2];	// x,y coordinates of sprite on sprite sheet
	private int[] spriteWalkX = {128*5,128*6,128*7,128*8,128*9,128*10,128*11};	// coordinates for walking animation
	private int[] spriteWalkY = {0,128*1,128*2,128*3,128*4,128*5,128*6,128*7};	// y coodinates for zombie walk direction
	private int[] zombieDieX = {128*28,128*29,128*30,128*31,128*32,128*33,128*34,128*35,128*36};	// zombie x die animation coordinates
	private int[] zombieAttackX = {128*12,128*13,128*14,128*15,128*16,128*17,128*18,128*19,128*20};	// zombie x attack animation coordinates
	
	private int[] faceDirectionXY ={0,0};	// used to set facing direction of zombie
	private boolean attacking = false;	// set when zombie is attacking to pause move thread
	private int walkCount = 0;
	public Thread ZombieChaseThread;	// thread for zombie chase functionallity
	private Thread ZombieDieThread = new Thread(new DieRunnable());	// thread for zombie die animation
	private Dimension ScreenSize;	// screen dimensions
	// Position = top left
	private int positionX;
	private int positionY;
	// Zombie cent coordinates x+64xy+64
	// actually center zombie position relative to top right
	private static int zombieSpriteWidth = 64;	// width
	private static int zombieSpriteHeight = 64;	// height
	private int maxSpeed;	// maxspeed of zombie
	private int health;	// health of zombie
	//private int healthDepletionRate; // not used player sets damage using player multiplier
	public static boolean pauseGame;	// not implemented
	private int damageMultiplier;	// damage inflicted on player with each hit
	public boolean Dead = false;	// used as flag to remove zombie from zombie population thread
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
		attacking = true;
		Thread attack = new Thread(new attackRunnable());
		attack.start();
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
		ZombieDieThread.start();
	}

	@Override
	public void setHealth(int increment) {
		// TODO Auto-generated method stub
		health+= increment;
		if(health <= 0){
			die();
		}
		System.out.println(getHealth());
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
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
	public void setAbsolutePositionY(int position){
		positionY = position;
	}
	public void setPositionX(int increment){
		positionX += increment;
	}
	public void setAbsolutePositionX(int position){
		positionY = position;
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
	// method for Zombie sprite animation for movement
	private void chase(){
		//TODO make 128/2 variable for scaling(image size/2)
		// Zombie below player
		if(this.getPositionY()>Player.getPositionYStat()-((zombieSpriteHeight))){
				setPositionY(-maxSpeed);
				faceDirectionXY[1] = 1;
		}
		//Zombie above player
		else if(this.getPositionY()<Player.getPositionYStat()-(zombieSpriteHeight)){
				setPositionY(+maxSpeed);
				faceDirectionXY[1] = -1;
			}
		else{
			faceDirectionXY[1] = 0;
		}
		
		// Zombie to right of player
		if((this.getPositionX())>Player.getPositionXStat()-(zombieSpriteWidth)){
				setPositionX(-maxSpeed);
				faceDirectionXY[0] = -1;
		}
		// Zombie to left of player
		else if ((this.getPositionX())<Player.getPositionXStat()-(zombieSpriteWidth)){
				setPositionX(+maxSpeed);
				faceDirectionXY[0] = 1;
		}
		else{
			faceDirectionXY[0] = 0;
		}
		// determine direction zombie is facing
		switch(faceDirectionXY[0]){
		case 0:
				switch(faceDirectionXY[1]){
				case 0:
					break;
					
				case 1:
					spriteCoord[1] = spriteWalkY[2];
				break;
				
				case -1:
						spriteCoord[1] = spriteWalkY[6];
					break;
				}
			break;
		case 1:
			switch(faceDirectionXY[1]){
			case 0:
				spriteCoord[1] = spriteWalkY[4];
				break;
				
			case 1:
				spriteCoord[1] = spriteWalkY[3];
			break;
			
			case -1:
					spriteCoord[1] = spriteWalkY[5];
				break;
			}
			break;
		case -1:
			switch(faceDirectionXY[1]){
			case 0:
				spriteCoord[1] = spriteWalkY[1];
				break;
				
			case 1:
				spriteCoord[1] = spriteWalkY[0];
			break;
			
			case -1:
					spriteCoord[1] = spriteWalkY[7];
				break;
			}
			break;
		}
		
		spriteCoord[0] = spriteWalkX[walkCount];
		if(walkCount < 6){
			walkCount++;
		}
		else{
			walkCount = 0;
		}
	}
	// Zombie Chase runnable for chasing player thread
	private class zombieMoveRunnable implements Runnable{
		zombieMoveRunnable(){
			
		}
		public void run(){
			for(;;){
				
				if(!isAttacking()){
				boolean attackingSet = false;
				int zombieBodyXCenter = positionX+57;
				int zombieBodyYCenter = positionY+28;
				// Proximity detection for attacking
				if(Math.abs(Player.getPositionXStat()-(zombieBodyXCenter)) <= 30){
					if(zombieBodyYCenter < Player.getPositionYStat()){
						if(Math.abs(Player.getPositionYStat()-zombieBodyYCenter) <=58){
							attack();
							attackingSet = true;	
					}
					else{
					if(Math.abs(Player.getPositionYStat()-zombieBodyYCenter) <=40){
						attack();
						attackingSet = true;
					}
					}
				}
				}
				// if not attacking chase
				if(!isAttacking()){
					chase();
				}
				// sleep based on zombie speed
				try {
					Thread.sleep((int)(100/maxSpeed));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return;
				}
				}
				else{
					//sleep longer when attacking
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
				}
			}
		}
	}
	// runnable for zombie death
	public class DieRunnable implements Runnable{
		DieRunnable(){
			
		}
		public void run(){
			Player.addKill();	// increment kill count
			for(int i = 0; i<zombieDieX.length;i++){
				spriteCoord[0] = zombieDieX[i]; 
			try {
				Thread.sleep((int)(100/maxSpeed));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// move off screen (fixes invisible zombie issue)
				setAbsolutePositionY(-100);
				setAbsolutePositionX(-100);
				return;
			}
			}
			// move off screen (fixes invisible zombie issue)
			setAbsolutePositionY(-100);
			setAbsolutePositionX(-100);
		}
	}
	// runnable for zombie attacking
	public class attackRunnable implements Runnable{
		attackRunnable(){
			
		}
		public void run(){
			// animate
			for(int i = 0; i<zombieAttackX.length;i++){
				spriteCoord[0] = zombieAttackX[i]; 
			try {
				Thread.sleep((int)(300/maxSpeed));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
			attacking = false;
		}
	}
	// method to get zombie chase thread
	public Thread getThread(){
		return ZombieChaseThread;
	}

	@Override
	public void setPosition() {
		// TODO Auto-generated method stub
		
	}
	// method for getting zombie sprites dimensions
	public static int getSpriteDim(){
		return zombieSpriteWidth;
	}
	// method to get clipping coordinate for animation
	public int[] getSpriteCoord(){
		return spriteCoord;
	}
	// flag to start death animation
	public boolean isDead(){
		return Dead;
	}
	// method to indicate if zombie is attacking (pauses move thread)
	public boolean isAttacking(){
		return attacking;
	}
}
