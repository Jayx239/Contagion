package GameComponents;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
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
		gameCanvas = new GameCanvas();
		moveThread = new Thread(new personMoveListener());
		add(gamePanel);
		add(gameCanvas);
		gameCanvas.setFocusable(false);
		//gamePanel.requestFocus();
		validate();
		playerLastX = 0;
		playerLastY = 0;
			
	}
	Lock moveLock = new ReentrantLock();
	Thread moveThread;
	GamePanel gamePanel;
	GameCanvas gameCanvas;
	private int playerLastX;// = player.getPositionX();
	private int playerLastY;// = player.getPositionY();
	Dimension ScreenSize;
	private static boolean isVis = false;
	private Player player;
	private ZombiePopulation zombies;
	
	private static boolean runCanvasThread = false;
	public static void setVis(boolean vis){
		isVis = vis;
		runCanvasThread = vis;
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
			moveThread.start();
			zombies = new ZombiePopulation();
		}
		
	}
	
	public class personMoveListener implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(runCanvasThread){
				
				try {
					Thread.sleep(30);
					while(!moveLock.tryLock()){
						Thread.sleep(10);
					}
					//if(player.getPositionX()!= playerLastX || player.getPositionY() != playerLastY){
					moveLock.unlock();
						gameCanvas.repaint();
						
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
			this.getInputMap().put(KeyStroke.getKeyStroke("A"),"moveleft");
			this.getActionMap().put("moveleft",
					moveLeft
			);
			// Move right mapped to D key
			this.getInputMap().put(KeyStroke.getKeyStroke("D"),"moveright");
			this.getActionMap().put("moveright",
					moveRight
			);
			// Move up mapped to W key
			this.getInputMap().put(KeyStroke.getKeyStroke("W"),"moveUp");
			this.getActionMap().put("moveUp",
					moveUp
			);
			// Move down mapped to S key
			this.getInputMap().put(KeyStroke.getKeyStroke("S"),"movedown");
			this.getActionMap().put("movedown",
					moveDown
			);		
		}
			LinkedList<Integer> keysDown = new LinkedList<Integer>();
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
					player.setPosition(-2);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			Action moveRight = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setPosition(2);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			Action moveUp = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setPosition(1);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			Action moveDown = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setPosition(-1);
					//System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			
			
	}
	public class GameCanvas extends Canvas{
		public GameCanvas(){
			
			this.setBounds(0, 0,100,100);
			repaint();
			setVisible(true);
			//moveListener = new Thread(new personMoveListener());
			//moveListener.start();

		}
		public void paint(Graphics g){
while(!moveLock.tryLock()){
				
			}
			Graphics2D g2 = (Graphics2D) g;
			// Draw Player Sprite TODO get sprite coordinates, import as png for transparency (player sprite issue)
			
			g2.drawImage(player.getSprite(), player.getPositionX(), player.getPositionY(), player.getPositionX()+28, player.getPositionY()+36, 166, 230, 194, 275, null);
			for(int i=0; i< zombies.getZombiePopulation();i++){
			g2.drawImage(zombies.getZombie(i).getSprite(), zombies.getZombie(i).getPositionX(), zombies.getZombie(i).getPositionY(), zombies.getZombie(i).getPositionX()+128, zombies.getZombie(i).getPositionY()+128, 0, 0, 128, 128, null);
			}
			moveLock.unlock();
		}
			Thread moveListener;
		
		
		
	}
		/*
		public class redrawCanvasRunnable implements Runnable{
			public redrawCanvasRunnable(){
				
			}
			public void run(){
				
			}
		}
		*/
	

	
}
