package GameComponents;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

public class ZombiePopulation {
	public ZombiePopulation(){
		ZombieLimit = 500;
		ZombieSpawnRate = 1000;
		genZombieThread = new Thread(new generateZombie());
		genZombieThread.start();
		try {
			setZombieSprite = ImageIO.read(new File("assets/ZombieSprites.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// instance variables
	private BufferedImage setZombieSprite;
	private int ZombieLimit;
	private int ZombieSpawnRate;	// zombie spawn rate
	private static Vector<Zombie> Zombies = new Vector<Zombie>();	// vector to keep track of zombies
	public Thread genZombieThread; // generate zombie thread object
	//method to add zombie to zombies vector
	private void addZombie(){
		Zombie zombie = new Zombie(setZombieSprite);
		Zombies.addElement(zombie);
	}
	// method for removing dead zombies
	public void removeZombie(int zombieIndex){
		Zombies.get(zombieIndex).die();
		
		Zombies.remove(zombieIndex);
		
	}
	// method to get specific zombie
	public static Zombie getZombie(int zombieIndex){
		return Zombies.get(zombieIndex);
	}
	// method to get zombie population size
	public static int getZombiePopulation(){
		return Zombies.size();
	}
	// thread for adding zombies
	public class generateZombie implements Runnable{
		public generateZombie(){
			
		}
		public void run(){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(;;){
				// while zombies is less then maximum allowed number of zombies add zombie
				while(Zombies.size() < ZombieLimit){
					addZombie();
					// time between adding zombie
					try {
						Thread.sleep((ZombieSpawnRate));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
	
}
