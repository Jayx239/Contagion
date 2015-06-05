package GameComponents;

public class Bullet {
	public Bullet(int[] dir){
		// set instance variables
		bulletX = Player.getPositionXStat();
		bulletY = Player.getPositionYStat();
		bulletDirection = dir;
		dx = (dir[2] - dir[0]) * bulletSpeed;
		dy = (dir[3] - dir[1]) * bulletSpeed;
		BulletTracking.addBullet(this);
		moveBulletThread = new Thread(new moveBullet());
		moveBulletThread.start();
	}
	// instance variables
	private Thread moveBulletThread;
	private static int bulletSpeed = 5;
	private int bulletX;
	private int bulletY;
	private int[] bulletDirection;
	private boolean destroyBullet = false;
	private boolean isAlive = true;
	private int dx;
	private int dy;
	// method to set bullet position
	public void setPosition(){

		bulletX += dx;
		bulletY += dy;
		
	}
	// method to get bullet x coords
	public int getBulletX(){
		return bulletX;
	}
	// method to get bullet y coords
	public int getBulletY(){
		return bulletY;
	}
	// method to get bullet speed
	public static int getBulletSpeed(){
		return bulletSpeed;
	}
	// method to move bullet off screen to hide
	public void hideBullet(){
		bulletX = -20;
	}
	// method to destroy bullet on hit or off screen
	public void destroy(){
		hideBullet();
		destroyBullet = true;
	}
	// method to check if bullet is still active, used by bullet tracking class
	public boolean isDestroyed(){
		return destroyBullet;
	}
	// move bullet runnable for bullet animation
	public class moveBullet implements Runnable{
		public moveBullet(){
			
		}
		@Override
		public void run() {
			
			for(;;){
				// detect if on screen
			if(getBulletX() <= GameFrame.getWindowWidth() && getBulletX() >= 0 &&getBulletY() <= GameFrame.getWindowHeight() && getBulletY() >=0){
			}
			else{
				isAlive = false;
			}
				// hit detection
				for(int i=0; i< ZombiePopulation.getZombiePopulation(); i++){
					if(getBulletX()>=(ZombiePopulation.getZombie(i).getPositionX()+50-10) && getBulletX()<=(ZombiePopulation.getZombie(i).getPositionX()+75+10)){
						if(getBulletY()>=(ZombiePopulation.getZombie(i).getPositionY()+45-10) && getBulletY()<=(ZombiePopulation.getZombie(i).getPositionY()+100+10)){
							destroy();
							try{
						ZombiePopulation.getZombie(i).setHealth(Player.getDamage());
							}
							catch(IllegalThreadStateException e){
								isAlive = false;
							}
						}
					}
				}
				if(isAlive){
				setPosition();
				}
				else{
					destroy();
					return;
				}
				
				try {
					Thread.sleep(100/Bullet.getBulletSpeed());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					destroy();
					return;
				}
			
			}
		}
		
	}
}
