package GameComponents;

import java.util.Vector;

public class BulletTracking {
	public BulletTracking(){
		bulletThread = new Thread(new moveBullet());
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
	
	public class moveBullet implements Runnable{
		public moveBullet(){
			
		}
		@Override
		public void run() {
			boolean isAlive = true;
			for(;;){
			for(int j=0; j<getNumBullets(); j++){
			if(getBullet(j).getBulletX() <= GameFrame.getWindowWidth() && getBullet(j).getBulletX() >= 0 && getBullet(j).getBulletY() <= GameFrame.getWindowHeight() && getBullet(j).getBulletY() >=0){
			}
			else{
				removeBullet(j);
				isAlive = false;
			}
				for(int i=0; i< ZombiePopulation.getZombiePopulation(); i++){
					if(getBullet(j).getBulletX()<=(ZombiePopulation.getZombie(i).getPositionX()) && getBullet(j).getBulletX()>=(ZombiePopulation.getZombie(i).getPositionX()-Zombie.getSpriteDim()) && getBullet(j).getBulletY()<=(ZombiePopulation.getZombie(i).getPositionY()) && getBullet(j).getBulletY()>=(ZombiePopulation.getZombie(i).getPositionY()-Zombie.getSpriteDim())){
						ZombiePopulation.getZombie(i).setHealth(Player.getDamage());
						removeBullet(j);
						isAlive = false;
					}
				}
				if(isAlive){
				getBullet(j).setPosition();
				}
			
			
				System.out.println("running bullet thread");
				
				try {
					Thread.sleep(100/Bullet.getBulletSpeed());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			}
			}
		}
		
	}
	
}
