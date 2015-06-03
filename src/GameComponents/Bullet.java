package GameComponents;

public class Bullet {
	public Bullet(int[] dir){
		bulletX = Player.getPositionXStat();
		bulletY = Player.getPositionYStat();
		bulletDirection = dir;
		dx = (dir[2] - dir[0]) * bulletSpeed;
		dy = (dir[3] - dir[1]) * bulletSpeed;
		BulletTracking.addBullet(this);
		moveBulletThread = new Thread(new moveBullet());
		moveBulletThread.start();
	}
	private Thread moveBulletThread;
	private static int bulletSpeed = 5;
	private int bulletX;
	private int bulletY;
	private int[] bulletDirection;
	private boolean destroyBullet = false;
	private boolean isAlive = true;
	private int dx;
	private int dy;
	public void setPosition(){

		bulletX += dx;
		bulletY += dy;
		
	}
	public int getBulletX(){
		return bulletX;
	}
	public int getBulletY(){
		return bulletY;
	}
	public static int getBulletSpeed(){
		return bulletSpeed;
	}
	public void hideBullet(){
		bulletX = -20;
	}
	public void destroy(){
		hideBullet();
		destroyBullet = true;
	}
	public boolean isDestroyed(){
		return destroyBullet;
	}
	
	public class moveBullet implements Runnable{
		public moveBullet(){
			
		}
		@Override
		public void run() {
			
			for(;;){
			if(getBulletX() <= GameFrame.getWindowWidth() && getBulletX() >= 0 &&getBulletY() <= GameFrame.getWindowHeight() && getBulletY() >=0){
			}
			else{
				isAlive = false;
			}
				for(int i=0; i< ZombiePopulation.getZombiePopulation(); i++){
					if(getBulletX()>=(ZombiePopulation.getZombie(i).getPositionX()) && getBulletX()<=(ZombiePopulation.getZombie(i).getPositionX()+Zombie.getSpriteDim())){
						if(getBulletY()>=(ZombiePopulation.getZombie(i).getPositionY()) && getBulletY()<=(ZombiePopulation.getZombie(i).getPositionY()+Zombie.getSpriteDim())){
							destroy();
						ZombiePopulation.getZombie(i).setHealth(Player.getDamage());
						isAlive = false;
						}
					}
				}
				if(isAlive){
				setPosition();
				}
				else{
					destroy();
					break;
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
