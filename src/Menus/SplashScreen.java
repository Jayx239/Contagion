package Menus;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import GameComponents.GameFrame;
//http://zetcode.com/tutorials/javagamestutorial/movingsprites/
public class SplashScreen extends JFrame{
	SplashScreen(){
		Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(ScreenSize.width/2-ScreenSize.width/8,ScreenSize.height/2-ScreenSize.height/8 , ScreenSize.width/4, ScreenSize.height/4);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		splashPanel = new SplashScreenPanel();
		this.add(splashPanel);
		
		visibilityRun = new Visibility();
		visibilityThread = new Thread(visibilityRun);

		show = true;
		visibilityThread.start();
		this.setVisible(true);
		//splashPanel.requestFocus();
		validate();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private static Thread visibilityThread;
	private Visibility visibilityRun;
	private static boolean show;
	public static void setShow(){
		show = !show;
	}
	public static int startVisThread(){
		visibilityThread.start();
		return 1;
	}
	public class Visibility implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			setVisible(true);
			while(show){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			setVisible(false);
			GameFrame.setVis(true);
		}
		
	}
	SplashScreenPanel splashPanel;
	public void hideSplashScreen(){
		this.setVisible(false);
	}
	public static void main(String[] args){
		SplashScreen splashScreen = new SplashScreen();
		GameFrame gameFrame = new GameFrame();
		//splashScreen.setVisible(true);
	}
}
