package GameComponents;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Menus.*;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.Action;

public class GameFrame extends JFrame {
	public GameFrame(){
		// instantiate objects
		ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0,0, ScreenSize.width, ScreenSize.height);
		player = new Player();
		visListen = new Thread(new ShowGameFrame());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		visListen.start();
		gamePanel = new GamePanel();
		moveThread = new Thread(new personMoveListener());
		add(gamePanel);
		validate();
		playerLastX = 0;
		playerLastY = 0;
			try {
				Background = ImageIO.read(new File("assets/grass_18.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	BufferedImage Background;	// background image
	public static Thread visListen;	// 
	Lock moveLock = new ReentrantLock();	// not really used
	Thread moveThread;	// thread for repainting frame
	GamePanel gamePanel;	// game panel for drawing
	private int playerLastX;// = player.getPositionX();
	private int playerLastY;// = player.getPositionY();
	public static Dimension ScreenSize;	// Screen dimension variable
	private static boolean isVis = false;	// used to set gameframe visible in showgame thread
	private Player player;	// main player object
	private ZombiePopulation zombies;	// zombie population object
	private BulletTracking bullets;		// bullet tracking object
	
	private static boolean runCanvasThread = false;
	public static void setVis(boolean vis){
		isVis = vis;
		runCanvasThread = vis;
	}
	// method to get screen width
	public static int getWindowWidth(){
		return (int) ScreenSize.width;
	}
	// method to get screen height
	public static int getWindowHeight(){
		return (int) ScreenSize.height;
	}
	// runnable listening for splash screen close
	public class ShowGameFrame implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!isVis){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			setVisible(true);	// make game frame visible
			setVis(true);	// make game frame visible variable set
			bullets = new BulletTracking();	// create bullet object for shooting

			zombies = new ZombiePopulation();	// create zombie population object

			moveThread.start();	// start move thread
		}
		
	}
	
	public class personMoveListener implements Runnable{
		private int bulletStepCount = 0;	// bullet delay counter variable
		private int bulletDelay = 4;	// delay value for bullet
		public void steBulletDelay(int delay){
			bulletDelay = delay;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(runCanvasThread){
				
				try {
					Thread.sleep(30);
					while(!moveLock.tryLock()){
						Thread.sleep(1000);
					}
					
					//if(player.getPositionX()!= playerLastX || player.getPositionY() != playerLastY){
					moveLock.unlock();
					// if player dead do nothing
					if(player.getHealth() != 0){
						// add bullet based on delay
					if(bulletStepCount >= bulletDelay){
						// add bullet if keys pressed
					if(gamePanel.getBulletX() != 0 || gamePanel.getBulletY() != 0){
						bullets.addBullet(new Bullet(gamePanel.getBulletDirection()));
						bulletStepCount = 0;
					}
					}
					else{
						bulletStepCount++;	// increment bullet delay counter
					}
					player.setPosition();	// setPlayer position
					}
						gamePanel.repaint();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
				}
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Exited");
		}
		
	}
	
	public class GamePanel extends JComponent{
		GamePanel(){
			// Enter key for pause (not implemented)
			this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pause");
			this.getActionMap().put("pause",
					pauseGame
			);
			// R key for restart TODO: implement
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_R,0,true),"restart");
			this.getActionMap().put("restart",
					Restart
			);
			// move left mapped to A key
			// key pressed (start)
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0,false),"moveleft");
			this.getActionMap().put("moveleft",
					moveLeft
			);
			// key released (stop)
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0,true),"releaseleft");
			this.getActionMap().put("releaseleft",
					releaseLeft
			);
			
			// Move right mapped to D key
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D,0,false),"moveright");
			this.getActionMap().put("moveright",
					moveRight
			);
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D,0,true),"releaseright");
			this.getActionMap().put("releaseright",
					releaseRight
			);
			
			// Move up mapped to W key
			// key pressed (start)
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0,false),"moveUp");
			this.getActionMap().put("moveUp",
					moveUp
			);
			// key released (stop)
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0,true),"releaseUp");
			this.getActionMap().put("releaseUp",
					releaseUp
			);
			// Move down mapped to S key
			// key pressed (start)
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S,0,false),"movedown");
			this.getActionMap().put("movedown",
					moveDown
			);		
			// key released (stop)
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S,0,true),"releasedown");
			this.getActionMap().put("releasedown",
					releaseDown
			);
			
			// Shoot functionallity
						// shoot left mapped to left arrow key
						// key pressed (start)
						this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,false),"shootleft");
						this.getActionMap().put("shootleft",
								shootLeft
						);
						// key released (stop)
						this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,true),"releaseShootleft");
						this.getActionMap().put("releaseShootleft",
								releaseShootLeft
						);
						
						// Move right mapped to right arrow key
						this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,false),"shootright");
						this.getActionMap().put("shootright",
								shootRight
						);
						this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,true),"releaseShootright");
						this.getActionMap().put("releaseShootright",
								releaseShootRight
						);
						
						// Move up mapped to up arrow key
						// key pressed (start)
						this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,false),"shootup");
						this.getActionMap().put("shootup",
								shootUp
						);
						// key released (stop)
						this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,true),"releaseShootup");
						this.getActionMap().put("releaseShootup",
								releaseShootUp
						);
						// Move down mapped to down arrow key
						// key pressed (start)
						this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,false),"shootdown");
						this.getActionMap().put("shootdown",
								shootDown
						);		
						// key released (stop)
						this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,true),"releaseShootdown");
						this.getActionMap().put("releaseShootdown",
								releaseShootDown
						);
		}
			// Array for bullet directions set with key presses (0:left 1: up 2: down 3: right)
			private int[] bulletDirection = {0,0,0,0};
			public int[] getBulletDirection(){
				return bulletDirection;
			}
			// get bullet dx (index 0 for left index 2 for right)
			public int getBulletX(){
				return bulletDirection[0]+bulletDirection[2];
			}
			// get bullet dy key press values (index 1 for up index 3 for down)
			public int getBulletY(){
				return bulletDirection[1]+bulletDirection[3];
			}
			// Mapped Actions (generally obviouse by name)
			// Pause game
			Action pauseGame = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.pauseGame = !player.pauseGame;
					Zombie.pauseGame = player.pauseGame;
					//System.out.println(player.pauseGame);
				}
			};
			
			//Respawn player (R key)
			Action Restart = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(player.getHealth() == 0){
						player.respawn();
					}
				}
			};

			// Move Keys ----------------------------------
			Action moveLeft = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(2,1);
				}
			};
			
			Action releaseLeft = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(2,0);
				}
			};
			
			Action moveRight = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(0,1);
				}
			};
			
			Action releaseRight = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(0,0);
				}
			};
			
			Action moveUp = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(1,1);
				}
			};
			
			Action releaseUp = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(1,0);
				}
			};
			Action moveDown = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(3,1);
				}
			};
			
			Action releaseDown = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(3,0);
				}
			};
			
			// Shoot actions -----------------------------------------------
			// shoot left key press
			Action shootLeft = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[0] = 1;
					
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			// shoot left key release
			Action releaseShootLeft = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[0] = 0;
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			// shoot right key press
			Action shootRight = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[2] = 1;
				}
			};
			// shoot right key release
			Action releaseShootRight = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[2] = 0;
				}
			};
			// shoot up key press
			Action shootUp = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[1] = 1;
				}
			};
			// shoot up key release
			Action releaseShootUp = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[1] = 0;
				}
			};
			// shoot down key press
			Action shootDown = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[3] = 1;
				}
			};
			// shoot down key release
			Action releaseShootDown = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[3] = 0;
				}
			};
			// Drawing component for animation and frame update
			public void paintComponent(Graphics g){
				while(!moveLock.tryLock()){
					
				}
				Graphics2D g2 = (Graphics2D) g;	// convert to 2d graphics object
				g2.drawImage(Background, 0, 0, ScreenSize.width, ScreenSize.height, 0, 0, 429, 429, null);	// Draw background
				g2.setColor(Color.WHITE);	// set color for health/kill hud
				g2.fillRect(5, 5, 250, 75);	// draw hud
				g2.setColor(Color.BLACK);	// set color for text in hud
				g2.drawString("Health",50,30);	// health label
				g2.drawString("Kills", 50, 60);	// kills label
				g2.drawString(Integer.toString(player.getKillCount()),100,60);	// draw # kills
				g2.drawRect(99, 11, 101, 22);	// draw outline for health bar
				g2.setColor(Color.GREEN);	// draw health color
				
				g2.fillRect(100, 12, Player.getHealthStat(), 20);	// draw health bar status
				
				// Draw Player Sprite TODO get sprite coordinates, import as png for transparency (player sprite issue)
				if(player.getHealth() != 0){
				g2.drawImage(player.getSprite(), player.getPositionX(), player.getPositionY(), player.getPositionX()+28, player.getPositionY()+36, 166, 230, 194, 275, null);
				}
				// Draw zombies
				for(int i=0; i< zombies.getZombiePopulation();i++){	
				g2.drawImage(zombies.getZombie(i).getSprite(), zombies.getZombie(i).getPositionX(), zombies.getZombie(i).getPositionY(), zombies.getZombie(i).getPositionX()+128, zombies.getZombie(i).getPositionY()+128, zombies.getZombie(i).getSpriteCoord()[0], zombies.getZombie(i).getSpriteCoord()[1], zombies.getZombie(i).getSpriteCoord()[0]+128, zombies.getZombie(i).getSpriteCoord()[1]+128, null);
				// remove zombie from population if dead
				if(zombies.getZombie(i).isDead()){
						zombies.removeZombie(i);
					}
				}
				g2.setColor(Color.YELLOW);	// bullet color
				//draw bullets
				for(int i=0; i< bullets.getNumBullets(); i++ ){
					g2.fillOval(bullets.getBullet(i).getBulletX(), bullets.getBullet(i).getBulletY(), 10, 10);
				}
				// game over text
				if(player.getHealth() == 0){
					g2.setColor(Color.RED);
					g2.setFont(new Font("Impact",Font.ITALIC,30));
					g2.drawString("GAME OVER", (int)(ScreenSize.getWidth()/2-50), (int)(ScreenSize.getHeight()/2-50));
					g2.drawString("Press R to respawn",(int)(ScreenSize.getWidth()/2-100),(int)(ScreenSize.getHeight()/2+50));
				}
				moveLock.unlock();	// not really used
			}
			
	}
	

	
}
