package GameComponents;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

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
		zombie = new Zombie();
		Thread visListen = new Thread(new ShowGameFrame());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		visListen.start();
		gamePanel = new GamePanel();
		gameCanvas = new GameCanvas();
		Thread moveListner = new Thread(new personMoveListener());
		add(gamePanel);
		add(gameCanvas);
		gameCanvas.setFocusable(false);
		//gamePanel.requestFocus();
		validate();
		playerLastX = 0;
		playerLastY = 0;
			
	}
	GamePanel gamePanel;
	GameCanvas gameCanvas;
	private int playerLastX;// = player.getPositionX();
	private int playerLastY;// = player.getPositionY();
	Dimension ScreenSize;
	private static boolean isVis = false;
	private Player player;
	private Zombie zombie;
	private static boolean runCanvasThread = false;
	public static void setVis(boolean vis){
		isVis = vis;
		runCanvasThread = !runCanvasThread;
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
		}
		
	}
	
	public class personMoveListener implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(runCanvasThread){
				try {
					Thread.sleep(30);
					if(player.getPositionX()!= playerLastX || player.getPositionY() != playerLastY){
						repaint();
						playerLastX = player.getPositionX();
						playerLastY = player.getPositionY();
						System.out.println("Repainted");
					}
						System.out.println("Running");
						
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Exited");
			}
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
		
			// Mapped Actions
			// Pause game
			Action pauseGame = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.pauseGame = !player.pauseGame;
					zombie.pauseGame = player.pauseGame;
					System.out.println(player.pauseGame);
				}
			};

			Action moveLeft = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setPosition(-2);
					System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			Action moveRight = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setPosition(2);
					System.out.println(player.getPositionX()+ " " + player.getPositionY());

				}
			};
			Action moveUp = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setPosition(1);
					System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			Action moveDown = new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e) {
					player.setPosition(-1);
					System.out.println(player.getPositionX()+ " " + player.getPositionY());
				}
			};
			
	}
	public class GameCanvas extends Canvas{
		public GameCanvas(){
			this.setBounds(0, 0,(int)ScreenSize.getWidth(),(int)ScreenSize.getHeight());
			repaint();
			setVisible(true);
			//moveListener = new Thread(new personMoveListener());
			//moveListener.start();
			validate();
		}
		public void paint(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			// Draw Player Sprite TODO get sprite coordinates, import as png for transparency
			g2.drawImage(player.getSprite(), player.getPositionX(), player.getPositionY(), player.getPositionX()+28, player.getPositionY()+36, 166, 230, 194, 266, null);
			g2.drawImage(zombie.getSprite(), 200, 200, 328, 328, 0, 0, 128, 128, null);
			validate();
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
