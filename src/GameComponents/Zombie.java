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
		//healthDepletionRate = 10; // Rate at which players health depletes TODO make selectable for difficulty
		damageMultiplier = -2;
		zombieSprite = Sprite;
		ZombieChaseThread = new Thread(new zombieMoveRunnable());
		ZombieChaseThread.start();
		spriteCoord[0] = 0;
		spriteCoord[1] = 0;
	}
	// Instance variables
	// Sprite handling
	private int[] spriteCoord = new int[2];
	private int[] spriteWalkX = {128*5,128*6,128*7,128*8,128*9,128*10,128*11};
	private int[] spriteWalkY = {0,128*1,128*2,128*3,128*4,128*5,128*6,128*7};
	private int[] zombieDieX = {128*28,128*29,128*30,128*31,128*32,128*33,128*34,128*35,128*36};
	private int[] zombieAttackX = {128*12,128*13,128*14,128*15,128*16,128*17,128*18,128*19,128*20};
	private int[] faceDirectionXY ={0,0};
	
	private int walkCount = 0;
	public Thread ZombieChaseThread;
	private Thread ZombieDieThread = new Thread(new DieRunnable());
	private Dimension ScreenSize;
	// Position = top left
	private int positionX;
	private int positionY;
	// Zombie cent coordinates x+64xy+64
	private static int zombieSpriteWidth = 64;
	private static int zombieSpriteHeight = 64;
	private int maxSpeed;
	private int health;
	//private int healthDepletionRate; // not used player sets damage using player multiplier
	public static boolean pauseGame;
	private int damageMultiplier;
	public boolean Dead = false;
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
		Thread attack = new Thread(new attackRunnable());
		attack.start();
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
				chase();
				int dirX;
				int dirY;
				try{
					dirX=positionX-Player.getPositionXStat()/Math.abs(positionX-Player.getPositionXStat());
				}
				catch(ArithmeticException e){
					dirX= 0;
				}
				
				try{
					dirY=positionX-Player.getPositionXStat()/Math.abs(positionX-Player.getPositionXStat());
				}
				catch(ArithmeticException e){
					dirY= 0;
				}
				
				switch(dirX){
				case 0:
						
						switch(dirY){
						case 0:
							attack();
							//break;
						
						case 1:
								if(Math.abs(positionY-Player.getPositionYStat()) <=Player.getPlayerHeight()/2+50){
									attack();
								}
								//break;
							case -1:
								if(Math.abs(positionY-Player.getPositionYStat()) <=Player.getPlayerHeight()/2+100){
									attack();
								}
								//break;
						}
					
				case 1:
					if(positionX-Player.getPositionXStat()<= Player.getPlayerWidth()/2+50){
						
						switch(dirY){
						case 0:
							attack();
							//break;
						
						case 1:
								if(Math.abs(positionY-Player.getPositionYStat()) <=Player.getPlayerHeight()/2+50){
									attack();
								}
								//break;
							case -1:
								if(Math.abs(positionY-Player.getPositionYStat()) <=Player.getPlayerHeight()/2+100){
									attack();
								}
								//break;
						}
					}
						//break;
					case -1:
						if(positionX-Player.getPositionXStat()>= Player.getPlayerWidth()/2+75){
							
							switch(dirY){
							case 0:
								attack();
								//break;
							
							case 1:
									if(Math.abs(positionY-Player.getPositionYStat()) <=Player.getPlayerHeight()/2+50){
										attack();
									}
									//break;
								case -1:
									if(Math.abs(positionY-Player.getPositionYStat()) <=Player.getPlayerHeight()/2+100){
										attack();
									}
									//break;
							}
							}
					//break;
				}
				
				try {
					Thread.sleep((int)(100/maxSpeed));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return;
				}
			}
		}
	}
	
	public class DieRunnable implements Runnable{
		DieRunnable(){
			
		}
		public void run(){
			Player.addKill();
			for(int i = 0; i<zombieDieX.length;i++){
				spriteCoord[0] = zombieDieX[i]; 
			try {
				Thread.sleep((int)(100/maxSpeed));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();

				setAbsolutePositionY(-100);
				setAbsolutePositionX(-100);
				return;
			}
			}
			setAbsolutePositionY(-100);
			setAbsolutePositionX(-100);
		}
	}
	
	public class attackRunnable implements Runnable{
		attackRunnable(){
			
		}
		public void run(){
			for(int i = 0; i<zombieAttackX.length;i++){
				spriteCoord[0] = zombieAttackX[i]; 
			try {
				Thread.sleep((int)(300/maxSpeed));
			} catch (InterruptedException e) {
				e.printStackTrace();
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
	public int[] getSpriteCoord(){
		return spriteCoord;
	}
	public boolean isDead(){
		return Dead;
	}
}
