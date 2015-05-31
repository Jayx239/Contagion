package GameComponents;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import Menus.*;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	public GameFrame(){
		ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0,0, ScreenSize.width, ScreenSize.height);
		player = new Player();
		zombie = new Zombie();
		Thread visListen = new Thread(new ShowGameFrame());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		visListen.start();
		GameCanvas gameCanvas = new GameCanvas();
		add(gameCanvas);
		validate();
	}
	Dimension ScreenSize;
	private static boolean isVis = false;
	private Player player;
	private Zombie zombie;
	public static void setVis(boolean vis){
		isVis = vis;
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
	public class GameCanvas extends Canvas{
		public GameCanvas(){
			this.setBounds(0, 0,(int)ScreenSize.getWidth(),(int)ScreenSize.getHeight());
			repaint();
			validate();
			setVisible(true);
			//Thread redrawCanvas = new Thread()
		}
		public void paint(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			// Draw Player Sprite TODO get sprite coordinates, import as png for transparency
			g2.drawImage(player.getSprite(), player.getPositionX(), player.getPositionY(), player.getPositionX()+28, player.getPositionX()+36, 166, 230, 194, 266, null);
			g2.drawImage(zombie.getSprite(), 200, 200, 328, 328, 0, 0, 128, 128, null);
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
}
