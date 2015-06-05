package GameComponents;

import java.util.Vector;

public class BulletTracking {
	public BulletTracking(){
		bulletThread = new Thread(new moveBullets());
		bulletThread.start();
	}
	// instance variables
	private static Thread bulletThread;	// bullet thread for position incrementation
	public static Vector<Bullet> bullets = new Vector<Bullet>();	// vector of all live bullets
	// method to add bullet to bullets vector
	public static void addBullet(Bullet bullet){
		bullets.addElement(bullet);
	}
	// get number of bullets in vector
	public int getNumBullets(){
		return bullets.size();
	}
	// method to get specific bullet by index
	public Bullet getBullet(int index){
		return bullets.elementAt(index);
	}
	// method to remove bullet from bullets vector
	public void removeBullet(int index){
		bullets.remove(index);
	}
	// thread to move all bullets
	public class moveBullets implements Runnable{
		moveBullets(){
			
		}
		
		public void run(){
			for(;;){
				for(int i=0; i< getNumBullets();i++){
					if(bullets.get(i).isDestroyed()){
						try{
						bullets.remove(i);
						}
						catch(ArrayIndexOutOfBoundsException e){
							
						}
						}
				}
			}
		}
	}
	
}
