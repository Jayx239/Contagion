package GameComponents;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Menus.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.Action;

public class GameFrame extends JFrame {
	public GameFrame(){
		ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0,0, ScreenSize.width, ScreenSize.height);
		player = new Player();
		Thread visListen = new Thread(new ShowGameFrame());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		visListen.start();
		gamePanel = new GamePanel();
		moveThread = new Thread(new personMoveListener());
		add(gamePanel);
		validate();
		playerLastX = 0;
		playerLastY = 0;
			
	}
	Lock moveLock = new ReentrantLock();
	Thread moveThread;
	GamePanel gamePanel;
	private int playerLastX;// = player.getPositionX();
	private int playerLastY;// = player.getPositionY();
	public static Dimension ScreenSize;
	private static boolean isVis = false;
	private Player player;
	private ZombiePopulation zombies;
	private BulletTracking bullets;
	
	private static boolean runCanvasThread = false;
	public static void setVis(boolean vis){
		isVis = vis;
		runCanvasThread = vis;
	}
	
	public static int getWindowWidth(){
		return (int) ScreenSize.width;
	}
	public static int getWindowHeight(){
		return (int) ScreenSize.height;
	}
	
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
			setVisible(true);
			setVis(true);
			bullets = new BulletTracking();

			zombies = new ZombiePopulation();

			moveThread.start();
		}
		
	}
	
	public class personMoveListener implements Runnable{
		private int bulletStepCount = 0;
		private int bulletDelay = 4;
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
					if(bulletStepCount >= bulletDelay){
					if(gamePanel.getBulletX() != 0 || gamePanel.getBulletY() != 0){
						bullets.addBullet(new Bullet(gamePanel.getBulletDirection()));
						bulletStepCount = 0;
					}
					}
					else{
						bulletStepCount++;
					}
					player.setPosition();
						//gameCanvas.repaint();
						gamePanel.repaint();
						//playerLastX = player.getPositionX();
						//playerLastY = player.getPositionY();
						//System.out.println("Repainted");
					//}
						//System.out.println("Running");
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
			// Enter key for pause
			this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pause");
			this.getActionMap().put("pause",
					pauseGame
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
			private int[] bulletDirection = {0,0,0,0};
			public int[] getBulletDirection(){
				return bulletDirection;
			}
			public int getBulletX(){
				return bulletDirection[0]+bulletDirection[2];
			}
			public int getBulletY(){
				return bulletDirection[1]+bulletDirection[3];
			}
			// Mapped Actions
			// Pause game
			Action pauseGame = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.pauseGame = !player.pauseGame;
					Zombie.pauseGame = player.pauseGame;
					//System.out.println(player.pauseGame);
				}
			};

			Action moveLeft = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(2,1);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			
			Action releaseLeft = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(2,0);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			
			Action moveRight = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(0,1);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			
			Action releaseRight = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(0,0);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			
			Action moveUp = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(1,1);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			
			Action releaseUp = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(1,0);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			Action moveDown = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(3,1);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			
			Action releaseDown = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setMoveCoordinate(3,0);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			
			// Shoot actions -----------------------------------------------
			Action shootLeft = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[0] = 1;
					
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			
			Action releaseShootLeft = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[0] = 0;
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			
			Action shootRight = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[2] = 1;
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			
			Action releaseShootRight = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[2] = 0;
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			
			Action shootUp = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[1] = 1;
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			
			Action releaseShootUp = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[1] = 0;
					
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			Action shootDown = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[3] = 1;
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			
			Action releaseShootDown = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bulletDirection[3] = 0;
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			
			public void paintComponent(Graphics g){
				while(!moveLock.tryLock()){
					
				}
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.BLACK);
				g2.drawString("Health",50,30);
				g2.drawString("Kills", 50, 60);
				g2.drawString(Integer.toString(player.getKillCount()),100,60);
				g2.drawRect(99, 11, 101, 22);
				g2.setColor(Color.GREEN);
				
				g2.fillRect(100, 12, Player.getHealthStat(), 20);
				
				// Draw Player Sprite TODO get sprite coordinates, import as png for transparency (player sprite issue)
				g2.drawImage(player.getSprite(), player.getPositionX(), player.getPositionY(), player.getPositionX()+28, player.getPositionY()+36, 166, 230, 194, 275, null);
				for(int i=0; i< zombies.getZombiePopulation();i++){
					
				g2.drawImage(zombies.getZombie(i).getSprite(), zombies.getZombie(i).getPositionX(), zombies.getZombie(i).getPositionY(), zombies.getZombie(i).getPositionX()+128, zombies.getZombie(i).getPositionY()+128, zombies.getZombie(i).getSpriteCoord()[0], zombies.getZombie(i).getSpriteCoord()[1], zombies.getZombie(i).getSpriteCoord()[0]+128, zombies.getZombie(i).getSpriteCoord()[1]+128, null);
				if(zombies.getZombie(i).isDead()){
						zombies.removeZombie(i);
					}
				}
				g2.setColor(Color.YELLOW);
				for(int i=0; i< bullets.getNumBullets(); i++ ){
					g2.fillOval(bullets.getBullet(i).getBulletX(), bullets.getBullet(i).getBulletY(), 10, 10);
				}
				moveLock.unlock();
			}
			
	}
	

	
}
