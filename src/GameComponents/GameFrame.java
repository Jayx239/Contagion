package GameComponents;

import java.awt.Dimension;
import java.awt.Toolkit;
import Menus.*;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
	public GameFrame(){
		Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0,0, ScreenSize.width, ScreenSize.height);
		Thread visListen = new Thread(new ShowGameFrame());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		visListen.start();
	}
	private static boolean isVis = false;
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
}
