package Menus;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import GameComponents.GameFrame;
//http://zetcode.com/tutorials/javagamestutorial/movingsprites/
public class SplashScreen extends JFrame{
	SplashScreen(){
		// Instantiate jframe and instance variables
		Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();	// used to set position
		this.setBounds(ScreenSize.width/2-ScreenSize.width/8,ScreenSize.height/2-ScreenSize.height/8 ,  480, 270);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		splashPanel = new SplashScreenPanel();	// create new splash panel for text display
		this.add(splashPanel);	// add to frame
		// visibility thread instantiation
		visibilityRun = new Visibility();
		visibilityThread = new Thread(visibilityRun);

		show = true;	// set show to true for visibility thread
		visibilityThread.start();	// listen for visibility change
		this.setVisible(true);	// show this
		//splashPanel.requestFocus();
		validate();	// refresh
	}
	private static Thread visibilityThread;
	private Visibility visibilityRun;	// thread variable
	private static boolean show;	// show variable for splash screen showing
	// method to switch showing of splashscreem
	public static void setShow(){
		show = !show;
	}
	public static int startVisThread(){
		visibilityThread.start();
		return 1;
	}
	// thread to listen for input that makes gameframe visible
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
					setVisible(false);	// make this invisible
					GameFrame.setVis(true);	// show gameframe
					return;
				}
			}
			setVisible(false);	// hide
			GameFrame.setVis(true);	// set so game frame thread keeps running
			
		}
		
	}
	SplashScreenPanel splashPanel;
	public void hideSplashScreen(){
		this.setVisible(false);
	}
	public static void main(String[] args){
		SplashScreen splashScreen = new SplashScreen();	// Start splash screen
		GameFrame gameFrame = new GameFrame();	// run gameframe in background
		//splashScreen.setVisible(true);
	}
}
