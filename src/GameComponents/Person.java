package GameComponents;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public interface Person {
	void move();
	void attack();
	void die();
	void setHealth(int increment);
	int getHealth();
	int getPositionX();
	int getPositionY();
	public void setPosition(); // -2:left 2:right -1:down 1:up
	BufferedImage getSprite();
	int[] getAffineSprite();
}
