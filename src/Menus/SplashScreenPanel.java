package Menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class SplashScreenPanel extends JPanel{ //implements KeyListener{
	
	public SplashScreenPanel(){
		Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(ScreenSize.width/2-ScreenSize.width/8,ScreenSize.height/2-ScreenSize.height/8 , ScreenSize.width/4, ScreenSize.height/4);
		//this.addKeyListener(this);
		this.setVisible(true);
		stringRun = new showStringThread();
		stringThread = new Thread(stringRun);
		stringThread.start();
		validate();
		this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"interrupt");
		this.getActionMap().put("interrupt",
				interruptT
		);
	}
	
	showStringThread stringRun;
	static Thread stringThread;
	Action interruptT = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
			validate();
		stringThread.interrupt();
		setVisible(false);
		}
	};
	public class showStringThread implements Runnable{

		public void run(){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(!Thread.interrupted()){

				showString = true;
				repaint();
			System.out.println(" aa");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				SplashScreen.setShow();
				return;
				
			}
			}
			
			
		}
	}
	boolean showString = false;

	public void paint(Graphics g){
		String continueS;
		if(showString){
			continueS = "Press Enter to Continue";
			g.setColor(Color.BLACK);
			g.drawString(continueS,this.getWidth()/2 -this.getWidth()/8 , this.getHeight()-this.getHeight()/4);
			showString = false;
			validate();
		}
		else{
			showString = true;
		}
	}
	
}
