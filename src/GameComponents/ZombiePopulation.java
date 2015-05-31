package GameComponents;

import java.util.Vector;

public class ZombiePopulation {
	public void ZombiePopulation(){
		ZombieLimit = 500;
		ZombieSpawnRate = 1000;
		genZombieThread = new Thread(new generateZombie());
		genZombieThread.start();
	}
	private int ZombieLimit;
	private int ZombieSpawnRate;
	private Vector<Zombie> Zombies = new Vector<Zombie>();
	public Thread genZombieThread;
	private void addZombie(){
		Zombies.addElement(new Zombie());
	}
	
	public void removeZombie(int zombieIndex){
		Zombies.get(zombieIndex).die();
		Zombies.remove(zombieIndex);
	}
	public Zombie getZombie(int zombieIndex){
		return Zombies.get(zombieIndex);
	}
	public int getZombiePopulation(){
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
				System.out.println("Generating Zombies");
				while(Zombies.size() < ZombieLimit){
					addZombie();
					try {
						Thread.sleep((int)(10*ZombieSpawnRate/getZombiePopulation()));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
}
