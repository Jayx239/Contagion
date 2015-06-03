package GameComponents;

import java.util.Vector;

public class BulletTracking {
	public BulletTracking(){
		bulletThread = new Thread(new moveBullets());
		bulletThread.start();
	}
	private static Thread bulletThread;
	public static Vector<Bullet> bullets = new Vector<Bullet>();
	public static void addBullet(Bullet bullet){
		bullets.addElement(bullet);
	}
	public int getNumBullets(){
		return bullets.size();
	}
	public Bullet getBullet(int index){
		return bullets.elementAt(index);
	}
	public void removeBullet(int index){
		bullets.remove(index);
	}
	
	public class moveBullets implements Runnable{
		moveBullets(){
			
		}
		
		public void run(){
			for(;;){
				for(int i=0; i< getNumBullets();i++){
					if(bullets.get(i).isDestroyed()){
						bullets.remove(i);
					}
				}
			}
		}
	}
	
}
