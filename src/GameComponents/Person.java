package GameComponents;

public interface Person {
	void move();
	void attack();
	void die();
	void setHealth(int increment);
	int getHealth();
	int getPositionX();
	int getPositionY();
	void setPosition();
}
