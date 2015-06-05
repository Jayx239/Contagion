package Menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class SplashScreenPanel extends JPanel{ //implements KeyListener{
	
	public SplashScreenPanel(){
		Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(ScreenSize.width/2-ScreenSize.width/8,ScreenSize.height/2-ScreenSize.height/8 , 480, 270);
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
	boolean showString = false;
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
			//while(!Thread.interrupted()){
			for(;;){
				repaint();
			System.out.println(" aa");
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				SplashScreen.setShow();
				return;
				
			}
			}
			
			
		}
	}

	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		String continueS;
		g2.setFont(new Font("Impact",Font.ITALIC,30));
		g2.setColor(Color.RED);
		g2.drawString("CONTAGION", 165, 30);
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Algerean",Font.PLAIN,20));
		g2.drawString("Created by Jason Gallagher",110,75);
		g2.setFont(new Font("Algerean",Font.PLAIN,10));
		g2.drawString("Zombie Sprites by Clint Bellanger",280,220);
		continueS = "Press Enter to Continue";
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Algerean",Font.PLAIN,20));
		g2.drawString(continueS,135,175);
		validate();
	}
	
}
