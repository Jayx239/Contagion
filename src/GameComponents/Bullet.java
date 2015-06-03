package GameComponents;

public class Bullet {
	public Bullet(int[] dir){
		bulletX = Player.getPositionXStat();
		bulletY = Player.getPositionYStat();
		bulletDirection = dir;
		BulletTracking.addBullet(this);
	}
	private static int bulletSpeed = 5;
	private int bulletX;
	private int bulletY;
	private int[] bulletDirection;
	public void setPosition(){
		int dx = (bulletDirection[2] - bulletDirection[0]) * bulletSpeed;
		int dy = (bulletDirection[3] - bulletDirection[1]) * bulletSpeed;
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
		bulletX = 10000;
	}
}
