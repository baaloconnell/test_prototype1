package objects;

import java.awt.Color;
import java.awt.Point;


public class Product 
{
	private int width, height;
	private Point position;
	private Color Color;
	private String name;
	private double costModifier;
	
	public Product(String name, int x, int y, int width, int height,Color c)
	{
		Point p = new Point(x,y);
		position = p;
		Color = c;
		this.width = width;
		this.height = height;
		this.setName(name);
		setCostModifier(1);
	}
	
	public Product(String name, int x, int y, int width, int height,Color c, double costModifier)
	{
		Point p = new Point(x,y);
		position = p;
		Color = c;
		this.width = width;
		this.height = height;
		this.setName(name);
		setCostModifier(1);
		this.costModifier = costModifier;
	}
	
	public void setColor(Color c)
	{
		Color = c;
	}
	
	public Color getColor()
	{
		return Color;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCostModifier()
	{
		return costModifier;
	}

	public void setCostModifier(double costModifier)
	{
		this.costModifier = costModifier;
	}
}
