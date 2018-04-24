package objects;

import java.awt.Color;
import java.awt.Point;


public class Customer
{
	private Point position;
	private int radius;
	private Color color;
	private int advertisementThreshold;
	
	public Customer(int x, int y,int radius,Color c)
	{
		this.radius = radius;
		position = new Point(x,y);
		color = c;
		advertisementThreshold = 26;
	}
	
	public void setPosition(Point p)
	{
		position = p;
	}
	
	public Point getPosition()
	{
		return position;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public Color getColor()
	{
		return color;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getAdvertisementThreshold() {
		return advertisementThreshold;
	}

	public void setAdvertisementThreshold(int advertisementThreshold) {
		this.advertisementThreshold = advertisementThreshold;
	}
}
