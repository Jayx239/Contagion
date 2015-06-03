package GameComponents;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZombiePopulation {
	public ZombiePopulation(){
		ZombieLimit = 500;
		ZombieSpawnRate = 1000;
		genZombieThread = new Thread(new generateZombie());
		genZombieThread.start();
	}
	private int ZombieLimit;
	private int ZombieSpawnRate;
	private static Vector<Zombie> Zombies = new Vector<Zombie>();
	public Thread genZombieThread;
	private void addZombie(){
		Zombie zombie = new Zombie();
		Zombies.addElement(zombie);
	}
	
	public void removeZombie(int zombieIndex){
		Zombies.get(zombieIndex).die();
		
		Zombies.remove(zombieIndex);
		
	}
	public static Zombie getZombie(int zombieIndex){
		return Zombies.get(zombieIndex);
	}
	public static int getZombiePopulation(){
		return Zombies.size();
	}
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
				//System.out.println("Generating Zombies");
				while(Zombies.size() < ZombieLimit){
					addZombie();
					try {
						Thread.sleep((5000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
	
}
